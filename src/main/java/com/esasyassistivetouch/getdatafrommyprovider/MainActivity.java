package com.esasyassistivetouch.getdatafrommyprovider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final Uri CONTENT_URI = Uri.parse("content://com.esasyassistivetouch.democontentprovider.StudentProvider/StudentInformation");
    private static final String COL_NAME = "_name";
    private static final String[] PROJECTION_UNI = {"*"};
    private static final String[] ARGUMENT_UNI = {"ptit"};
    private static final String PERMISSION_PROVIDER_WRITE_DB = "com.esasyassistivetouch.democontentprovider.StudentProvider.WRITE_DATABASE";
    private static final String PERMISSION_PROVIDER_READ_DB = "com.esasyassistivetouch.democontentprovider.StudentProvider.READ_DATABASE";
    private static final String EXTRA_ID = "extra_id";
    private static final String EXTRA_NAME = "extra_name";
    private static final String EXTRA_UNI = "extra_uni";
    private static final String EXTRA_POSITION = "extra_position";
    private ArrayList<Student> listResult;
    private ResultAdapter resultAdapter;
    private RecyclerView rvResult;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listResult = new ArrayList<>();
        rvResult = findViewById(R.id.rv_result);
        rvResult.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.setNestedScrollingEnabled(false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        requestPermissions();
        listResult = getAllData();
        Button btDeleteAll = findViewById(R.id.bt_delete_all);
        Button btGetByUni = findViewById(R.id.bt_get_by_name);
        final EditText edFilter = findViewById(R.id.ed_filter);
        if (isAllPermissionGranted()) {
            resultAdapter = new ResultAdapter(listResult, this);
            resultAdapter.notifyDataSetChanged();
            rvResult.setAdapter(resultAdapter);
            resultAdapter.setItemOnclickListener(new OnItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra(EXTRA_ID, listResult.get(position).getId());
                    intent.putExtra(EXTRA_NAME, listResult.get(position).getName());
                    intent.putExtra(EXTRA_UNI, listResult.get(position).getUniversity());
                    intent.putExtra(EXTRA_POSITION, position);
                    startActivity(intent);
                }
            });
            btDeleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("content://com.esasyassistivetouch.democontentprovider.StudentProvider/StudentInformation");
                    if (getContentResolver().delete(uri, null, null) != 0) {
                        listResult.clear();
                        resultAdapter.notifyDataSetChanged();
                        rvResult.setAdapter(resultAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "Erros !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btGetByUni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listResult.clear();
                    listResult = getStudentByUni(edFilter.getText().toString());
                    resultAdapter = new ResultAdapter(listResult, MainActivity.this);
                    resultAdapter.notifyDataSetChanged();
                    rvResult.setAdapter(resultAdapter);
                }
            });
        } else {
            requestPermissions();
        }

    }


    public ArrayList<Student> getAllData() {
        ArrayList<Student> listResult = new ArrayList<>();
        Cursor cursor;
        cursor = this.getContentResolver().query(CONTENT_URI, null, null, null, "_name DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Student student = new Student();
                student.setId(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                student.setName(cursor.getString(cursor.getColumnIndexOrThrow("_name")));
                student.setUniversity(cursor.getString(cursor.getColumnIndexOrThrow("_uni")));
                listResult.add(student);
            }
            cursor.close();
        }
        return listResult;
    }

    public ArrayList<Student> getStudentByUni(String uni) {
        ArrayList<Student> listResult = new ArrayList<>();
        Cursor cursor;
        Uri uri = Uri.parse("content://com.esasyassistivetouch.democontentprovider.StudentProvider/StudentInformation/_uni");
        cursor = this.getContentResolver().query(uri, new String[]{"_id", "_name", "_uni"}, "_uni =?", new String[]{uni}, "_uni DESC");
        // SELECT String[]projection FROM TABLE_NAME_STUDENT WHERE selection =  String[] selectionArgs ORDER BY sortOrder;
        // SELECT _id, _name, _uni FROM StudentInformation WHERE _uni = uni ORDER BY _uni DESC;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Student student = new Student();
                student.setId(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                student.setName(cursor.getString(cursor.getColumnIndexOrThrow("_name")));
                student.setUniversity(cursor.getString(cursor.getColumnIndexOrThrow("_uni")));
                listResult.add(student);
            }
            cursor.close();
        }
        return listResult;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{PERMISSION_PROVIDER_READ_DB, PERMISSION_PROVIDER_WRITE_DB}, 1);
    }


    private boolean isAllPermissionGranted() {
        return isPermissionGranted(PERMISSION_PROVIDER_READ_DB, PERMISSION_PROVIDER_WRITE_DB);
    }

    private boolean isPermissionGranted(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String msg = event.getMsg();
        if (msg.equals("updateView")) {
            resultAdapter.notifyDataSetChanged();
            rvResult.setAdapter(resultAdapter);
        }
    }


}

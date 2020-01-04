package com.esasyassistivetouch.getdatafrommyprovider;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final Uri CONTENT_URI = Uri.parse("content://com.segu.demo.provider/students");
    private static final String COL_NAME = "name";
    private ArrayList<String> listResult;
    private ResultAdapter resultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listResult= new ArrayList<>();
        RecyclerView rvResult = findViewById(R.id.rv_result);
        rvResult.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.setNestedScrollingEnabled(false);
        listResult = getAllData();
        resultAdapter = new ResultAdapter(listResult,this);
        resultAdapter.notifyDataSetChanged();
        rvResult.setAdapter(resultAdapter);
        resultAdapter.setItemOnclickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(getBaseContext(), listResult.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }



    public String getData() {
        Cursor cursor;
        String result = null;
        Uri uriQuery = Uri.parse("content://com.segu.demo.provider/students");
        cursor = this.getContentResolver().query(uriQuery, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                result += cursor.getString(cursor.getColumnIndexOrThrow("name"));
            }
            cursor.close();
        }
        return result;
    }

    public ArrayList<String> getAllData() {
        ArrayList<String> listResult = new ArrayList<>();
        Cursor cursor;
        cursor = this.getContentResolver().query(CONTENT_URI, null, null, null, null);
        String data;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                data = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                listResult.add(data);
            }
            cursor.close();
        }
        return listResult;
    }



}

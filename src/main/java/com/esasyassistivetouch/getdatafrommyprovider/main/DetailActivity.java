package com.esasyassistivetouch.getdatafrommyprovider.main;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esasyassistivetouch.getdatafrommyprovider.R;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String EXTRA_ID = "extra_id";
    private static final String EXTRA_NAME = "extra_name";
    private static final String EXTRA_UNI = "extra_uni";
    TextView tvResultID;
    EditText edResultName;
    EditText edResultUni;
    Button btDelete;
    Button btUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

    }

    private void initView() {
        final Intent intentData = getIntent();
        tvResultID = findViewById(R.id.tv_result_id);
        edResultName = findViewById(R.id.ed_result_name);
        edResultUni = findViewById(R.id.ed_result_university);
        btDelete = findViewById(R.id.bt_delete);
        btUpdate = findViewById(R.id.bt_update_data);
        tvResultID.setText(intentData.getStringExtra(EXTRA_ID));
        edResultName.setText(intentData.getStringExtra(EXTRA_NAME));
        edResultUni.setText(intentData.getStringExtra(EXTRA_UNI));
        btDelete.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
    }

    @SuppressLint("ShowToast")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_delete:
                if (getContentResolver().delete(Uri.parse("content://com.esasyassistivetouch.democontentprovider.data.StudentProvider/StudentInformation/#")
                        , "_id = " + tvResultID.getText(), null) != 0) {
                    tvResultID.setText("");
                    edResultName.setText("");
                    edResultUni.setText("");
                } else {
                    Toast.makeText(DetailActivity.this, "Erros !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_update_data:
                Log.e("check", "onClick: " + "fdsgdsgdsdgsdgsdgs");
                ContentValues contentValues = new ContentValues();
                contentValues.put("_name", edResultName.getText().toString());
                contentValues.put("_uni", edResultUni.getText().toString());
                if (getContentResolver().update(Uri.parse("content://com.esasyassistivetouch.democontentprovider.data.StudentProvider/StudentInformation/#")
                        , contentValues, "_id = ?", new String[]{tvResultID.getText().toString()}) != 0) {
                    Toast.makeText(DetailActivity.this, "Update thanh cong !", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DetailActivity.this, "Erros !", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }
}

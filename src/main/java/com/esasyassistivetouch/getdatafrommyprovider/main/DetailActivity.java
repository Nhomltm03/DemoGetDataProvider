package com.esasyassistivetouch.getdatafrommyprovider.main;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esasyassistivetouch.getdatafrommyprovider.R;


public class DetailActivity extends AppCompatActivity {


    private static final String EXTRA_ID = "extra_id";
    private static final String EXTRA_NAME = "extra_name";
    private static final String EXTRA_UNI = "extra_uni";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final Intent intentData = getIntent();
        final TextView tvResultID = findViewById(R.id.tv_result_id);
        final EditText edResultName = findViewById(R.id.ed_result_name);
        final EditText edResultUni = findViewById(R.id.ed_result_university);
        Button btDelete = findViewById(R.id.bt_delete);
        Button btUpdate = findViewById(R.id.bt_update);
        tvResultID.setText(intentData.getStringExtra(EXTRA_ID));
        edResultName.setText(intentData.getStringExtra(EXTRA_NAME));
        edResultUni.setText(intentData.getStringExtra(EXTRA_UNI));
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.esasyassistivetouch.democontentprovider.data.StudentProvider/StudentInformation/#");
                if (getContentResolver().delete(uri, "_id = " + tvResultID.getText(), null) != 0) {
                    tvResultID.setText("");
                    edResultName.setText("");
                    edResultUni.setText("");
                } else {
                    Toast.makeText(DetailActivity.this, "Erros !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.esasyassistivetouch.democontentprovider.data.StudentProvider/StudentInformation/_uni");
                ContentValues contentValues = new ContentValues();
                contentValues.put("_name", edResultName.getText().toString());
                contentValues.put("_uni", edResultUni.getText().toString());
                getContentResolver().update(uri, contentValues, "_id = ?", new String[]{tvResultID.getText().toString()});
/*                if (getContentResolver().update(uri, contentValues, "_id = " + tvResultID.getText(), null)!=0) {
                    Toast.makeText(DetailActivity.this,"Update thanh cong",Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(DetailActivity.this,"Loi",Toast.LENGTH_SHORT);
                }*/
            }
        });

    }
}

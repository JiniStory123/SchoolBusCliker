package com.cafe24.as8794.schoolbuscliker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity
{
    Button btn_OK;
    EditText et_userID, et_userPW, et_name, et_email, et_tel, et_address;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btn_OK = findViewById(R.id.btn_OK);

        et_userID = findViewById(R.id.et_id);
        et_userPW = findViewById(R.id.et_pw);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_tel = findViewById(R.id.et_tel);
        et_address = findViewById(R.id.et_address);

        btn_OK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String str_id = et_userID.getText().toString();
                String str_pw = et_userPW.getText().toString();
                String str_name = et_name.getText().toString();
                String str_email = et_email.getText().toString();
                String str_tel = et_tel.getText().toString();
                String str_address = et_address.getText().toString();

                // 유효성 검사
                if (str_id.equals("") || str_pw.equals("") || str_name.equals("") || str_email.equals("") || str_tel.equals("") || str_address.equals(""))
                {
                    Toast.makeText(JoinActivity.this, "빈 칸 있음", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
}
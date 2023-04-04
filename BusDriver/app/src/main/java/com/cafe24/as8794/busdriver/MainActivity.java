package com.cafe24.as8794.busdriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    EditText et_id, et_pw;
    Button bt_login;
    String userID, userPass, userName, email, tel, address;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액션바 없애기
        getSupportActionBar().hide();

        // 액티비티 등장 애니메이션
        overridePendingTransition(R.anim.fadein, R.anim.none);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), BusListActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.none, R.anim.fadeout);
            }
        });
    }
}
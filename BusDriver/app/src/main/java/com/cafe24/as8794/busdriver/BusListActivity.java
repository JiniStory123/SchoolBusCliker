package com.cafe24.as8794.busdriver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BusListActivity extends AppCompatActivity
{
    // 위치 권한 얻어오기 위한 변수들
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1981;
    private static final int REQUEST_CODE_LOCATION_SETTINGS = 2981;
    private static final String[] PERMISSIONS =
            {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };

    GoHomeFragment goHomeFragment;
    GoSchoolFragment goSchoolFragment;

    String userID, userPass, userName, email, tel, address;

    Button bt_goSchool, bt_goHome;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        // 액티비티 전환 애니메이션
        overridePendingTransition(R.anim.fadein, R.anim.none);
        getSupportActionBar().hide();

        // 위치 권한 얻어오기
        ActivityCompat.requestPermissions(this, PERMISSIONS, 1000);

        goSchoolFragment = new GoSchoolFragment();
        goHomeFragment = new GoHomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.lin_fragment, goSchoolFragment).commit();

        bt_goHome = findViewById(R.id.bt_goHome);
        bt_goSchool = findViewById(R.id.bt_goSchool);

        bt_goSchool.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bt_goSchool.setBackgroundResource(R.drawable.selector_button2);
                bt_goHome.setBackgroundResource(R.drawable.selector_button);
                getSupportFragmentManager().beginTransaction().replace(R.id.lin_fragment, goSchoolFragment).commit();
            }
        });

        bt_goHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bt_goSchool.setBackgroundResource(R.drawable.selector_button);
                bt_goHome.setBackgroundResource(R.drawable.selector_button2);
                getSupportFragmentManager().beginTransaction().replace(R.id.lin_fragment, goHomeFragment).commit();
            }
        });
    }
}
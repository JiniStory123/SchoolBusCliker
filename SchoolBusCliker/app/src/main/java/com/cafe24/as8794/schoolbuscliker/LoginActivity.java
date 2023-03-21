
/*

    로그인 화면
    어플리케이션을 실행했을 때 처음으로 보여지는 화면

    ### 2023-02-28 ###
        1. 현재 기능
            - 로그인 기능 완벽 작동

        2. 추가 긔
            - 회원가입 페이지
            - 자동 로그인 (안되면 아이디라도 남기기)

 */


package com.cafe24.as8794.schoolbuscliker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
{
    // 권한 관련 테스트
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1981;
    private static final int REQUEST_CODE_LOCATION_SETTINGS = 2981;
    private static final String[] PERMISSIONS =
            {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };

    // 액티비티 제어 변수
    EditText et_id;
    EditText et_password;
    Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 액션바 없애기
        getSupportActionBar().hide();

        // 액티비티 등장 애니메이션
        overridePendingTransition(R.anim.fadein, R.anim.none);

        // 액티비티 제어 변수 아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);

        // 위치 권한 얻어오기
//        ActivityCompat.requestPermissions(this, PERMISSIONS, 1000);

        // 로그인 버튼 클릭시
        bt_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 에디트박스 값 얻어오기
                String userID = et_id.getText().toString();
                String userPW = et_password.getText().toString();

                // 빈 값이 있는지
                if(userID.equals("") || userPW.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "빈칸 없이 입력해야 해요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Volley로 DB 접근
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) // 로그인에 성공한 경우
                            {
                                // 에디트박스 초기화
                                et_id.setText("");
                                et_password.setText("");

                                // DB 회원정보 값 얻어오기
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");
                                String email = jsonObject.getString("email");
                                String tel = jsonObject.getString("tel");
                                String address = jsonObject.getString("address");

                                // 액티비티 전환 (메인 화면)
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPass", userPass);
                                intent.putExtra("userName", userName);
                                intent.putExtra("email", email);
                                intent.putExtra("tel", tel);
                                intent.putExtra("address", address);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.none, R.anim.fadeout);
                            } else
                            { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"아이디 혹은 비밀번호가 잘못된 것 같아요.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                };
                // Volley 객체 큐 요청
                RequestLogin_Student loginRequest = new RequestLogin_Student(userID, userPW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(loginRequest);
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    // 뒤로가기
    private long tempTime = System.currentTimeMillis();
    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
        if (System.currentTimeMillis() > tempTime + 2000)
        {
            tempTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= tempTime + 2000)
        {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    // 권한 테스트
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode)
//        {
//            case 1000:
//            {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                {
////                    Toast.makeText(this, "승인이 허가되어 있습니다.", Toast.LENGTH_LONG).show();
//                }
//                else
//                {
////                    Toast.makeText(this, "아직 승인받지 않았습니다.", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//
//        }
//    }
}
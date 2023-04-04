package com.cafe24.as8794.schoolbuscliker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class BusReservation_D_BusStop extends AppCompatActivity
{
    String userID, userPass, userName, email, tel, address, busNumber;
    String date;
    TextView tv_string;
    TextView tv_start, tv_end;
    Spinner spinner_start, spinner_end;
    int int_select;
    Button bt_start, bt_end;
    Button bt_OK;
    String[] str_BusStop = new String[20];
    String[] str_BusStop_Time = new String[20];
    int int_select_time;
    int int_busStopCount;
    PopupMenu popupMenu;
    Boolean isSelect;

    private void DefaultSetting()
    {
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userPass = intent.getStringExtra("userPass");
        userName = intent.getStringExtra("userName");
        email = intent.getStringExtra("email");
        tel = intent.getStringExtra("tel");
        address = intent.getStringExtra("address");
        date = intent.getStringExtra("date");
        busNumber = intent.getStringExtra("busNumber");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_reservation_d_bus_stop);

        LocalDate seoul = LocalDate.now(ZoneId.of("Asia/Seoul"));

        isSelect = false;
        int_busStopCount = 0;
        DefaultSetting();

        overridePendingTransition(R.anim.horizon, R.anim.none);

        // 상단바 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(busNumber + " 예약하기");

        bt_start = findViewById(R.id.bt_start);
        bt_end = findViewById(R.id.bt_end);
        bt_OK = findViewById(R.id.bt_next);
        tv_string = findViewById(R.id.tv_string);
        tv_start = findViewById(R.id.tv_start);
        tv_end = findViewById(R.id.tv_end);
        spinner_start = findViewById(R.id.sp_start);
        spinner_end = findViewById(R.id.sp_end);

        tv_string.setText(userName + "님, 예약해볼까요?");

        BusStopLoad();

        popupMenu = new PopupMenu(this, bt_start, Gravity.CENTER, 0, R.style.MyPopupMenu);

        bt_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isSelect == false)
                {
                    for(int i=0; i<(int_busStopCount - 1); i++)
                    {
                        popupMenu.getMenu().add(0, i, 0, str_BusStop[i]);
                    }
                    isSelect = true;
                }
                popupMenu.show();
            }
        });

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                bt_start.setText(str_BusStop[menuItem.getItemId()]);
                int_select_time = menuItem.getItemId();
                bt_OK.setEnabled(true);
                return false;
            }
        });

        bt_OK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String start = bt_start.getText().toString();

                if (start.equals("목원대학교"))
                {
                    Toast.makeText(getApplicationContext(), "승차지점과 하차지점은 같을 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BusReservation_D_BusStop.this);
                    builder.setTitle("예약 확인");
                    builder.setMessage("탑승 버스 : " + busNumber + "\n승차 정류장 : " + start + "\n하차 정류장 : 목원대학교\n위 내용이 맞나요?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            LocalTime ExpiredTime = LocalTime.parse(str_BusStop_Time[int_select_time]);

                            String ExpiredDate = date + " " + ExpiredTime.plusHours(1) + ":00";

                            builder.setNegativeButton("아니오", null);
                            builder.create().show();
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) { // 예약에 성공한 경우
                                            Toast.makeText(getApplicationContext(),"예약에 성공했어요",Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else { // 예약에 실패한 경우
                                            Toast.makeText(getApplicationContext(),"예약에 실패했어요.",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            // 서버로 Volley를 이용해서 요청을 함.
                            RequestReservation requestReservation = new RequestReservation(userID, userName, busNumber, date, start, "목원대학교", "탑승 전", ExpiredDate, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            queue.add(requestReservation);

                            finish();
                            overridePendingTransition(R.anim.none, R.anim.horizon_exit);
                        }
                    });
                    builder.setNegativeButton("아니오", null);
                    builder.create().show();
                }
            }
        });
    }

    void BusStopLoad()
    {
        String URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D1.php";

        switch (busNumber)
        {
            case "등교버스1번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D1.php";
                break;
            case "등교버스2번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D2.php";
                break;
            case "등교버스3번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D3.php";
                break;
            case "등교버스4번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D4.php";
                break;
            case "등교버스5번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D5.php";
                break;
            case "등교버스6번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D6.php";
                break;
            case "등교버스7번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D7.php";
                break;
//            case "하교버스1번" :
//                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H1.php";
//                break;
//            case "하교버스2번" :
//                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H2.php";
//                break;
//            case "하교버스3번" :
//                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H3.php";
//                break;

        }

        if (!(busNumber.equals("테스트1번") || busNumber.equals("테스트2번")))
        {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>()
            {
                @Override
                public void onResponse(JSONArray response)
                {
                    try
                    {
                        for (int i = 0; i < response.length(); i++)
                        {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String busStop = jsonObject.getString("busStop");
                            String time = jsonObject.getString("time");

                            str_BusStop[i] = busStop + "";
                            str_BusStop_Time[i] = time + "";
                            int_busStopCount++;
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            requestQueue.add(jsonArrayRequest);
        }

        if (busNumber.equals("테스트1번"))
        {
            str_BusStop[0] = "동신빌라";
            str_BusStop_Time[0] = "12:00";
            str_BusStop[1] = "정림초등학교";
            str_BusStop_Time[1] = "12:00";
            str_BusStop[2] = "정림삼거리";
            str_BusStop_Time[2] = "12:00";
            int_busStopCount = 4;
        }

        if(busNumber.equals("테스트2번"))
        {
            str_BusStop[0] = "목원대학교입구";
            str_BusStop_Time[0] = "12:00";
            str_BusStop[1] = "중앙로터리";
            str_BusStop_Time[1] = "12:00";
            str_BusStop[2] = "중앙도서관";
            str_BusStop_Time[2] = "12:00";
            str_BusStop[3] = "공과대학";
            str_BusStop_Time[3] = "12:00";
            int_busStopCount = 5;
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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        if(isFinishing())
        {
            overridePendingTransition(R.anim.none, R.anim.horizon_exit);
        }
    }
}
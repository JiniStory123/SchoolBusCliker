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

        // ????????? ??????
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(busNumber + " ????????????");

        bt_start = findViewById(R.id.bt_start);
        bt_end = findViewById(R.id.bt_end);
        bt_OK = findViewById(R.id.bt_next);
        tv_string = findViewById(R.id.tv_string);
        tv_start = findViewById(R.id.tv_start);
        tv_end = findViewById(R.id.tv_end);
        spinner_start = findViewById(R.id.sp_start);
        spinner_end = findViewById(R.id.sp_end);

        tv_string.setText(userName + "???, ???????????????????");

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

                if (start.equals("???????????????"))
                {
                    Toast.makeText(getApplicationContext(), "??????????????? ??????????????? ?????? ??? ????????????.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BusReservation_D_BusStop.this);
                    builder.setTitle("?????? ??????");
                    builder.setMessage("?????? ?????? : " + busNumber + "\n?????? ????????? : " + start + "\n?????? ????????? : ???????????????\n??? ????????? ??????????");
                    builder.setPositiveButton("???", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            LocalTime ExpiredTime = LocalTime.parse(str_BusStop_Time[int_select_time]);

                            String ExpiredDate = date + " " + ExpiredTime.plusHours(1) + ":00";

                            builder.setNegativeButton("?????????", null);
                            builder.create().show();
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) { // ????????? ????????? ??????
                                            Toast.makeText(getApplicationContext(),"????????? ???????????????",Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else { // ????????? ????????? ??????
                                            Toast.makeText(getApplicationContext(),"????????? ???????????????.",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            // ????????? Volley??? ???????????? ????????? ???.
                            RequestReservation requestReservation = new RequestReservation(userID, userName, busNumber, date, start, "???????????????", "?????? ???", ExpiredDate, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            queue.add(requestReservation);

                            finish();
                            overridePendingTransition(R.anim.none, R.anim.horizon_exit);
                        }
                    });
                    builder.setNegativeButton("?????????", null);
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
            case "????????????1???" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D1.php";
                break;
            case "????????????2???" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D2.php";
                break;
            case "????????????3???" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D3.php";
                break;
            case "????????????4???" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D4.php";
                break;
            case "????????????5???" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D5.php";
                break;
            case "????????????6???" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D6.php";
                break;
            case "????????????7???" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D7.php";
                break;
//            case "????????????1???" :
//                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H1.php";
//                break;
//            case "????????????2???" :
//                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H2.php";
//                break;
//            case "????????????3???" :
//                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H3.php";
//                break;

        }

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
                Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(jsonArrayRequest);
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
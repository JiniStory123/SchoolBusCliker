package com.cafe24.as8794.schoolbuscliker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BusReservation_D_Date extends AppCompatActivity
{
    CalendarView calendarView;
    TextView tv_string;
    TextView tv_state;
    Button bt_next;
    String userID, userPass, userName, email, tel, address, busNumber;
    String date;

    private void DefaultSetting()
    {
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userPass = intent.getStringExtra("userPass");
        userName = intent.getStringExtra("userName");
        email = intent.getStringExtra("email");
        tel = intent.getStringExtra("tel");
        address = intent.getStringExtra("address");
        busNumber = intent.getStringExtra("busNumber");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_reservation_d_date);

        DefaultSetting();

        // 상단바 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(busNumber + " 예약하기");

        calendarView = findViewById(R.id.calender);

        tv_string = findViewById(R.id.tv_string);
        tv_state = findViewById(R.id.tv_state);
        bt_next = findViewById(R.id.bt_next);

        tv_string.setText(userName + "님, 예약해볼까요?");


        // 날짜 받아오기
        Calendar calender = Calendar.getInstance();
        Calendar week = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.KOREA);

        long now_time = System.currentTimeMillis();
        Date mDate = new Date(now_time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        date = simpleDateFormat.format(mDate);
        tv_state.setText(date);
        tv_state.setTextColor(Color.parseColor("#000000"));

        String weekDate_ = dayFormat.format(now_time);

        if(weekDate_.equals("토요일") || weekDate_.equals("일요일"))
        {
            bt_next.setEnabled(false);
        }
        else
        {
            bt_next.setEnabled(true);
        }

        calendarView.setMinDate(now_time);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day)
            {
                week.set(year, month, day);
                String weekDate = dayFormat.format(week.getTime());

                if(weekDate.equals("토요일") || weekDate.equals("일요일"))
                {
                    tv_state.setText("주말은 예약할 수 없어요!");
                    tv_state.setTextColor(0xFFD30E00);
                    bt_next.setEnabled(false);
                    return;
                }
                else
                {
                    tv_state.setTextColor(Color.parseColor("#000000"));
                    bt_next.setEnabled(true);
                }

                String date;
                String str_year, str_month, str_day;
                str_year = year + "-";

                if((month+1) < 10)
                {
                    str_month = "0" + (month+1);
                }
                else
                {
                    str_month = "" + (month+1);
                }

                if(day < 10)
                {
                    str_day = "-0" + day;
                }
                else
                {
                    str_day = "-" + day;
                }

                date = str_year + str_month + str_day;
                tv_state.setText(date);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(getApplicationContext(), BusReservation_D_BusStop.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("date", tv_state.getText().toString());
                intent.putExtra("busNumber", busNumber);
                startActivity(intent);
                finish();
            }
        });
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
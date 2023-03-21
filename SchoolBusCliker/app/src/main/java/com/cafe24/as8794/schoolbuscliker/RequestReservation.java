package com.cafe24.as8794.schoolbuscliker;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestReservation extends StringRequest
{
    //서버 url 설정(php파일 연동)
    final static private String URL = "https://as8794.cafe24.com/new_bus_clicker/user_management/bus_city_reservation.php";
    private Map<String, String> map;

    public RequestReservation(String userID, String userName, String busNumber, String date, String start, String end, String isBoarding, String ExpiredDate, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userName", userName);
        map.put("busNumber", busNumber);
        map.put("date", date);
        map.put("start", start);
        map.put("end", end);
        map.put("isBoarding", isBoarding);
        map.put("ExpiredDate", ExpiredDate);
    }

    public RequestReservation(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener)
    {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

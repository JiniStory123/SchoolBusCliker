package com.cafe24.as8794.schoolbuscliker;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestDelete extends StringRequest
{
    //서버 url 설정(php파일 연동)
    final static private String URL = "https://as8794.cafe24.com/new_bus_clicker/user_management/bus_city_reservation_delete.php";
    private Map<String, String> map;

    public RequestDelete(int id, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", String.valueOf(id));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return map;
    }

}

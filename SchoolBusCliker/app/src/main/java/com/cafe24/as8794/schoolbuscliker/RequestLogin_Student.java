package com.cafe24.as8794.schoolbuscliker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestLogin_Student extends StringRequest
{
    // 서버 url 설정
    final static private String URL = "https://as8794.cafe24.com/new_bus_clicker/user_management/login_student.php";
    private Map<String, String> map;

    public RequestLogin_Student(String userID, String userPassword, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword", userPassword);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return map;
    }
}

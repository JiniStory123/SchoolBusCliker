package com.cafe24.as8794.schoolbuscliker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class ReservationInformation extends Fragment
{

    MainActivity main;

    AdapterRecyclerReservation adapter = null;
    RecyclerViewEmptySupport recyclerView = null;
    ArrayList<itemReservationCheck> list;
    String userID, userPass, userName, email, tel, address;

    TextView tv_empty;
    int int_dataCount;

    public ReservationInformation()
    {
        super();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        main = (MainActivity)context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        main = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_reservation_information, container, false);

        int_dataCount = 0;
        tv_empty = view.findViewById(R.id.tv_empty);

        recyclerView = view.findViewById(R.id.recycler);
        list = new ArrayList<>();
        adapter = new AdapterRecyclerReservation(list);

        ((MainActivity)MainActivity.context_main).setList(list);
        ((MainActivity)MainActivity.context_main).setAdapter(adapter);
        ((MainActivity)MainActivity.context_main).setRecyclerView(recyclerView);
        ((MainActivity)MainActivity.context_main).setTextView(tv_empty);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false));

        recyclerView.setVisibility(View.GONE);
        tv_empty.setVisibility(View.VISIBLE);

        DataLode();
        adapter.notifyDataSetChanged();


        return view;
    }

    void DataLode()
    {
        String serverUrl = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                try
                {
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String userId = jsonObject.getString("userID");
                        String start = jsonObject.getString("start");
                        String end = jsonObject.getString("end");
                        String bus = jsonObject.getString("busNumber");
                        String date = jsonObject.getString("date");
                        int id = Integer.parseInt(jsonObject.getString("id"));
                        String isBoarding = jsonObject.getString("isBoarding");

                        if(userId.equals(userID))
                        {
                            list.add(0, new itemReservationCheck(start, end, bus, date, id, isBoarding));
                            adapter.notifyItemInserted(0);
                            int_dataCount++;
                        }

                        if (int_dataCount == 0)
                        {
                            recyclerView.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            recyclerView.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        }
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
                Toast.makeText(main, "에러", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public void setUserPass(String userPass)
    {
        this.userPass = userPass;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
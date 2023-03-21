package com.cafe24.as8794.schoolbuscliker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ReservationGoHomeListFragment extends Fragment
{

    MainActivity main;
    String userID, userPass, userName, email, tel, address;
    Button bt_H1, bt_H2, bt_H3, bt_H4, bt_H5;

    public ReservationGoHomeListFragment()
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
        View view = inflater.inflate(R.layout.activity_reservation_go_home_list_fragment, container, false);

        bt_H1 = view.findViewById(R.id.bt_H1);
        bt_H2 = view.findViewById(R.id.bt_H2);
        bt_H3 = view.findViewById(R.id.bt_H3);
        bt_H4 = view.findViewById(R.id.bt_H4);
        bt_H5 = view.findViewById(R.id.bt_H5);

        bt_H1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), BusReservation_H_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "하교버스1번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_H2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), BusReservation_H_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "하교버스2번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_H3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), BusReservation_H_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "하교버스3번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_H4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), BusReservation_H_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "하교버스4번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_H5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), BusReservation_H_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "하교버스5번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        return view;
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
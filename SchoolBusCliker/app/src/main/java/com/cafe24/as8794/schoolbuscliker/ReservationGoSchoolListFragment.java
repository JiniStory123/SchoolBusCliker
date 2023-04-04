package com.cafe24.as8794.schoolbuscliker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

public class ReservationGoSchoolListFragment extends Fragment
{

    MainActivity main;

    String userID, userPass, userName, email, tel, address;

    Button bt_D1, bt_D2, bt_D3, bt_D4, bt_D5, bt_D6, bt_D7;

    Button bt_test1, bt_test2;

    public ReservationGoSchoolListFragment()
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

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_reservation_go_school_list_fragment, container, false);

//        Toast.makeText(main, userID, Toast.LENGTH_SHORT).show();

        bt_D1 = view.findViewById(R.id.bt_D1);
        bt_D2 = view.findViewById(R.id.bt_D2);
        bt_D3 = view.findViewById(R.id.bt_D3);
        bt_D4 = view.findViewById(R.id.bt_D4);
        bt_D5 = view.findViewById(R.id.bt_D5);
        bt_D6 = view.findViewById(R.id.bt_D6);
        bt_D7 = view.findViewById(R.id.bt_D7);

        bt_test1 = view.findViewById(R.id.bt_test1);
        bt_test2 = view.findViewById(R.id.bt_test2);

        bt_D1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), BusReservation_D_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "등교버스1번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), BusReservation_D_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "등교버스2번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), BusReservation_D_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "등교버스3번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), BusReservation_D_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "등교버스4번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), BusReservation_D_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "등교버스5번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), BusReservation_D_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "등교버스6번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), BusReservation_D_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "등교버스7번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });


        bt_test1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), BusReservation_D_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "테스트1번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_test2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), BusReservation_D_Date.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("email", email);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.putExtra("busNumber", "테스트2번");
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
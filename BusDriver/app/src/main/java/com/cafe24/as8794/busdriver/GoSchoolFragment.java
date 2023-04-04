package com.cafe24.as8794.busdriver;

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

public class GoSchoolFragment extends Fragment
{

    BusListActivity main;
    String userID, userPass, userName, email, tel, address;
    Button bt_D1, bt_D2, bt_D3, bt_D4, bt_D5, bt_D6, bt_D7;

    Button bt_test1, bt_test2;

    public GoSchoolFragment()
    {
        super();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        main = (BusListActivity) context;
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
        View view = inflater.inflate(R.layout.activity_go_school_fragment, container, false);

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
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "등교버스1번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "등교버스2번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "등교버스3번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "등교버스4번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "등교버스5번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "등교버스6번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_D7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "등교버스7번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_test1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "테스트1번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_test2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "테스트2번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        return view;
    }
}
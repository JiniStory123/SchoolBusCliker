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
import android.view.animation.Animation;
import android.widget.Button;

public class GoHomeFragment extends Fragment
{

    BusListActivity main;
    String userID, userPass, userName, email, tel, address;
    Button bt_H1, bt_H2, bt_H3, bt_H4, bt_H5;

    public GoHomeFragment()
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
        View view = inflater.inflate(R.layout.activity_go_home_fragment, container, false);

        bt_H1 = view.findViewById(R.id.bt_H1);
        bt_H2 = view.findViewById(R.id.bt_H2);
        bt_H3 = view.findViewById(R.id.bt_H3);
        bt_H4 = view.findViewById(R.id.bt_H4);
        bt_H5 = view.findViewById(R.id.bt_H5);

        bt_H1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "하교버스1번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        bt_H2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), BusStopInformationActivity.class);
                intent.putExtra("bus", "하교버스2번");
                startActivity(intent);
                main.overridePendingTransition(R.anim.horizon, R.anim.none);
            }
        });

        return view;
    }
}
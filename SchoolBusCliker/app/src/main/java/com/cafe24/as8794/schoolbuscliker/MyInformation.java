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
import android.widget.TextView;
import android.widget.Toast;

public class MyInformation extends Fragment
{
    MainActivity main;
    String userID, userPass, userName, email, tel, address;

    TextView tv_userName;
    Button bt_logout;
    public MyInformation()
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
        View view = inflater.inflate(R.layout.activity_my_information, container, false);

        tv_userName = view.findViewById(R.id.tv_username);
        bt_logout = view.findViewById(R.id.bt_logout);

        tv_userName.setText(userName + "님, 환영합니다.");

        bt_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "로그아웃했어요", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                main.finish();
                main.overridePendingTransition(R.anim.none, R.anim.fadeout);
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
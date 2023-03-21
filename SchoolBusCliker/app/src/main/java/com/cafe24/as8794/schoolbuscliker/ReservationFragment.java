
/*



*/

package com.cafe24.as8794.schoolbuscliker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ReservationFragment extends Fragment
{
    MainActivity main;
    ReservationGoSchoolListFragment reservationGoSchoolListFragment;
    ReservationGoHomeListFragment reservationGoHomeListFragment;

    Button bt_goSchool;
    Button bt_goHome;

    String userID, userPass, userName, email, tel, address;

    public ReservationFragment()
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
        View view = inflater.inflate(R.layout.activity_reservation_fragment, container, false);

        reservationGoSchoolListFragment = new ReservationGoSchoolListFragment();
        reservationGoHomeListFragment = new ReservationGoHomeListFragment();

        reservationGoSchoolListFragment.setUserID(userID);
        reservationGoSchoolListFragment.setUserPass(userPass);
        reservationGoSchoolListFragment.setUserName(userName);
        reservationGoSchoolListFragment.setEmail(email);
        reservationGoSchoolListFragment.setTel(tel);
        reservationGoSchoolListFragment.setAddress(address);

        reservationGoHomeListFragment.setUserID(userID);
        reservationGoHomeListFragment.setUserPass(userPass);
        reservationGoHomeListFragment.setUserName(userName);
        reservationGoHomeListFragment.setEmail(email);
        reservationGoHomeListFragment.setTel(tel);
        reservationGoHomeListFragment.setAddress(address);

        getChildFragmentManager().beginTransaction().replace(R.id.lin_fragment, reservationGoSchoolListFragment).commit();

        bt_goSchool = view.findViewById(R.id.bt_goSchool);
        bt_goHome = view.findViewById(R.id.bt_goHome);

        bt_goSchool.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bt_goSchool.setBackgroundResource(R.drawable.selector_button2);
                bt_goHome.setBackgroundResource(R.drawable.selector_button);
                getChildFragmentManager().beginTransaction().replace(R.id.lin_fragment, reservationGoSchoolListFragment).commit();
            }
        });

        bt_goHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bt_goSchool.setBackgroundResource(R.drawable.selector_button);
                bt_goHome.setBackgroundResource(R.drawable.selector_button2);
                getChildFragmentManager().beginTransaction().replace(R.id.lin_fragment, reservationGoHomeListFragment).commit();
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
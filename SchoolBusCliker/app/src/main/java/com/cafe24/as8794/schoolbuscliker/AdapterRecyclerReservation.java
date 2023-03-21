package com.cafe24.as8794.schoolbuscliker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterRecyclerReservation extends RecyclerView.Adapter<AdapterRecyclerReservation.ViewHolder>
{
    MainActivity main;
    ReservationInformation reservationInformation;
    int ID;
    private ArrayList<itemReservationCheck> data = null;
    LinearLayout lin_1, lin_2, lin_3;

    private IntentIntegrator qrScan;

    String str;

    public AdapterRecyclerReservation(ArrayList<itemReservationCheck> data)
    {
        this.data = data;
    }

    // onCreateViewHolder : 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_reservation, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    // onBindViewHolder : position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        itemReservationCheck item = data.get(position);

        holder.start.setText(item.getStart());
        holder.end.setText(item.getEnd());
        holder.bus.setText(item.getBus());
        holder.date.setText(item.getDate());
        holder.id.setText(item.getId()+"");

        String sta = item.getIsBoarding()+"";
        if (sta.equals("미탑승"))
        {
            holder.state.setTextColor(Color.RED);
//            btn_cancel.setVisibility(View.INVISIBLE);
//            btn_OK.setVisibility(View.INVISIBLE);
            lin_1.setVisibility(View.GONE);
            lin_2.setVisibility(View.GONE);
            lin_3.setVisibility(View.VISIBLE);
        }

        if (sta.equals("탑승 완료"))
        {
            holder.state.setTextColor(Color.GREEN);
//            btn_cancel.setVisibility(View.INVISIBLE);
//            btn_OK.setVisibility(View.INVISIBLE);
            lin_1.setVisibility(View.GONE);
            lin_2.setVisibility(View.GONE);
            lin_3.setVisibility(View.VISIBLE);
        }

        holder.state.setText(item.getIsBoarding()+"");

        holder.cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (holder.state.getText().toString().equals("탑승 완료"))
                {
                    Toast.makeText(holder.id.getContext(), "이미 탑승이 확인되었어요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.id.getContext());
                builder.setTitle("예약 취소");
                builder.setMessage("예약을 취소할까요?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        ID = Integer.parseInt((String)holder.id.getText().toString());
                        Response.Listener<String> responseListener = new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                                    System.out.println("hongchul" + response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success)
                                    { // 성공
                                        Toast.makeText(holder.id.getContext(), "예약을 취소했어요.",Toast.LENGTH_SHORT).show();

                                        ((MainActivity)MainActivity.context_main).DataLode();
                                    } else
                                    { // 실패

                                    }
                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        };
                        RequestDelete requestDelete = new RequestDelete(ID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(holder.id.getContext().getApplicationContext());
                        queue.add(requestDelete);
                    }
                });
                builder.setNegativeButton("아니오", null);
                builder.create().show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View _view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.id.getContext());
                builder.setTitle("탑승 내역 삭제");
                builder.setMessage("탑승 내역을 삭제할까요?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        ID = Integer.parseInt((String)holder.id.getText().toString());
                        Response.Listener<String> responseListener = new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                                    System.out.println("hongchul" + response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success)
                                    { // 성공
                                        Toast.makeText(holder.id.getContext(), "내역을 삭제했어요.",Toast.LENGTH_SHORT).show();

                                        ((MainActivity)MainActivity.context_main).DataLode();
                                    } else
                                    { // 실패

                                    }
                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        };
                        RequestDelete requestDelete = new RequestDelete(ID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(holder.id.getContext().getApplicationContext());
                        queue.add(requestDelete);
                    }
                });
                builder.setNegativeButton("아니오", null);
                builder.create().show();
            }
        });

        qrScan = new IntentIntegrator(((MainActivity)MainActivity.context_main));

        holder.check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (holder.state.getText().toString().equals("탑승 완료"))
                {
                    Toast.makeText(holder.id.getContext(), "이미 탑승이 확인되었어요.",Toast.LENGTH_SHORT).show();
                    return;
                }
//                ((MainActivity)MainActivity.context_main).setHolder(holder);
//                ((MainActivity)MainActivity.context_main).setPosition(position);
                ((MainActivity)MainActivity.context_main).setBus_Number(holder.bus.getText().toString());
                ((MainActivity)MainActivity.context_main).setIsBoarding(holder.state.getText().toString());
                ((MainActivity)MainActivity.context_main).setID(item.getId());
                //scan option
                qrScan.setPrompt("QR 코드를 인식해주세요.");
                qrScan.initiateScan();
            }
        });
    }

    public void removeItem(int position)
    {
        data.remove(position);
        notifyDataSetChanged();
    }

    // getItemCount : 전체 데이터의 개수를 리턴
    @Override
    public int getItemCount()
    {
        return data.size();
    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        EditText start, end, bus, date;
        TextView id, state;
        Button cancel, delete, check;

        ViewHolder(View view)
        {
            super(view);
            start = view.findViewById(R.id.et_start);
            end = view.findViewById(R.id.et_end);
            bus = view.findViewById(R.id.et_bus);
            date = view.findViewById(R.id.et_date);
            id = view.findViewById(R.id.tv_id);
            state = view.findViewById(R.id.tv_state);

            cancel = view.findViewById(R.id.btn_Cancel);
            delete = view.findViewById(R.id.btn_delete);
            check = view.findViewById(R.id.btn_OK);

            lin_1 = view.findViewById(R.id.lin_1);
            lin_2 = view.findViewById(R.id.lin_2);
            lin_3 = view.findViewById(R.id.lin_3);
        }
    }
}

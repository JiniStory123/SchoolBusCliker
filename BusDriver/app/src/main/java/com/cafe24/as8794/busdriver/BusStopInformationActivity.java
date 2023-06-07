package com.cafe24.as8794.busdriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class BusStopInformationActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1981;
    private static final int REQUEST_CODE_LOCATION_SETTINGS = 2981;

    private static final String[] PERMISSIONS =
            {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
    private NaverMap naverMap;
    LocationOverlay locationOverlay;
    CameraUpdate cameraUpdate;

    TextView tv_busStopInformation, tv_nowBusStop, tv_nextBusStop, tv_busIn, tv_busOut;
    String busNumber;
    String[] str_BusStop = new String[20];
    int int_busStopCount;

    PathOverlay path;
    Marker[] marker_D1;
    Marker[] marker_D2;
    Marker[] marker_D3;
    Marker[] marker_D4;
    Marker[] marker_D5;
    Marker[] marker_D6;
    Marker[] marker_D7;
    Marker[] marker_D8;
    Marker[] marker_D9;
    Marker[] marker_D10;
    Marker[] marker_D11;
    Marker[] marker_D12;

    Marker[] marker_TEST1;
    Marker[] marker_TEST2;

    String str_nowTime;

    // 사운드 관련 처리를 위한 요소들
    SoundPool soundPool;
    int sound;

    String str_nowBusStop;

    int int_count;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_information);

        // 사운드 처리
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        }

        sound = soundPool.load(getApplicationContext(), R.raw.bell, 1);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        busNumber = intent.getStringExtra("bus");

        LocalDate seoulNow = LocalDate.now(ZoneId.of("Asia/Seoul"));
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 포맷 적용
        String formatedNow = seoulNow.format(formatter);

        System.out.println(formatedNow + "");

        str_nowTime = formatedNow + "";

        // 경로, 포지션
        path = new PathOverlay();

        marker_D1 = new Marker[14];
        for (int i = 0; i < marker_D1.length; i++)
        {
            marker_D1[i] = new Marker();
        }

        marker_D2 = new Marker[12];
        for (int i = 0; i < marker_D2.length; i++)
        {
            marker_D2[i] = new Marker();
        }

        marker_D3 = new Marker[11];
        for (int i = 0; i < marker_D3.length; i++)
        {
            marker_D3[i] = new Marker();
        }
        marker_D4 = new Marker[16];
        for (int i = 0; i < marker_D4.length; i++)
        {
            marker_D4[i] = new Marker();
        }
        marker_D5 = new Marker[14];
        for (int i = 0; i < marker_D5.length; i++)
        {
            marker_D5[i] = new Marker();
        }
        marker_D6 = new Marker[12];
        for (int i = 0; i < marker_D6.length; i++)
        {
            marker_D6[i] = new Marker();
        }
        marker_D7 = new Marker[8];
        for (int i = 0; i < marker_D7.length; i++)
        {
            marker_D7[i] = new Marker();
        }
        marker_D8 = new Marker[12];
        for (int i = 0; i < marker_D8.length; i++)
        {
            marker_D8[i] = new Marker();
        }
        marker_D9 = new Marker[13];
        for (int i = 0; i < marker_D9.length; i++)
        {
            marker_D9[i] = new Marker();
        }
        marker_D10 = new Marker[12];
        for (int i = 0; i < marker_D10.length; i++)
        {
            marker_D10[i] = new Marker();
        }
        marker_D11 = new Marker[13];
        for (int i = 0; i < marker_D11.length; i++)
        {
            marker_D11[i] = new Marker();
        }
        marker_D12 = new Marker[15];
        for (int i = 0; i < marker_D12.length; i++)
        {
            marker_D12[i] = new Marker();
        }

        marker_TEST1 = new Marker[10];
        for (int i = 0; i < marker_TEST1.length; i++)
        {
            marker_TEST1[i] = new Marker();
        }
        marker_TEST2 = new Marker[10];
        for (int i = 0; i < marker_TEST2.length; i++)
        {
            marker_TEST2[i] = new Marker();
        }

        tv_busStopInformation = findViewById(R.id.tv_busStopInformation);
        tv_nowBusStop = findViewById(R.id.tv_nowBusStop);
        tv_nextBusStop = findViewById(R.id.tv_nextBusStop);
        tv_busIn = findViewById(R.id.tv_busIn);
        tv_busOut = findViewById(R.id.tv_busOut);

        int_busStopCount = 0;
        BusStopLoad();


        // 지도 생성
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null)
        {
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        this.naverMap = naverMap;
        locationOverlay = naverMap.getLocationOverlay();
        Location();
        DrawPath();
    }

    // 위치 권한 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1000:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
//                    Toast.makeText(this, "위치 권한 승인됨", Toast.LENGTH_LONG).show();
                }
                else
                {
//                    Toast.makeText(this, "위치 권한 거부됨", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    // 위도 경도 얻어오기
    void Location()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        { // 위치 권한 승인되었으면
            try
            {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                final LocationListener gpsLocationListener = new LocationListener()
                {
                    public void onLocationChanged(Location location) {

                        String provider = location.getProvider();
                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();
                        double altitude = location.getAltitude();

                        String str = "위도 : " + longitude + "\n" + "경도 : " + latitude + "\n" + "고도  : " + altitude;

                        locationOverlay.setVisible(true);
                        locationOverlay.setPosition(new LatLng(latitude, longitude));
                        if((longitude >= 132.237046884194 || longitude <= 123.1619757570789))
                        {
                            cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(36.35052536396161, 127.38484035032442), 11).animate(CameraAnimation.Fly, 1000);
                        }
                        else
                        {
                            cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(latitude, longitude), 15).animate(CameraAnimation.Fly, 1000);;
                        }
                        naverMap.moveCamera(cameraUpdate);

                        BusLocation(latitude, longitude);
                        BusStopData();
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };

                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, gpsLocationListener);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1, gpsLocationListener);
            }
            catch (Exception e)
            {

            }

        }
        else
        { // 위치 권한 거부됨
            double latitude = 36.32588035150573;
            double longitude = 127.33871827934472;
        }
    }

    void BusStopLoad()
    {
        String URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D1.php";

        switch (busNumber)
        {
            case "등교버스1번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D1.php";
                break;
            case "등교버스2번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D2.php";
                break;
            case "등교버스3번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D3.php";
                break;
            case "등교버스4번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D4.php";
                break;
            case "등교버스5번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D5.php";
                break;
            case "등교버스6번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D6.php";
                break;
            case "등교버스7번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_D7.php";
                break;
            case "하교버스1번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H1.php";
                break;
            case "하교버스2번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H2.php";
                break;
            case "하교버스3번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H3.php";
                break;
            case "하교버스4번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H4.php";
                break;
            case "하교버스5번" :
                URL = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city_stop_code_H5.php";
                break;

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                try
                {
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String busStop = jsonObject.getString("busStop");
                        String time = jsonObject.getString("time");

                        str_BusStop[i] = busStop + "";
                        int_busStopCount++;

                        if(str_BusStop[i] != null)
                        {
                            System.out.println("jini " + i + "번 : " + str_BusStop[i]);
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
                Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(jsonArrayRequest);
    }

    // 위치에 따른 이벤트 처리
    void BusLocation(double latitude, double longitude)
    {
        // 임시 처리
        switch (busNumber)
        {
            case "등교버스1번" :
                // 산내소방서
                if (latitude > 36.28076649224061 && latitude < 36.28328138427356 && longitude > 127.46493543678868 && longitude < 127.46972222625904)
                {
                    tv_nowBusStop.setText(str_BusStop[0]);
                    tv_nextBusStop.setText(str_BusStop[1]);
                }

                // 은어송초등학교 앞
                if (latitude > 36.29920918206583 && latitude < 36.3032197677609 && longitude > 127.45507516678663 && longitude < 127.4589508529688)
                {
                    tv_nowBusStop.setText(str_BusStop[1]);
                    tv_nextBusStop.setText(str_BusStop[2]);
                }

                // 효동 현대아파트 106동 앞
                if (latitude > 36.31456649903346 && latitude < 36.319037068538734 && longitude > 127.43787954198643 && longitude < 127.44240253935484)
                {
                    tv_nowBusStop.setText(str_BusStop[2]);
                    tv_nextBusStop.setText(str_BusStop[3]);
                }

                // 대전역 대성미용갤러리 앞
                if (latitude > 36.328966068372175 && latitude < 36.33161720577736 && longitude > 127.43063761438242 && longitude < 127.43460374439849)
                {
                    tv_nowBusStop.setText(str_BusStop[3]);
                    tv_nextBusStop.setText(str_BusStop[4]);
                }

                // 교보생명빌딩 앞
                if (latitude > 36.324167633484734 && latitude < 36.32652951462643 && longitude > 127.41883584512534 && longitude < 127.42289622945617)
                {
                    tv_nowBusStop.setText(str_BusStop[4]);
                    tv_nextBusStop.setText(str_BusStop[5]);
                }

                // 오류동 임문택약국 앞
                if (latitude > 36.31992767750674 && latitude < 36.32167634977752 && longitude > 127.40560086339107 && longitude < 127.40847051962739)
                {
                    tv_nowBusStop.setText(str_BusStop[5]);
                    tv_nextBusStop.setText(str_BusStop[6]);
                }

                // 유천동 로이젠 앞
                if (latitude > 36.31832733860286 && latitude < 36.31950808612281 && longitude > 127.39826114052228 && longitude < 127.40121959360565)
                {
                    tv_nowBusStop.setText(str_BusStop[6]);
                    tv_nextBusStop.setText(str_BusStop[7]);
                }

                // 버드내아파트 버스승강장
                if (latitude > 36.31544485939395 && latitude < 36.31677397006905 && longitude > 127.38692713725146 && longitude < 127.38967915709081)
                {
                    tv_nowBusStop.setText(str_BusStop[7]);
                    tv_nextBusStop.setText(str_BusStop[8]);
                }

                // 신원상가
                if (latitude > 36.3108004510748 && latitude < 36.31242565405585 && longitude > 127.37506229098061 && longitude < 127.37760254337392)
                {
                    tv_nowBusStop.setText(str_BusStop[8]);
                    tv_nextBusStop.setText(str_BusStop[9]);
                }

                // 정림동 고개
                if (latitude > 36.30943887226671 && latitude < 36.310588121611694 && longitude > 127.37208429975382 && longitude < 127.37380551881954)
                {
                    tv_nowBusStop.setText(str_BusStop[9]);
                    tv_nextBusStop.setText(str_BusStop[10]);
                }

                // 정림삼거리
                if (latitude > 36.30636982622623 && latitude < 36.30894822428227 && longitude > 127.36441265832126 && longitude < 127.36758249453416)
                {
                    tv_nowBusStop.setText(str_BusStop[10]);
                    tv_nextBusStop.setText(str_BusStop[11]);
                }

                // 가수원 4거리
                if (latitude > 36.304793691864106 && latitude < 36.306903449949665 && longitude > 127.3524275550858 && longitude < 127.35606514249022)
                {
                    tv_nowBusStop.setText(str_BusStop[11]);
                    tv_nextBusStop.setText(str_BusStop[12]);
                }

                // 수목토아파트
                if (latitude > 36.31595184406396 && latitude < 36.3201154075982 && longitude > 127.34632889693265 && longitude < 127.34993738786103)
                {
                    tv_nowBusStop.setText(str_BusStop[12]);
                    tv_nextBusStop.setText(str_BusStop[13]);
                }

                // 목원대학교
                if (latitude > 36.319015465166565 && latitude < 36.331957638629746 && longitude > 127.33527974119649 && longitude < 127.34165153136762)
                {
                    tv_nowBusStop.setText(str_BusStop[13]);
                    tv_nextBusStop.setText("종점");
                }
                break;
            case "등교버스2번" :
                // 법동우체국
                if (latitude > 36.36667685691265 && latitude < 36.367862274262365 && longitude > 127.42977541962942 && longitude < 127.43149233890516)
                {
                    tv_nowBusStop.setText(str_BusStop[0]);
                    tv_nextBusStop.setText(str_BusStop[1]);
                }

                // 동춘당
                if (latitude > 36.36413552298315 && latitude < 36.3661808506135 && longitude > 127.43826908757318 && longitude < 127.4411998319625)
                {
                    tv_nowBusStop.setText(str_BusStop[1]);
                    tv_nextBusStop.setText(str_BusStop[2]);
                }

                // 웰니스
                if (latitude > 36.35351674672429 && latitude < 36.357732692682255 && longitude > 127.44312886541508 && longitude < 127.44721699553841)
                {
                    tv_nowBusStop.setText(str_BusStop[2]);
                    tv_nextBusStop.setText(str_BusStop[3]);
                }

                // 대전복합
                if (latitude > 36.34806777610407 && latitude < 36.35048311514921 && longitude > 127.43485009069667 && longitude < 127.43945404059079)
                {
                    tv_nowBusStop.setText(str_BusStop[3]);
                    tv_nextBusStop.setText(str_BusStop[4]);
                }

                // 성남사거리
                if (latitude > 36.339585241852404 && latitude < 36.343129664340054 && longitude > 127.43396385220476 && longitude < 127.43705108388586)
                {
                    tv_nowBusStop.setText(str_BusStop[4]);
                    tv_nextBusStop.setText(str_BusStop[5]);
                }

                // 목동
                if (latitude > 36.331253208515385 && latitude < 36.3351018909423 && longitude > 127.40953712316109 && longitude < 127.41430480341367)
                {
                    tv_nowBusStop.setText(str_BusStop[5]);
                    tv_nextBusStop.setText(str_BusStop[6]);
                }

                // 용문동
                if (latitude > 36.33227058066145 && latitude < 36.33798254370876 && longitude > 127.39402942173238 && longitude < 127.40187063086152)
                {
                    tv_nowBusStop.setText(str_BusStop[6]);
                    tv_nextBusStop.setText(str_BusStop[7]);
                }

                // 풍전
                if (latitude > 36.34056970760391 && latitude < 36.34317363079516 && longitude > 127.38631946310524 && longitude < 127.39068545306877)
                {
                    tv_nowBusStop.setText(str_BusStop[7]);
                    tv_nextBusStop.setText(str_BusStop[8]);
                }

                // 큰마을네거리
                if (latitude > 36.346934174423886 && latitude < 36.35014181997336 && longitude > 127.37457880881497 && longitude < 127.37863307180479)
                {
                    tv_nowBusStop.setText(str_BusStop[8]);
                    tv_nextBusStop.setText(str_BusStop[9]);
                }

                // 갈마서부농협
                if (latitude > 36.3516810028034 && latitude < 36.35483369715592 && longitude > 127.36691249677892 && longitude < 127.37147964213993)
                {
                    tv_nowBusStop.setText(str_BusStop[9]);
                    tv_nextBusStop.setText(str_BusStop[10]);
                }

                // 대전일보
                if (latitude > 36.353042053184765 && latitude < 36.35466421293611 && longitude > 127.36081208205235 && longitude < 127.36567115526725)
                {
                    tv_nowBusStop.setText(str_BusStop[10]);
                    tv_nextBusStop.setText(str_BusStop[11]);
                }

                // 목원대학교
                if (latitude > 36.319015465166565 && latitude < 36.331957638629746 && longitude > 127.33527974119649 && longitude < 127.34165153136762)
                {
                    tv_nowBusStop.setText(str_BusStop[11]);
                    tv_nextBusStop.setText("종점");
                }
                break;

            case "등교버스3번" :
                // 신탄진역
                if (latitude > 36.44906626731378 && latitude < 36.45290648778754 && longitude > 127.42634350790547 && longitude < 127.43125317921637)
                {
                    tv_nowBusStop.setText(str_BusStop[0]);
                    tv_nextBusStop.setText(str_BusStop[1]);
                }

                // 신탄진 톨게이트
                if (latitude > 36.438149996301476 && latitude < 36.44207774665802 && longitude > 127.42346051190442 && longitude < 127.42922725847504)
                {
                    tv_nowBusStop.setText(str_BusStop[1]);
                    tv_nextBusStop.setText(str_BusStop[2]);
                }

                // 삼성전자 리빙프라자
                if (latitude > 36.442509539899085 && latitude < 36.444617416806985 && longitude > 127.42473506636085 && longitude < 127.4264415978605)
                {
                    tv_nowBusStop.setText(str_BusStop[2]);
                    tv_nextBusStop.setText(str_BusStop[3]);
                }

                // 목상동 파출소
                if (latitude > 36.44746944712039 && latitude < 36.449948204867255 && longitude > 127.40932220305672 && longitude < 127.41393130767285)
                {
                    tv_nowBusStop.setText(str_BusStop[3]);
                    tv_nextBusStop.setText(str_BusStop[4]);
                }

                // 송강체육관
                if (latitude > 36.4306493241381 && latitude < 36.43393692103906 && longitude > 127.3853170317534 && longitude < 127.38979964175381)
                {
                    tv_nowBusStop.setText(str_BusStop[4]);
                    tv_nextBusStop.setText(str_BusStop[5]);
                }

                // 대전테크노벨리
                if (latitude > 36.4199268256754 && latitude < 36.42392565098441 && longitude > 127.37995484451064 && longitude < 127.38395754343603)
                {
                    tv_nowBusStop.setText(str_BusStop[5]);
                    tv_nextBusStop.setText(str_BusStop[6]);
                }

                // 전민동 세종아파트
                if (latitude > 36.3992319541082 && latitude < 36.40015124121452 && longitude > 127.4012656936575 && longitude < 127.40484944197824)
                {
                    tv_nowBusStop.setText(str_BusStop[6]);
                    tv_nextBusStop.setText(str_BusStop[7]);
                }

                // 외국인유학생 기숙사
                if (latitude > 36.37692048922958 && latitude < 36.379200158272035 && longitude > 127.38926368240391 && longitude < 127.39391101589533)
                {
                    tv_nowBusStop.setText(str_BusStop[7]);
                    tv_nextBusStop.setText(str_BusStop[8]);
                }

                // 한아름아파트
                if (latitude > 36.364328977827775 && latitude < 36.36546429840076 && longitude > 127.37513044107594 && longitude < 127.37948725435662)
                {
                    tv_nowBusStop.setText(str_BusStop[8]);
                    tv_nextBusStop.setText(str_BusStop[9]);
                }

                // 유성온천
                if (latitude > 36.35215815268795 && latitude < 36.35440132002758 && longitude > 127.3404335951467 && longitude < 127.34541064527176)
                {
                    tv_nowBusStop.setText(str_BusStop[8]);
                    tv_nextBusStop.setText(str_BusStop[9]);
                }

                // 목원대학교
                if (latitude > 36.319015465166565 && latitude < 36.331957638629746 && longitude > 127.33527974119649 && longitude < 127.34165153136762)
                {
                    tv_nowBusStop.setText(str_BusStop[11]);
                    tv_nextBusStop.setText("종점");
                }
                break;

            case "등교버스4번" :
                // 도램마을
                if (latitude > 36.517343565960836 && latitude < 36.51859603818245 && longitude > 127.25839699285387 && longitude < 127.25974744974161)
                {
                    tv_nowBusStop.setText(str_BusStop[0]);
                    tv_nextBusStop.setText(str_BusStop[1]);
                }

                // 세종청사
                if (latitude > 36.499621755676394 && latitude < 36.50265760777968 && longitude > 127.25785253274148 && longitude < 127.26075543101923)
                {
                    tv_nowBusStop.setText(str_BusStop[1]);
                    tv_nextBusStop.setText(str_BusStop[2]);
                }

                // 연세에스
                if (latitude > 36.48566215440208 && latitude < 36.489087971501085 && longitude > 127.25516458392514 && longitude < 127.25909442150056)
                {
                    tv_nowBusStop.setText(str_BusStop[2]);
                    tv_nextBusStop.setText(str_BusStop[3]);
                }

                // 첫마을
                if (latitude > 36.480346052002204 && latitude < 36.482690071698485 && longitude > 127.2598211336751 && longitude < 127.26289093465797)
                {
                    tv_nowBusStop.setText(str_BusStop[3]);
                    tv_nextBusStop.setText(str_BusStop[4]);
                }

                // 세종 고속버스
                if (latitude > 36.468170890619824 && latitude < 36.469910626577615 && longitude > 127.27118142603966 && longitude < 127.27480888497519)
                {
                    tv_nowBusStop.setText(str_BusStop[4]);
                    tv_nextBusStop.setText(str_BusStop[5]);
                }

                // 반석역
                if (latitude > 36.39129838804007 && latitude < 36.39518935909336 && longitude > 127.31123360967433 && longitude < 127.31662987344664)
                {
                    tv_nowBusStop.setText(str_BusStop[5]);
                    tv_nextBusStop.setText(str_BusStop[6]);
                }

                // 휴 사우나
                if (latitude > 36.386729379339926 && latitude < 36.38875939588035 && longitude > 127.3171078148513 && longitude < 127.31910348525075)
                {
                    tv_nowBusStop.setText(str_BusStop[6]);
                    tv_nextBusStop.setText(str_BusStop[7]);
                }

                // 지족역
                if (latitude > 36.38467032214096 && latitude < 36.38638589736696 && longitude > 127.31850487030862 && longitude < 127.32086903165816)
                {
                    tv_nowBusStop.setText(str_BusStop[7]);
                    tv_nextBusStop.setText(str_BusStop[8]);
                }

                // 노은역2번
                if (latitude > 36.373763046374684 && latitude < 36.37639441350476 && longitude > 127.31724739515172 && longitude < 127.31883944305585)
                {
                    tv_nowBusStop.setText(str_BusStop[8]);
                    tv_nextBusStop.setText(str_BusStop[9]);
                }

                // 유성소방서
                if (latitude > 36.3684416588118 && latitude < 36.370289045314394 && longitude > 127.31727415784717 && longitude < 127.31853832888751)
                {
                    tv_nowBusStop.setText(str_BusStop[9]);
                    tv_nextBusStop.setText(str_BusStop[10]);
                }

                // 하이마트
                if (latitude > 36.36347990737202 && latitude < 36.36557459148256 && longitude > 127.33536810953188 && longitude < 127.33916469059747)
                {
                    tv_nowBusStop.setText(str_BusStop[10]);
                    tv_nextBusStop.setText(str_BusStop[11]);
                }

                // 유성 새마을금고
                if (latitude > 36.36065446625122 && latitude < 36.36230318793231 && longitude > 127.33505879619776 && longitude < 127.33762234547913)
                {
                    tv_nowBusStop.setText(str_BusStop[11]);
                    tv_nextBusStop.setText(str_BusStop[12]);
                }

                // 구암역
                if (latitude > 36.35588812085229 && latitude < 36.35782949778936 && longitude > 127.33027155839694 && longitude < 127.33213026061864)
                {
                    tv_nowBusStop.setText(str_BusStop[12]);
                    tv_nextBusStop.setText(str_BusStop[13]);
                }

                // 정림스토어
                if (latitude > 36.35120351289864 && latitude < 36.35202814132335 && longitude > 127.33109857582554 && longitude < 127.33254003496639)
                {
                    tv_nowBusStop.setText(str_BusStop[13]);
                    tv_nextBusStop.setText(str_BusStop[14]);
                }

                // 유성고
                if (latitude > 36.35003578420493 && latitude < 36.35155883189055 && longitude > 127.3342086854615 && longitude < 127.33612924488467)
                {
                    tv_nowBusStop.setText(str_BusStop[14]);
                    tv_nextBusStop.setText(str_BusStop[15]);
                }

                // 목원대학교
                if (latitude > 36.319015465166565 && latitude < 36.331957638629746 && longitude > 127.33527974119649 && longitude < 127.34165153136762)
                {
                    tv_nowBusStop.setText(str_BusStop[15]);
                    tv_nextBusStop.setText("종점");
                }
                break;

            case "하교버스1번" :
                // 신학대학
                if (latitude > 36.31977899352905 && latitude < 36.32962177787277 && longitude > 127.33637642545834 && longitude < 127.341101031621)
                {
                    tv_nowBusStop.setText(str_BusStop[0]);
                    tv_nextBusStop.setText(str_BusStop[1]);
                }

                // 가수원
                if (latitude > 36.30455155103852 && latitude < 36.309025562101745 && longitude > 127.3516455560165 && longitude < 127.35636883819426)
                {
                    tv_nowBusStop.setText(str_BusStop[1]);
                    tv_nextBusStop.setText(str_BusStop[2]);
                }

                // 정림동
                if (latitude > 36.30607896515553 && latitude < 36.30864011519705 && longitude > 127.35972328578941 && longitude < 127.36589744872148)
                {
                    tv_nowBusStop.setText(str_BusStop[2]);
                    tv_nextBusStop.setText(str_BusStop[3]);
                }

                // 도마동
                if (latitude > 36.31046598571165 && latitude < 36.312025635917344 && longitude > 127.37465215670666 && longitude < 127.3775052999072)
                {
                    tv_nowBusStop.setText(str_BusStop[3]);
                    tv_nextBusStop.setText(str_BusStop[4]);
                }

                // 버드내
                if (latitude > 36.31526040023996 && latitude < 36.31655694088954 && longitude > 127.38505702777694 && longitude < 127.38846231385563)
                {
                    tv_nowBusStop.setText(str_BusStop[4]);
                    tv_nextBusStop.setText(str_BusStop[5]);
                }

                // 유천동
                if (latitude > 36.317830561020784 && latitude < 36.31946218663133 && longitude > 127.39590600044343 && longitude < 127.39908230247734)
                {
                    tv_nowBusStop.setText(str_BusStop[5]);
                    tv_nextBusStop.setText(str_BusStop[6]);
                }

                // 기독교
                if (latitude > 36.320868421144 && latitude < 36.322410247907804 && longitude > 127.40722451177035 && longitude < 127.41051246281832)
                {
                    tv_nowBusStop.setText(str_BusStop[6]);
                    tv_nextBusStop.setText(str_BusStop[7]);
                }

                // 중구청
                if (latitude > 36.32257482612893 && latitude < 36.32542389934753 && longitude > 127.41500494927624 && longitude < 127.42050958309736)
                {
                    tv_nowBusStop.setText(str_BusStop[7]);
                    tv_nextBusStop.setText(str_BusStop[8]);
                }

                // 장동
                if (latitude > 36.329318857619164 && latitude < 36.33140161735163 && longitude > 127.4281904590416 && longitude < 127.43209458796889)
                {
                    tv_nowBusStop.setText(str_BusStop[8]);
                    tv_nextBusStop.setText(str_BusStop[9]);
                }

                // 효동
                if (latitude > 36.316679789901286 && latitude < 36.319654761551185 && longitude > 127.43770756069823 && longitude < 127.44163949577151)
                {
                    tv_nowBusStop.setText(str_BusStop[9]);
                    tv_nextBusStop.setText(str_BusStop[10]);
                }

                // 은어송
                if (latitude > 36.30027266765966 && latitude < 36.303391487656135 && longitude > 127.45493463296499 && longitude < 127.4573503097709)
                {
                    tv_nowBusStop.setText(str_BusStop[10]);
                    tv_nextBusStop.setText(str_BusStop[11]);
                }

                // 산내
                if (latitude > 36.280514764926984 && latitude < 36.285808576360154 && longitude > 127.46438544052073 && longitude < 127.4696415811916)
                {
                    tv_nowBusStop.setText(str_BusStop[11]);
                    tv_nextBusStop.setText("종점");
                }


            case "테스트1번" :
                // 동신빌라
                if (latitude > 36.30205691498086 && latitude < 36.30289562035214 && longitude > 127.36775245308789 && longitude < 127.36926155258882)
                {
                    tv_nowBusStop.setText("동신빌라");
                    tv_nextBusStop.setText("정림초등학교");
                }

                // 정림초등학교
                if (latitude > 36.30321669216275 && latitude < 36.30403704604803 && longitude > 127.36517298173274 && longitude < 127.36666247258661)
                {
                    tv_nowBusStop.setText("정림초등학교");
                    tv_nextBusStop.setText("정림삼거리");
                }

                // 정림삼거리
                if (latitude > 36.306321928461394 && latitude < 36.30747828951361 && longitude > 127.36419363292056 && longitude < 127.3655442800771)
                {
                    tv_nowBusStop.setText("정림삼거리");
                    tv_nextBusStop.setText("목원대학교");
                }
                break;

            case "테스트2번" :
                // 목원대학교입구
                if (latitude > 36.329155691055 && latitude < 36.3303608855219 && longitude > 36.32957115991179 && longitude < 127.33931652575328)
                {
                    tv_nowBusStop.setText("목원대학교입구");
                    tv_nextBusStop.setText("중앙로터리");
                }

                // 중앙로터리
                if (latitude > 36.326777795226484 && latitude < 36.3281998852861 && longitude > 127.33743242952323 && longitude < 127.33920957269709)
                {
                    tv_nowBusStop.setText("중앙로터리");
                    tv_nextBusStop.setText("중앙도서관");
                }

                // 중앙도서관
                if (latitude > 36.32462023645471 && latitude < 36.3262876038027 && longitude > 127.33712035923847 && longitude < 127.33901951106942)
                {
                    tv_nowBusStop.setText("중앙도서관");
                    tv_nextBusStop.setText("공과대학");
                }

                // 공과대학
                if (latitude > 36.32111059794323 && latitude < 36.32476118545646 && longitude > 127.33680943344088 && longitude < 127.34049638749629)
                {
                    tv_nowBusStop.setText("공과대학");
                    tv_nextBusStop.setText("종점");
                }
                break;

//            default:
//                // 임시로
//                tv_nowBusStop.setText(str_BusStop[0]);
//                tv_nextBusStop.setText("벗어난상태");
//                break;
        }
    }

    // 위치에 따른 데이터 불러오기
    void BusStopData()
    {
        String serverUrl = "https://as8794.cafe24.com/new_bus_clicker/get_json/get_json_bus_city.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                try
                {
                    int int_start = 0;
                    int int_end = 0;

                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = Integer.parseInt(jsonObject.getString("id"));
                        String number = jsonObject.getString("busNumber");
                        String start = jsonObject.getString("start");
                        String end = jsonObject.getString("end");
                        String date = jsonObject.getString("date");
                        String isBoarding = jsonObject.getString("isBoarding");

                        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = fm.parse(date);
                        Date date2 = fm.parse(str_nowTime);

                        int result = date1.compareTo(date2);

                        if(result == 0)
                        {
                            System.out.println("동일한 날짜");
                        }
                        else if (result < 0)
                        {
                            // System.out.println("date1은 date2 이전 날짜");
                            if(busNumber.equals(number))
                            {
                                if (isBoarding.equals("탑승 전"))
                                {
                                    Response.Listener<String> responseListener = new Response.Listener<String>()
                                    {
                                        @Override
                                        public void onResponse(String response)
                                        {
                                            try
                                            {
                                                System.out.println("hongchul" + response);
                                                JSONObject jsonObject = new JSONObject(response);
                                                boolean success = jsonObject.getBoolean("success");
                                                if (success)
                                                { // 성공

                                                } else
                                                { // 실패
                                                    Toast.makeText(getApplicationContext(), "실패",Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }

                                    };
                                    RequestUpdate requestUpdate = new RequestUpdate(id, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    queue.add(requestUpdate);
                                }
                            }
                        }
                        else
                        {
                            // System.out.println("date1은 date2 이후 날짜");
                        }


                        System.out.println("JINI " + date1 + "");

                        if(busNumber.equals(number))
                        {
                            if(str_nowTime.equals(date))
                            {
                                if (isBoarding.equals("탑승 전"))
                                {
                                    if(tv_nowBusStop.getText().equals(start))
                                    {
                                        int_start++;
                                    }

                                    if(tv_nowBusStop.getText().equals(end))
                                    {
                                        int_end++;
                                    }
                                }

                            }
                        }

                        if(!tv_nowBusStop.getText().equals("이번 정류장"))
                        {
                            if(!tv_nowBusStop.getText().equals(str_nowBusStop))
                            {
                                str_nowBusStop = tv_nowBusStop.getText().toString();
                                int_count = 0;
                            }
                        }

                        if (int_start == 0 && int_end == 0)
                        {
                            tv_busStopInformation.setTextColor(Color.BLACK);
                            tv_busStopInformation.setText("승하차 인원이 없어요");
                        }
                        else
                        {
                            tv_busStopInformation.setTextColor(Color.RED);
                            tv_busStopInformation.setText("승하차 인원이 있어요!");

                            if (int_count == 0)
                            {
                                soundPool.play(sound, 1, 1, 1, 0, 1);
                                int_count++;
                            }
                        }

                        tv_busIn.setText(int_start + "");
                        tv_busOut.setText(int_end + "");
                        System.out.println();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                } catch (ParseException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(jsonArrayRequest);
    }

    // 버스 번호 선택시 정류장 경로 그리기
    void DrawPath()
    {
        CameraUpdate cameraUpdate;

        for(int j=0; j<marker_D1.length; j++)
        {
            marker_D1[j].setMap(null);
        }
        for(int j=0; j<marker_D2.length; j++)
        {
            marker_D2[j].setMap(null);
        }
        for(int j=0; j<marker_D3.length; j++)
        {
            marker_D3[j].setMap(null);
        }
        for(int j=0; j<marker_D4.length; j++)
        {
            marker_D4[j].setMap(null);
        }
        for(int j=0; j<marker_D5.length; j++)
        {
            marker_D5[j].setMap(null);
        }
        for(int j=0; j<marker_D6.length; j++)
        {
            marker_D6[j].setMap(null);
        }
        for(int j=0; j<marker_D7.length; j++)
        {
            marker_D7[j].setMap(null);
        }
        for(int j=0; j<marker_D8.length; j++)
        {
            marker_D8[j].setMap(null);
        }
        for(int j=0; j<marker_D9.length; j++)
        {
            marker_D9[j].setMap(null);
        }
        for(int j=0; j<marker_D10.length; j++)
        {
            marker_D10[j].setMap(null);
        }
        for(int j=0; j<marker_D11.length; j++)
        {
            marker_D11[j].setMap(null);
        }
        for(int j=0; j<marker_D12.length; j++)
        {
            marker_D12[j].setMap(null);
        }

        path.setWidth(18);
        switch (busNumber)
        {
            case "등교버스1번" :
                path.setCoords(Arrays.asList(
                        // 산내파출소 -> 은어송초등학교
                        new LatLng(36.28183624070405, 127.46737007186375), // 산내파출소
                        new LatLng(36.28450532788157, 127.46580817454968),
                        new LatLng(36.28820140412254, 127.4643329583286),
                        new LatLng(36.289255174669286, 127.46332900705322),
                        new LatLng( 36.29104328826666, 127.46293604853754),
                        new LatLng( 36.29327515275369, 127.46198073311609),
                        new LatLng( 36.29733460953294, 127.4597363670063),
                        new LatLng( 36.297913768724236, 127.4591107592909),
                        new LatLng( 36.30114782228246, 127.4570672663014),
                        new LatLng(36.30158193168874, 127.45666343099215), // 은어송초등학교

                        // 은어송초등학교 -> 효동현대아파트
                        new LatLng(36.3066888507901, 127.4514601775376),
                        new LatLng(36.30791852947595, 127.45097735771459),
                        new LatLng(36.309781854812414, 127.44914532072025),
                        new LatLng(36.310965142571206, 127.44841443274758),
                        new LatLng(36.311863315166235, 127.44499839547547),
                        new LatLng(36.31160737894712, 127.4435438485553),
                        new LatLng( 36.312130269262774, 127.44227186627899),
                        new LatLng(36.31277807574104, 127.44133461979858),
                        new LatLng(36.31757431340484, 127.43957716685759),

                        // 효동현대아파트 -> 대전역 대성미용 갤러리 앞
                        new LatLng(36.321710118075195, 127.43789932937672),
                        new LatLng(36.33118277656171, 127.43259515019896),
                        new LatLng(36.33079018225903, 127.43150985374912), // 대성 미용 갤러리

                        // 대전역 대성미용 갤러리 앞 -> 교보생명빌딩
                        new LatLng(36.327226262696975, 127.42197917193401),
                        new LatLng(36.32642762296918, 127.4216518949061),
                        new LatLng(36.32467816734136, 127.41942348679284), // 교보생명

                        // 교보생명빌딩 -> 오류동 임문택약국
                        new LatLng(36.32303507035383, 127.41704818749211),
                        new LatLng(36.322775319489615, 127.4139982114473),
                        new LatLng(36.32075951156468, 127.40660986745162), // 임문택약국

                        // 임문택약국 -> 유천동 로이젠
                        new LatLng(36.31880047748452, 127.39894936363694), // 유천동 로이젠

                        // 유천동 로이젠 -> 버드내아파트 버스승강장
                        new LatLng(36.315861774797895, 127.38756789341281), // 버드내아파트 버스승강장

                        // 버드내 -> 신원상가 연산타일
                        new LatLng(36.3150922088947, 127.38448516565795),
                        new LatLng(36.31118146565379, 127.37586434970615), // 신원상가 연산타일

                        // 신원상가 -> 정림동 고개 버스승강장
                        new LatLng(36.31007471611358, 127.37244070937659), // 정림동 고개 버스승강장

                        // 정림동 고개 -> 정림동 GS주유소
                        new LatLng(36.307927055221874, 127.36533236913465), // 정림동 GS주유소

                        // 정림동 -> 가수원 4거리 현대자동차
                        new LatLng(36.30731091609318, 127.36347007853881),
                        new LatLng(36.30700311368496, 127.36095789081399),
                        new LatLng(36.30526252057788, 127.35386033174561),
                        new LatLng(36.3054372474073, 127.3534380303692),
                        new LatLng(36.3056410388587, 127.35309101258551),
                        new LatLng(36.30609668881986, 127.35290378769179), // 가수원 4거리 현대자동차

                        // 가수원 4거리 현대자동차 -> 수목토아파트 버스승강장 앞
                        new LatLng(36.31755596600299, 127.3480362437681),
                        new LatLng(36.31870067772346, 127.34796338178732), // 수목토아파트 버스승강장 앞

                        // 수목토아파트 -> 목원대학교
                        new LatLng(36.33218194866398, 127.34809293442125),
                        new LatLng(36.33218579026018, 127.3381218569649),
                        new LatLng(36.329180441683185, 127.33809216512734),
                        new LatLng(36.32668980512917, 127.3384906899361) // 목원대학교
                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D1[0].setPosition(new LatLng(36.28183624070405, 127.46737007186375));
                marker_D1[0].setCaptionText("산내소방서 앞");
                marker_D1[1].setPosition(new LatLng(36.30158193168874, 127.45666343099215));
                marker_D1[1].setCaptionText("은어송초등학교 앞");
                marker_D1[2].setPosition(new LatLng(36.31757431340484, 127.43957716685759));
                marker_D1[2].setCaptionText("효동 현대 아파트 106동 앞");
                marker_D1[3].setPosition(new LatLng(36.33079018225903, 127.43150985374912));
                marker_D1[3].setCaptionText("대전역 대성미용갤러리 앞");
                marker_D1[4].setPosition(new LatLng(36.32467816734136, 127.41942348679284));
                marker_D1[4].setCaptionText("교보생명빌딩 앞");
                marker_D1[5].setPosition(new LatLng(36.32075951156468, 127.40660986745162));
                marker_D1[5].setCaptionText("오류동 임문택약국 앞");
                marker_D1[6].setPosition(new LatLng(36.31880047748452, 127.39894936363694));
                marker_D1[6].setCaptionText("유천동 로이젠 앞");
                marker_D1[7].setPosition(new LatLng(36.315861774797895, 127.38756789341281));
                marker_D1[7].setCaptionText("버드내아파트 버스승강장");
                marker_D1[8].setPosition(new LatLng(36.31118146565379, 127.37586434970615));
                marker_D1[8].setCaptionText("신원상가 연산타일");
                marker_D1[9].setPosition(new LatLng(36.31007471611358, 127.37244070937659));
                marker_D1[9].setCaptionText("정림동 고개 버스승강장");
                marker_D1[10].setPosition(new LatLng(36.307927055221874, 127.36533236913465));
                marker_D1[10].setCaptionText("정림동 GS주유소");
                marker_D1[11].setPosition(new LatLng(36.30609668881986, 127.35290378769179));
                marker_D1[11].setCaptionText("가수원 4거리 현대자동차");
                marker_D1[12].setPosition(new LatLng(36.31870067772346, 127.34796338178732));
                marker_D1[12].setCaptionText("수목토아파트 버스승강장 앞");
                marker_D1[13].setPosition(new LatLng(36.32668980512917, 127.3384906899361));
                marker_D1[13].setCaptionText("목원대학교");

                for(int j=0; j<marker_D1.length; j++)
                {
                    marker_D1[j].setWidth(80);
                    marker_D1[j].setHeight(110);
                    marker_D1[j].setMap(naverMap);
                }

                break;
            case "등교버스2번" :
                path.setCoords(Arrays.asList(
                        // 법동우체국 앞 -> 동춘당
                        new LatLng(36.36748490152211, 127.43118032265701),
                        new LatLng( 36.36816144263912, 127.43472748729957),
                        new LatLng( 36.36814227320804, 127.43566059543625),
                        new LatLng( 36.36787840777136, 127.43635276521317),
                        new LatLng( 36.36744371681849, 127.43693533298591),
                        new LatLng( 36.36484250650533, 127.44036657410311), // 동춘당 버스승강장

                        // 동춘당 -> 웰니스요양병원
                        new LatLng(36.36380208766047, 127.44207940255139),
                        new LatLng(36.36252336329172, 127.444256013225),
                        new LatLng(36.35916309124644, 127.44878546418579),
                        new LatLng(36.35842530077192, 127.44787319847981),
                        new LatLng(36.35725340543112, 127.4467690658369),
                        new LatLng(36.355351357904276, 127.44571650932755),
                        new LatLng(36.35571346797295, 127.44467131164882),
                        new LatLng(36.35488159006224, 127.44421258565313), // 웰니스요양병원

                        // 웰니스 -> 대전복합터미널 버스승강장
                        new LatLng(36.35100795811146, 127.44200984173412),
                        new LatLng(36.348592699758, 127.43528986425684), // 대전복합터미널

                        // 대전복합터미널 -> 성남사거리 지앤지마트 앞
                        new LatLng(36.347435134214045, 127.43206398627603),
                        new LatLng(36.34069589603182, 127.43598108666299),
                        new LatLng(36.340308916860785, 127.43521870011261), // 성남사거리 지앤지마트 앞

                        // 성남사거리 -> 목동 금호한사랑아파트 건너 신협
                        new LatLng(36.33923147819407, 127.43288470815301),
                        new LatLng(36.33830675184654, 127.43004759864613),
                        new LatLng(36.33385633756935, 127.420154883677),
                        new LatLng(36.33388486922744, 127.4197290088269),
                        new LatLng(36.33396307057817, 127.41927277034432),
                        new LatLng(36.336578815037065, 127.4160260295573),
                        new LatLng(36.33460564508309, 127.41331176509172),
                        new LatLng(36.33227033029939, 127.41106630785131), // 목동 금호한사랑아파트 건너 신협

                        // 목동 -> 용문동 치안센터
                        new LatLng(36.33048246849195, 127.40946982078565),
                        new LatLng(36.3291966658014, 127.40730802009774),
                        new LatLng(36.328984385291555, 127.40613193041342),
                        new LatLng(36.328754359307005, 127.4055348929903),
                        new LatLng(36.32883255362179, 127.40506474831021),
                        new LatLng(36.3289015365385, 127.40465302580871),
                        new LatLng(36.336285247314734, 127.39566080515974), // 용문동 치안센터

                        // 용문 -> 풍전 삼계탕 앞 버스 승강장
                        new LatLng(36.33827127419957, 127.39325376835063),
                        new LatLng(36.34168243358088, 127.38906033365686),
                        new LatLng(36.3421187568452, 127.38790402984269),
                        new LatLng(36.342326737855565, 127.38768506200417),
                        new LatLng(36.342379304227535, 127.38745418391191), // 풍전 삼계탕 앞 버스 승강장

                        // 풍전 -> 큰마을4거리 버스승강장
                        new LatLng(36.3429623695062, 127.38480313515049),
                        new LatLng(36.343401642996014, 127.38271388172724),
                        new LatLng(36.34371112876777, 127.38174905286381),
                        new LatLng(36.344669247361644, 127.38014128096947),
                        new LatLng(36.34533797488704, 127.37955691556155),
                        new LatLng(36.348034417870856, 127.37752580883182),
                        new LatLng(36.34876198909548, 127.37685255876242),
                        new LatLng(36.34924625164683, 127.37617534069209), // 큰마을4거리 버스승강장

                        // 큰마을4거리 -> 갈마서부농협
                        new LatLng(36.35051846533921, 127.37352448132414),
                        new LatLng(36.35129843346201, 127.37193511747023),
                        new LatLng(36.353788718548444, 127.36804209804906), // 갈마서부농협

                        // 갈마서부 -> 대전일보 지나 육교 밑 버스승강장
                        new LatLng(36.354305756841285, 127.36400037843016),
                        new LatLng(36.353929934237506, 127.36163676503077), // 대전일보

                        // 대전일보 -> 목원대학교
                        new LatLng(36.35291976250793, 127.35667447055143),
                        new LatLng(36.35186706527443, 127.35535508063509),
                        new LatLng(36.34900368751171, 127.35150985044778),
                        new LatLng(36.34846815342205, 127.34974730856023),
                        new LatLng(36.34848687425469, 127.34796497693381),
                        new LatLng(36.34864177989999, 127.34348176826178),
                        new LatLng(36.348653060864294, 127.3403263782303),
                        new LatLng(36.3455733595687, 127.34030183663405),
                        new LatLng(36.34283634931423, 127.33939878456987),
                        new LatLng(36.33606581222207, 127.33395618154455),
                        new LatLng(36.33389213323056, 127.33300017155379),
                        new LatLng(36.33220728981613, 127.33287048549157),
                        new LatLng(36.330170710869645, 127.33283954710951),
                        new LatLng(36.33013787001504, 127.33811857663959),
                        new LatLng(36.33013787001504, 127.33811857663959),
                        new LatLng(36.329180441683185, 127.33809216512734),
                        new LatLng(36.32668980512917, 127.3384906899361) // 목원대학교
                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D2[0].setPosition(new LatLng(36.36748490152211, 127.43118032265701));
                marker_D2[0].setCaptionText("법동우체국 앞");
                marker_D2[1].setPosition(new LatLng( 36.36484250650533, 127.44036657410311));
                marker_D2[1].setCaptionText("동춘당 버스승강장");
                marker_D2[2].setPosition(new LatLng(36.35488159006224, 127.44421258565313));
                marker_D2[2].setCaptionText("웰니스요양병원");
                marker_D2[3].setPosition(new LatLng(36.348592699758, 127.43528986425684));
                marker_D2[3].setCaptionText("대전복합터미널 버스승강장");
                marker_D2[4].setPosition(new LatLng(36.340308916860785, 127.43521870011261));
                marker_D2[4].setCaptionText("성남사거리 지앤지마트 앞");
                marker_D2[5].setPosition(new LatLng(36.33227033029939, 127.41106630785131));
                marker_D2[5].setCaptionText("목동 금호한사랑아파트 건너 신협");
                marker_D2[6].setPosition(new LatLng(36.336285247314734, 127.39566080515974));
                marker_D2[6].setCaptionText("용문동 치안센터");
                marker_D2[7].setPosition(new LatLng(36.342379304227535, 127.38745418391191));
                marker_D2[7].setCaptionText("풍전 삼계탕 앞 버스 승강장");
                marker_D2[8].setPosition(new LatLng(36.34924625164683, 127.37617534069209));
                marker_D2[8].setCaptionText("큰마을4거리 버스승강장");
                marker_D2[9].setPosition(new LatLng(36.353788718548444, 127.36804209804906));
                marker_D2[9].setCaptionText("갈마서부농협");
                marker_D2[10].setPosition(new LatLng(36.353929934237506, 127.36163676503077));
                marker_D2[10].setCaptionText("대전일보 지나 육교 밑 버스승강장");
                marker_D2[11].setPosition(new LatLng(36.32668980512917, 127.3384906899361));
                marker_D2[11].setCaptionText("목원대학교");

                for(int j=0; j< marker_D2.length; j++)
                {
                    marker_D2[j].setWidth(80);
                    marker_D2[j].setHeight(110);
                    marker_D2[j].setMap(naverMap);
                }
                break;

            case "등교버스3번" :
                path.setCoords(Arrays.asList(
                        //new LatLng(36.4554, 127.4283), // 산내소방서
                        //new LatLng(36.451, 127.4288), // 은어송초등학교
                        new LatLng(36.4474, 127.4294),
                        new LatLng(36.4411, 127.4287),
                        new LatLng(36.4397, 127.427),
                        new LatLng(36.4389, 127.4267),
                        new LatLng(36.4392, 127.4235),
                        new LatLng(36.4441, 127.4257),
                        new LatLng(36.4452, 127.4238),
                        new LatLng(36.4461, 127.4223),
                        new LatLng(36.4475, 127.4213),
                        new LatLng(36.4488, 127.4191),
                        new LatLng(36.4496, 127.4155),
                        new LatLng(36.4496, 127.4133),
                        new LatLng(36.4474, 127.4088),
                        new LatLng(36.4407, 127.3988),
                        new LatLng(36.4396, 127.3963),
                        new LatLng(36.4342, 127.3888),
                        new LatLng(36.4231, 127.3833),
                        new LatLng(36.4198, 127.3803),
                        new LatLng(36.4172, 127.3778),
                        new LatLng(36.4121, 127.3781),
                        new LatLng(36.4103, 127.3774),
                        new LatLng(36.4081, 127.3768),
                        new LatLng(36.4093, 127.3793),
                        new LatLng(36.4092, 127.3805),
                        new LatLng(36.4047, 127.3873),
                        new LatLng(36.4022, 127.3894),
                        new LatLng(36.4012, 127.3906),
                        new LatLng(36.4003, 127.3921),
                        new LatLng(36.3997, 127.395),
                        new LatLng(36.3997, 127.4047),
                        new LatLng(36.3866, 127.405),
                        new LatLng(36.3853, 127.4052),
                        new LatLng(36.3802, 127.4067),
                        new LatLng(36.3783, 127.4055),
                        new LatLng(36.3775, 127.4048),
                        new LatLng(36.3772, 127.4043),
                        new LatLng(36.3763, 127.4014),
                        new LatLng(36.37590012852339, 127.39926026368),
                        new LatLng(36.376132536907335, 127.39847858420393),
                        new LatLng(36.37843105055203, 127.39356172163981),
                        new LatLng(36.37909853880058, 127.39200207647399),
                        new LatLng(36.379170879824414, 127.3905508742993),
                        new LatLng(36.377679658410784, 127.39048491135274),
                        new LatLng(36.37691344305857, 127.38986258190424),
                        new LatLng(36.376048042274185, 127.38994741514429),
                        new LatLng(36.37550789982877, 127.39046569784982),
                        new LatLng(36.37428207237552, 127.39053757748937),
                        new LatLng(36.37439848904898, 127.39350797507396),
                        new LatLng(36.3671, 127.3934),
                        new LatLng(36.365, 127.3935),
                        new LatLng(36.3649, 127.3833),
                        new LatLng(36.365, 127.3795),
                        new LatLng(36.3651, 127.3767),
                        new LatLng(36.3649, 127.3707),
                        new LatLng(36.3652, 127.3695),
                        new LatLng(36.3623, 127.3643),
                        new LatLng(36.361, 127.3622),
                        new LatLng(36.359, 127.3602),
                        new LatLng(36.3576, 127.3588),
                        new LatLng(36.3533, 127.3526),
                        new LatLng(36.3521, 127.3516),
                        new LatLng(36.3512, 127.3511),
                        new LatLng(36.3538, 127.3415),
                        new LatLng(36.351, 127.3402),
                        new LatLng(36.3487, 127.3402),
                        new LatLng(36.3459, 127.3402),
                        new LatLng(36.3444, 127.3399),
                        new LatLng(36.343, 127.3393),
                        new LatLng(36.3382, 127.3355),
                        new LatLng(36.3382, 127.3382),
                        new LatLng(36.3301, 127.3382)
                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D3[0].setPosition(new LatLng(36.4496, 127.4291));
                marker_D3[0].setCaptionText("신탄진역");
                marker_D3[1].setPosition(new LatLng( 36.4392, 127.4246));
                marker_D3[1].setCaptionText("신탄진 톨게이트 전 주공Ⓐ");
                marker_D3[2].setPosition(new LatLng(36.4444, 127.4253));
                marker_D3[2].setCaptionText("삼성전자 리빙프라자");
                marker_D3[3].setPosition(new LatLng(36.4486, 127.4111));
                marker_D3[3].setCaptionText("묵상동 파출소 건너");
                marker_D3[4].setPosition(new LatLng(36.4312, 127.3871));
                marker_D3[4].setCaptionText("송강체육관 앞 버스승강장");
                marker_D3[5].setPosition(new LatLng(36.4211, 127.3813));
                marker_D3[5].setCaptionText("대전테크노벨리 날망집 버스승강장");
                marker_D3[6].setPosition(new LatLng(36.3996, 127.4037));
                marker_D3[6].setCaptionText("전민동 세종Ⓐ 건너편");
                marker_D3[7].setPosition(new LatLng(36.3775, 127.3902));
                marker_D3[7].setCaptionText("외국인 유학생 기숙사");
                marker_D3[8].setPosition(new LatLng(36.36507462586364, 127.37569717712056));
                marker_D3[8].setCaptionText("한아름Ⓐ 102동 건너 만년 버스승강장");
                marker_D3[9].setPosition(new LatLng(36.353138749663245, 127.34108676286755));
                marker_D3[9].setCaptionText("유성온천역 3번출구");
                marker_D3[10].setPosition(new LatLng(36.32668980512917, 127.3384906899361));
                marker_D3[10].setCaptionText("목원대학교");

                for(int j=0; j< marker_D3.length; j++)
                {
                    marker_D3[j].setWidth(80);
                    marker_D3[j].setHeight(110);
                    marker_D3[j].setMap(naverMap);
                }
                break;



            case "등교버스4번" :
                path.setCoords(Arrays.asList(
                        new LatLng(36.5135, 127.2591),
                        new LatLng(36.5121, 127.2595),
                        new LatLng(36.5112, 127.26),
                        new LatLng(36.5098, 127.2609),
                        new LatLng(36.5086, 127.2614),
                        new LatLng(36.5078, 127.2617),
                        new LatLng(36.5068, 127.2618),
                        new LatLng(36.5046, 127.2612),
                        new LatLng(36.4998, 127.2589),
                        new LatLng(36.4974, 127.2574),
                        new LatLng(36.4944, 127.2563),
                        new LatLng(36.4912, 127.2563),
                        new LatLng(36.4892, 127.2567),
                        new LatLng(36.486, 127.2582),
                        new LatLng(36.484, 127.2596),
                        new LatLng(36.4723, 127.268),
                        new LatLng(36.4717, 127.2686),
                        new LatLng(36.4711, 127.2699),
                        new LatLng(36.4692, 127.269),
                        new LatLng(36.4687, 127.2719),
                        new LatLng(36.4692, 127.2747),
                        new LatLng(36.4647, 127.2753),
                        new LatLng(36.4625, 127.2763),
                        new LatLng(36.4568, 127.2821),
                        new LatLng(36.4554, 127.2833),
                        new LatLng(36.4538, 127.2842),
                        new LatLng(36.452, 127.285),
                        new LatLng(36.4502, 127.2855),
                        new LatLng(36.4487, 127.2857),
                        new LatLng(36.4389, 127.2857),
                        new LatLng(36.4372, 127.2855),
                        new LatLng(36.4362, 127.2849),
                        new LatLng(36.4355, 127.2838),
                        new LatLng(36.4355, 127.2859),
                        new LatLng(36.4332, 127.2861),
                        new LatLng(36.4304, 127.287),
                        new LatLng(36.4284, 127.2888),
                        new LatLng(36.4233, 127.2956),
                        new LatLng(36.4221, 127.2972),
                        new LatLng(36.4207, 127.2982),
                        new LatLng(36.4188, 127.2989),
                        new LatLng(36.4151, 127.2996),
                        new LatLng(36.4103, 127.3012),
                        new LatLng(36.406, 127.304),
                        new LatLng(36.3996, 127.3097),
                        new LatLng(36.3959, 127.3117),
                        new LatLng(36.3843, 127.3203),
                        new LatLng(36.3806, 127.3181),
                        new LatLng(36.3797, 127.3179),
                        new LatLng(36.3668, 127.3179),
                        new LatLng(36.3667, 127.3307),
                        new LatLng(36.3643, 127.3378),
                        new LatLng(36.364, 127.3378),
                        new LatLng(36.3625, 127.3369),
                        new LatLng(36.3607, 127.3357),
                        new LatLng(36.3578, 127.3318),
                        new LatLng(36.3542, 127.3303),
                        new LatLng(36.3524, 127.3291),
                        new LatLng(36.3508, 127.335),
                        new LatLng(36.3502, 127.34),
                        new LatLng(36.3486, 127.3402),
                        new LatLng(36.3459, 127.3401),
                        new LatLng(36.343, 127.3394),
                        new LatLng(36.3382, 127.3354),
                        new LatLng(36.3382, 127.3382),
                        new LatLng(36.3292, 127.3382)
                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D4[0].setPosition(new LatLng(36.5179, 127.2588));
                marker_D4[0].setCaptionText("도램마을 지선버스 승강장(안경마을 앞)");
                marker_D4[1].setPosition(new LatLng( 36.5008, 127.2593));
                marker_D4[1].setCaptionText("세종청사 남측");
                marker_D4[2].setPosition(new LatLng(36.4877, 127.2571));
                marker_D4[2].setCaptionText("연세에스의원 피부과 앞");
                marker_D4[3].setPosition(new LatLng(36.4811, 127.2616));
                marker_D4[3].setCaptionText("첫마을 2단지버스승강장");
                marker_D4[4].setPosition(new LatLng(36.4688, 127.2735));
                marker_D4[4].setCaptionText("세종고속버스터미널");
                marker_D4[5].setPosition(new LatLng(36.3925, 127.3141));
                marker_D4[5].setCaptionText("반석역 4번 출구");
                marker_D4[6].setPosition(new LatLng(36.3866, 127.3184));
                marker_D4[6].setCaptionText("휴 사우나 앞 버스승강장");
                marker_D4[7].setPosition(new LatLng(36.3848, 127.3198));
                marker_D4[7].setCaptionText("지족역(신학대학 출구)");
                marker_D4[8].setPosition(new LatLng(36.3744, 127.3178));
                marker_D4[8].setCaptionText("노은역 2번 출구");
                marker_D4[9].setPosition(new LatLng(36.3689, 127.3177));
                marker_D4[9].setCaptionText("대전유성소방서 노은119 버스승강장");
                marker_D4[10].setPosition(new LatLng(36.3636, 127.3375));
                marker_D4[10].setCaptionText("하이마트 건너 밀레유성점 앞");
                marker_D4[11].setPosition(new LatLng(36.3614, 127.3361));
                marker_D4[11].setCaptionText("유성 새마을금고 앞 버스승강장");
                marker_D4[12].setPosition(new LatLng(36.356, 127.331));
                marker_D4[12].setCaptionText("구암역 지나 버스승강장");
                marker_D4[13].setPosition(new LatLng(36.3515, 127.3322));
                marker_D4[13].setCaptionText("정림스토어 건너 김밥천국");
                marker_D4[14].setPosition(new LatLng(36.3506, 127.3358));
                marker_D4[14].setCaptionText("유성고등학교 앞");
                marker_D4[15].setPosition(new LatLng(36.32668980512917, 127.3384906899361));
                marker_D4[15].setCaptionText("목원대학교");


                for(int j=0; j< marker_D4.length; j++)
                {
                    marker_D4[j].setWidth(80);
                    marker_D4[j].setHeight(110);
                    marker_D4[j].setMap(naverMap);
                }
                break;

            case "등교버스5번" :
                path.setCoords(Arrays.asList(
                        new LatLng(36.2824, 127.2393),
                        new LatLng(36.2885, 127.2417),
                        new LatLng(36.288, 127.2436),
                        new LatLng(36.288, 127.2441),
                        new LatLng(36.2885, 127.245),
                        new LatLng(36.2931, 127.2426),
                        new LatLng(36.2936, 127.2428),
                        new LatLng(36.2944, 127.2438),
                        new LatLng(36.295, 127.2449),
                        new LatLng(36.2957, 127.246),
                        new LatLng(36.2967, 127.2462),
                        new LatLng(36.2999, 127.2423),
                        new LatLng(36.2999, 127.2359),
                        new LatLng(36.2988, 127.2359),
                        new LatLng(36.2982, 127.2363),
                        new LatLng(36.2973, 127.2377),
                        new LatLng(36.2946, 127.2413),
                        new LatLng(36.2935, 127.2423),
                        new LatLng(36.2884, 127.2449),
                        new LatLng(36.2845, 127.2474),
                        new LatLng(36.2825, 127.25),
                        new LatLng(36.2819, 127.2506),
                        new LatLng(36.2786, 127.2518),
                        new LatLng(36.2763, 127.2526),
                        new LatLng(36.2757, 127.2533),
                        new LatLng(36.2726, 127.25),
                        new LatLng(36.2721, 127.2508),
                        new LatLng(36.2721, 127.2513),
                        new LatLng(36.2727, 127.2521),
                        new LatLng(36.2726, 127.2533),
                        new LatLng(36.2712, 127.2554),
                        new LatLng(36.2712, 127.2554),
                        new LatLng(36.2705, 127.2586),
                        new LatLng(36.2679, 127.2602),
                        new LatLng(36.2688, 127.2625),
                        new LatLng(36.2687, 127.2633),
                        new LatLng(36.2684, 127.2642),
                        new LatLng(36.2684, 127.2673),
                        new LatLng(36.2683, 127.2685),
                        new LatLng(36.2672, 127.2706),
                        new LatLng(36.265, 127.2737),
                        new LatLng(36.2624, 127.2756),
                        new LatLng(36.2629, 127.2775),
                        new LatLng(36.2635, 127.2786),
                        new LatLng(36.2668, 127.2814),
                        new LatLng(36.2668, 127.283),
                        new LatLng(36.2667, 127.2871),
                        new LatLng(36.2669, 127.2886),
                        new LatLng(36.2677, 127.29),
                        new LatLng(36.2688, 127.2907),
                        new LatLng(36.2722, 127.2911),
                        new LatLng(36.2737, 127.2914),
                        new LatLng(36.2776, 127.2944),
                        new LatLng(36.2787, 127.2959),
                        new LatLng(36.2793, 127.2973),
                        new LatLng(36.2799, 127.3),
                        new LatLng(36.2803, 127.301),
                        new LatLng(36.2812, 127.3026),
                        new LatLng(36.2815, 127.3044),
                        new LatLng(36.2817, 127.3062),
                        new LatLng(36.2834, 127.3096),
                        new LatLng(36.285, 127.3109),
                        new LatLng(36.2856, 127.3118),
                        new LatLng(36.2858, 127.3129),
                        new LatLng(36.2857, 127.3162),
                        new LatLng(36.2859, 127.3172),
                        new LatLng(36.2873, 127.3194),
                        new LatLng(36.2882, 127.3199),
                        new LatLng(36.2892, 127.3201),
                        new LatLng(36.2917, 127.3201),
                        new LatLng(36.2966, 127.322),
                        new LatLng(36.2984, 127.3234),
                        new LatLng(36.2985, 127.324),
                        new LatLng(36.2966, 127.3275),
                        new LatLng(36.2962, 127.33),
                        new LatLng(36.2964, 127.3347),
                        new LatLng(36.3013, 127.335),
                        new LatLng(36.3014, 127.3365),
                        new LatLng(36.302, 127.3388),
                        new LatLng(36.3031, 127.3443),
                        new LatLng(36.304, 127.3488),
                        new LatLng(36.3052, 127.3534),
                        new LatLng(36.3167, 127.3485),
                        new LatLng(36.3177, 127.3481),
                        new LatLng(36.3323, 127.3483),
                        new LatLng(36.3323, 127.3381),
                        new LatLng(36.3292, 127.3381)
                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D5[0].setPosition(new LatLng(36.2824, 127.2394));
                marker_D5[0].setCaptionText("하나로마트 건너");
                marker_D5[1].setPosition(new LatLng( 36.2863, 127.2409));
                marker_D5[1].setCaptionText("사계절마트(구, K-마트)");
                marker_D5[2].setPosition(new LatLng(36.2972, 127.2456));
                marker_D5[2].setCaptionText("삼위일체 성당");
                marker_D5[3].setPosition(new LatLng(36.2999, 127.2413));
                marker_D5[3].setCaptionText("초록델리 앞");
                marker_D5[4].setPosition(new LatLng(36.2723, 127.2504));
                marker_D5[4].setCaptionText("계룡보건소 버스승강장");
                marker_D5[5].setPosition(new LatLng(36.2696, 127.2566));
                marker_D5[5].setCaptionText("금암주공Ⓐ 203동 앞");
                marker_D5[6].setPosition(new LatLng(36.2673, 127.2703));
                marker_D5[6].setCaptionText("포스코더샵Ⓐ 정문 건너");
                marker_D5[7].setPosition(new LatLng(36.2982, 127.3244));
                marker_D5[7].setCaptionText("진잠4거리 버스승강장 앞");
                marker_D5[8].setPosition(new LatLng(36.2962, 127.3316));
                marker_D5[8].setCaptionText("구봉마을Ⓐ 9단지 육교 승강장");
                marker_D5[9].setPosition(new LatLng(36.2971, 127.3349));
                marker_D5[9].setCaptionText("관저2동 주민센터 앞");
                marker_D5[10].setPosition(new LatLng(36.3019, 127.3389));
                marker_D5[10].setCaptionText("KT건너 인도 지하차도 입구");
                marker_D5[11].setPosition(new LatLng(36.3028, 127.3435));
                marker_D5[11].setCaptionText("느리울 11단지 옆 버스승강장");
                marker_D5[12].setPosition(new LatLng(36.3036, 127.3469));
                marker_D5[12].setCaptionText("가수원 더맑음웨딩 앞 버스승강장");
                marker_D5[13].setPosition(new LatLng(36.32668980512917, 127.3384906899361));
                marker_D5[13].setCaptionText("목원대학교");

                for(int j=0; j< marker_D5.length; j++)
                {
                    marker_D5[j].setWidth(80);
                    marker_D5[j].setHeight(110);
                    marker_D5[j].setMap(naverMap);
                }
                break;

            case "등교버스6번" :
                path.setCoords(Arrays.asList(
                        new LatLng(36.3608, 127.3962),
                        new LatLng(36.3598, 127.3953),
                        new LatLng(36.349, 127.3952),
                        new LatLng(36.349, 127.3903),
                        new LatLng(36.3579, 127.3903),
                        new LatLng(36.3579, 127.3687),
                        new LatLng(36.3587, 127.3616),
                        new LatLng(36.3603, 127.3505),
                        new LatLng(36.3609, 127.3479),
                        new LatLng(36.3622, 127.3447),
                        new LatLng(36.3509, 127.3402),
                        new LatLng(36.3487, 127.3401),
                        new LatLng(36.3459, 127.3401),
                        new LatLng(36.3448, 127.34),
                        new LatLng(36.343, 127.3394),
                        new LatLng(36.3364, 127.3339),
                        new LatLng(36.3346, 127.333),
                        new LatLng(36.3321, 127.3326),
                        new LatLng(36.3321, 127.3381),
                        new LatLng(36.3292, 127.3381)

                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D6[0].setPosition(new LatLng(36.3604, 127.3959));
                marker_D6[0].setCaptionText("샘머리Ⓐ 222동 앞 하나로축산");
                marker_D6[1].setPosition(new LatLng( 36.355, 127.3952));
                marker_D6[1].setCaptionText("한밭초등학교 버스승강장");
                marker_D6[2].setPosition(new LatLng(36.3504, 127.3951));
                marker_D6[2].setCaptionText("탄방중학교 정문");
                marker_D6[3].setPosition(new LatLng(36.3499, 127.3902));
                marker_D6[3].setCaptionText("목련Ⓐ 102동 앞 버스승강장");
                marker_D6[4].setPosition(new LatLng(36.3544, 127.3904));
                marker_D6[4].setCaptionText("한마루 삼성Ⓐ 7동옆 버스승강장");
                marker_D6[5].setPosition(new LatLng(36.3579, 127.3893));
                marker_D6[5].setCaptionText("사학연금회관");
                marker_D6[6].setPosition(new LatLng(36.358, 127.3749));
                marker_D6[6].setCaptionText("무지개Ⓐ 104동");
                marker_D6[7].setPosition(new LatLng(36.358, 127.3687));
                marker_D6[7].setCaptionText("누리Ⓐ 104동");
                marker_D6[8].setPosition(new LatLng(36.3613, 127.3473));
                marker_D6[8].setCaptionText("궁동 다솔Ⓐ 버스승강장");
                marker_D6[9].setPosition(new LatLng(36.3636, 127.3375));
                marker_D6[9].setCaptionText("충대정문 건너 대전교회 학생센터 앞");
                marker_D6[10].setPosition(new LatLng(36.3534, 127.3412));
                marker_D6[10].setCaptionText("유성온천역 3번 출구");
                marker_D6[11].setPosition(new LatLng(36.32668980512917, 127.3384906899361));
                marker_D6[11].setCaptionText("목원대학교");

                for(int j=0; j< marker_D6.length; j++)
                {
                    marker_D6[j].setWidth(80);
                    marker_D6[j].setHeight(110);
                    marker_D6[j].setMap(naverMap);
                }
                break;

            case "등교버스7번" :
                path.setCoords(Arrays.asList(
                        new LatLng(36.3613, 127.356),
                        new LatLng(36.3604, 127.3527),
                        new LatLng(36.3605, 127.3503),
                        new LatLng(36.3622, 127.3452),
                        new LatLng(36.3622, 127.3446),
                        new LatLng(36.3594, 127.3434),
                        new LatLng(36.3599, 127.3416),
                        new LatLng(36.362, 127.3366),
                        new LatLng(36.3607, 127.3357),
                        new LatLng(36.3597, 127.3361),
                        new LatLng(36.3551, 127.3367),
                        new LatLng(36.3537, 127.3414),
                        new LatLng(36.3502, 127.34),
                        new LatLng(36.3487, 127.3402),
                        new LatLng(36.3458, 127.3402),
                        new LatLng(36.3429, 127.3392),
                        new LatLng(36.3382, 127.3355),
                        new LatLng(36.3362, 127.3337),
                        new LatLng(36.3346, 127.3331),
                        new LatLng(36.3321, 127.3326),
                        new LatLng(36.332, 127.3381),
                        new LatLng(36.332, 127.3381)

                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D7[0].setPosition(new LatLng(36.3614, 127.3561));
                marker_D7[0].setCaptionText("유성구청 앞");
                marker_D7[1].setPosition(new LatLng( 36.3607, 127.3498));
                marker_D7[1].setCaptionText("궁동 대학로 마트 앞(소비자마트)");
                marker_D7[2].setPosition(new LatLng(36.3602, 127.3409));
                marker_D7[2].setCaptionText("유성문화원 건너");
                marker_D7[3].setPosition(new LatLng(36.3615,127.3379));
                marker_D7[3].setCaptionText("드림월드Ⓐ 111동 앞 드림슈퍼");
                marker_D7[4].setPosition(new LatLng(36.3602, 127.3359));
                marker_D7[4].setCaptionText("금호고속 건너 태전마트 앞");
                marker_D7[5].setPosition(new LatLng(36.3549, 127.3373));
                marker_D7[5].setCaptionText("유성시외버스터미널 건너 롯데리아");
                marker_D7[6].setPosition(new LatLng(36.3534, 127.3412));
                marker_D7[6].setCaptionText("유성온천역 3번 출구");
                marker_D7[7].setPosition(new LatLng(36.32668980512917, 127.3384906899361));
                marker_D7[7].setCaptionText("목원대학교");


                for(int j=0; j< marker_D7.length; j++)
                {
                    marker_D7[j].setWidth(80);
                    marker_D7[j].setHeight(110);
                    marker_D7[j].setMap(naverMap);
                }
                break;

            case "하교버스1번" :
                path.setCoords(Arrays.asList(
                        new LatLng(36.3273, 127.3374),
                        new LatLng(36.3274, 127.3382),
                        new LatLng(36.3276, 127.3383),
                        new LatLng(36.3286, 127.3381),
                        new LatLng(36.3291, 127.3381),
                        new LatLng(36.3321, 127.3382),
                        new LatLng(36.3321, 127.344),
                        new LatLng(36.332, 127.3447),
                        new LatLng(36.332, 127.3479),
                        new LatLng(36.3186, 127.3478),
                        new LatLng(36.3176, 127.3479),
                        new LatLng(36.3172, 127.348),
                        new LatLng(36.3051, 127.3532),
                        new LatLng(36.3068, 127.3604),
                        new LatLng(36.307, 127.3611),
                        new LatLng(36.3072, 127.363),
                        new LatLng(36.3073, 127.3636),
                        new LatLng(36.308, 127.3658),
                        new LatLng(36.3084, 127.367),
                        new LatLng(36.3093, 127.3693),
                        new LatLng(36.3097, 127.3719),
                        new LatLng(36.3101, 127.3726),
                        new LatLng(36.3105, 127.3745),
                        new LatLng(36.3107, 127.3749),
                        new LatLng(36.3149, 127.3843),
                        new LatLng(36.3166, 127.3906),
                        new LatLng(36.3223, 127.4123),
                        new LatLng(36.3227,127.414),
                        new LatLng(36.323, 127.4168),
                        new LatLng(36.3236, 127.418),
                        new LatLng(36.3262, 127.4214),
                        new LatLng(36.3273, 127.4214),
                        new LatLng(36.3311, 127.4326),
                        new LatLng(36.3217, 127.4378),
                        new LatLng(36.3132, 127.4411),
                        new LatLng(36.3126, 127.4415),
                        new LatLng(36.3116, 127.4432),
                        new LatLng(36.3116, 127.4437),
                        new LatLng(36.3118, 127.4449),
                        new LatLng(36.3118, 127.4454),
                        new LatLng(36.3112, 127.4472),
                        new LatLng(36.3109, 127.4483),
                        new LatLng(36.3107, 127.4485),
                        new LatLng(36.3097, 127.4492),
                        new LatLng(36.3066, 127.4514),
                        new LatLng(36.3025, 127.4556),
                        new LatLng(36.3015, 127.4568),
                        new LatLng(36.301, 127.4572),
                        new LatLng(36.2979, 127.459),
                        new LatLng(36.2973, 127.4597),
                        new LatLng(36.2963, 127.4603),
                        new LatLng(36.296, 127.4603),
                        new LatLng(36.2958, 127.4604),
                        new LatLng(36.2927, 127.4623),
                        new LatLng(36.2901, 127.4632),
                        new LatLng(36.2892, 127.4632),
                        new LatLng(36.2883, 127.4643),
                        new LatLng(36.2872, 127.4648),
                        new LatLng(36.2858, 127.4652),
                        new LatLng(36.2839, 127.4662),
                        new LatLng(36.2817, 127.4673)

                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D8[0].setPosition(new LatLng(36.3272, 127.3376));
                marker_D8[0].setCaptionText("신학대학주차장");
                marker_D8[1].setPosition(new LatLng( 36.3052, 127.3538));
                marker_D8[1].setCaptionText("가수원네거리");
                marker_D8[2].setPosition(new LatLng(36.3073, 127.364));
                marker_D8[2].setCaptionText("정림동 반도패션");
                marker_D8[3].setPosition(new LatLng(36.3118, 127.3775));
                marker_D8[3].setCaptionText("도마동 유가옥설렁탕");
                marker_D8[4].setPosition(new LatLng(36.3159, 127.388));
                marker_D8[4].setCaptionText("버드내 육교밑");
                marker_D8[5].setPosition(new LatLng(36.3184, 127.3979));
                marker_D8[5].setCaptionText("유천동 국민은행");
                marker_D8[6].setPosition(new LatLng(36.3215, 127.4096));
                marker_D8[6].setCaptionText("기독교 봉사회관");
                marker_D8[7].setPosition(new LatLng(36.3244, 127.4191));
                marker_D8[7].setCaptionText("중구청 2번 출구");
                marker_D8[8].setPosition(new LatLng(36.3305, 127.431));
                marker_D8[8].setCaptionText("정동 다비치안경");
                marker_D8[9].setPosition(new LatLng(36.318, 127.4394));
                marker_D8[9].setCaptionText("효동 현대");
                marker_D8[10].setPosition(new LatLng(36.3015, 127.4567));
                marker_D8[10].setCaptionText("은어송초등학교 건너");
                marker_D8[11].setPosition(new LatLng(36.2817, 127.4673));
                marker_D8[11].setCaptionText("산내소방서");


                for(int j=0; j< marker_D8.length; j++)
                {
                    marker_D8[j].setWidth(80);
                    marker_D8[j].setHeight(110);
                    marker_D8[j].setMap(naverMap);
                }

                cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(36.3272, 127.3376), 11).animate(CameraAnimation.Fly, 1000);;
                naverMap.moveCamera(cameraUpdate);
                break;
            case "하교버스2번" :
                path.setCoords(Arrays.asList(
                        new LatLng(36.3273, 127.3374),
                        new LatLng(36.3274, 127.3382),
                        new LatLng(36.3276, 127.3383),
                        new LatLng(36.3286, 127.3381),
                        new LatLng(36.3291, 127.3381),
                        new LatLng(36.3273, 127.3374),
                        new LatLng(36.3274, 127.3382),
                        new LatLng(36.3276, 127.3383),
                        new LatLng(36.3286, 127.3381),
                        new LatLng(36.3291, 127.3381),
                        new LatLng(36.3324, 127.3381),
                        new LatLng(36.3324, 127.3331),
                        new LatLng(36.334, 127.3332),
                        new LatLng(36.336, 127.334),
                        new LatLng(36.3428,127.3396),
                        new LatLng(36.3458, 127.3405),
                        new LatLng(36.349, 127.3405),
                        new LatLng(36.3503, 127.3403),
                        new LatLng(36.3536, 127.3416),
                        new LatLng(36.351, 127.3514),
                        new LatLng(36.3507, 127.353),
                        new LatLng(36.3505, 127.3539),
                        new LatLng(36.3519, 127.3557),
                        new LatLng(36.3525, 127.3562),
                        new LatLng(36.353, 127.3571),
                        new LatLng(36.3542, 127.3638),
                        new LatLng(36.3541, 127.3665),
                        new LatLng(36.3537, 127.3676),
                        new LatLng(36.3488, 127.3763),
                        new LatLng(36.3483, 127.3769),
                        new LatLng(36.3446, 127.3798),
                        new LatLng(36.3442, 127.3803),
                        new LatLng(36.3437, 127.3811),
                        new LatLng(36.3433, 127.382),
                        new LatLng(36.3418, 127.3884),
                        new LatLng(36.3415, 127.3892),
                        new LatLng(36.3285, 127.4051),
                        new LatLng(36.329, 127.4065),
                        new LatLng(36.3292, 127.4074),
                        new LatLng(36.3294, 127.4083),
                        new LatLng(36.3304, 127.4095),
                        new LatLng(36.3363, 127.4158),
                        new LatLng(36.3423, 127.4234),
                        new LatLng(36.3434, 127.4266),
                        new LatLng(36.3453, 127.4295),
                        new LatLng(36.3472, 127.4319),
                        new LatLng(36.3475, 127.4324),
                        new LatLng(36.351, 127.4419),
                        new LatLng(36.3515, 127.4424),
                        new LatLng(36.3576, 127.4459),
                        new LatLng(36.3574, 127.4464),
                        new LatLng(36.3595, 127.4483),
                        new LatLng(36.3625, 127.4443),
                        new LatLng(36.3638,127.4421),
                        new LatLng(36.3679,127.4364),
                        new LatLng(36.3682, 127.4351),
                        new LatLng(36.3675, 127.4309)

                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D9[0].setPosition(new LatLng(36.3271, 127.3376));
                marker_D9[0].setCaptionText("신학대학주차장");
                marker_D9[1].setPosition(new LatLng( 36.3532, 127.3415));
                marker_D9[1].setCaptionText("유성온천역 4거리");
                marker_D9[2].setPosition(new LatLng(36.353,127.3572));
                marker_D9[2].setCaptionText("선화교회");
                marker_D9[3].setPosition(new LatLng(36.3541, 127.3633));
                marker_D9[3].setCaptionText("대전일보 육교 및 버스승강장");
                marker_D9[4].setPosition(new LatLng(36.3531, 127.3685));
                marker_D9[4].setCaptionText("안호영 동물병원");
                marker_D9[5].setPosition(new LatLng(36.3489,127.376));
                marker_D9[5].setCaptionText("큰마을 4거리");
                marker_D9[6].setPosition(new LatLng(36.3407,127.39));
                marker_D9[6].setCaptionText("롯데백화점 앞");
                marker_D9[7].setPosition(new LatLng(36.3368,127.395));
                marker_D9[7].setCaptionText("용문동 선창교회");
                marker_D9[8].setPosition(new LatLng(36.333, 127.412));
                marker_D9[8].setCaptionText("목동 금호한사랑아파트 앞");
                marker_D9[9].setPosition(new LatLng(36.3413, 127.4357));
                marker_D9[9].setCaptionText("성남사거리 현대주유소");
                marker_D9[10].setPosition(new LatLng(36.3486, 127.4357));
                marker_D9[10].setCaptionText("대전복합터미널 한국병원앞");
                marker_D9[11].setPosition(new LatLng(36.3651,127.4403));
                marker_D9[11].setCaptionText("동춘당 앞");
                marker_D9[12].setPosition(new LatLng(36.3676,127.431));
                marker_D9[12].setCaptionText("법동 우체국 맞은편 ");


                for(int j=0; j< marker_D9.length; j++)
                {
                    marker_D9[j].setWidth(80);
                    marker_D9[j].setHeight(110);
                    marker_D9[j].setMap(naverMap);
                }

                cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(36.3271, 127.3376), 11).animate(CameraAnimation.Fly, 1000);;
                naverMap.moveCamera(cameraUpdate);
                break;
            case "하교버스3번" :
                path.setCoords(Arrays.asList(
                        new LatLng(36.3273, 127.3374),
                        new LatLng(36.3274, 127.3382),
                        new LatLng(36.3276, 127.3383),
                        new LatLng(36.3286, 127.3381),
                        new LatLng(36.3291, 127.3381),
                        new LatLng(36.3324, 127.3381),
                        new LatLng(36.3324, 127.3331),
                        new LatLng(36.334, 127.3332),
                        new LatLng(36.336, 127.334),
                        new LatLng(36.3428,127.3396),
                        new LatLng(36.3458, 127.3405),
                        new LatLng(36.349, 127.3405),
                        new LatLng(36.3503, 127.3403),
                        new LatLng(36.3536, 127.3416),
                        new LatLng(36.3556, 127.3422),
                        new LatLng(36.3622, 127.3448),
                        new LatLng(36.3622, 127.3452),
                        new LatLng(36.3607, 127.3497),
                        new LatLng(36.3603, 127.3516),
                        new LatLng(36.3604, 127.3528),
                        new LatLng(36.3606, 127.3539),
                        new LatLng(36.3612, 127.3558),
                        new LatLng(36.362, 127.3576),
                        new LatLng(36.3627, 127.359),
                        new LatLng(36.3634, 127.3599),
                        new LatLng(36.3637,127.3605),
                        new LatLng(36.3646,127.3621),
                        new LatLng(36.3661, 127.3653),
                        new LatLng(36.3674, 127.3674),
                        new LatLng(36.3652, 127.3696),
                        new LatLng(36.3648, 127.3757),
                        new LatLng(36.3647, 127.3767),
                        new LatLng(36.3647, 127.3796),
                        new LatLng(36.3746, 127.3797),
                        new LatLng(36.3743, 127.3824),
                        new LatLng(36.3743, 127.3906),
                        new LatLng(36.3753, 127.3906),
                        new LatLng(36.3757, 127.3904),
                        new LatLng(36.3761, 127.39),
                        new LatLng(36.3766, 127.3898),
                        new LatLng(36.377,127.3899),
                        new LatLng(36.3773, 127.3901),
                        new LatLng(36.3774, 127.3905),
                        new LatLng(36.3775, 127.3906),
                        new LatLng(36.3777, 127.3906),
                        new LatLng(36.3792, 127.3906),
                        new LatLng(36.3791, 127.3921),
                        new LatLng(36.3785, 127.3935),
                        new LatLng(36.378, 127.3944),
                        new LatLng(36.3772, 127.3964),
                        new LatLng(36.3762, 127.3984),
                        new LatLng(36.3757, 127.3983),
                        new LatLng(36.376, 127.3994),
                        new LatLng(36.3762, 127.4016),
                        new LatLng(36.3772, 127.4043),
                        new LatLng(36.378, 127.4053),
                        new LatLng(36.3801, 127.4067),
                        new LatLng(36.3804, 127.4067),
                        new LatLng(36.3852, 127.4052),
                        new LatLng(36.3866, 127.405),
                        new LatLng(36.3997, 127.405),
                        new LatLng(36.3997, 127.3956),
                        new LatLng(36.3999, 127.3939),
                        new LatLng(36.4003, 127.3921),
                        new LatLng(36.4012, 127.3906),
                        new LatLng(36.4022, 127.3894),
                        new LatLng(36.4041, 127.3879),
                        new LatLng(36.4045, 127.3875),
                        new LatLng(36.4091, 127.3809),
                        new LatLng(36.4093, 127.3801),
                        new LatLng(36.4093, 127.3792),
                        new LatLng(36.4077, 127.3758),
                        new LatLng(36.4078, 127.3755),
                        new LatLng(36.408, 127.3753),
                        new LatLng(36.4086, 127.3756),
                        new LatLng(36.4091, 127.3763),
                        new LatLng(36.4094, 127.3771),
                        new LatLng(36.4096, 127.3774),
                        new LatLng(36.411, 127.3776),
                        new LatLng(36.412, 127.3781),
                        new LatLng(36.4133, 127.3783),
                        new LatLng(36.4139, 127.3783),
                        new LatLng(36.4165, 127.3778),
                        new LatLng(36.4172, 127.3778),
                        new LatLng(36.4173, 127.3779),
                        new LatLng(36.4232, 127.3835),
                        new LatLng(36.4318, 127.3877),
                        new LatLng(36.4341, 127.3888),
                        new LatLng(36.4391, 127.3959),
                        new LatLng(36.4403, 127.3985),
                        new LatLng(36.4404, 127.3987),
                        new LatLng(36.4454, 127.4053),
                        new LatLng(36.4479, 127.4096),
                        new LatLng(36.4491, 127.4121),
                        new LatLng(36.4495, 127.4133),
                        new LatLng(36.4497, 127.4154),
                        new LatLng(36.4488, 127.4186),
                        new LatLng(36.4488, 127.4193),
                        new LatLng(36.449, 127.42),
                        new LatLng(36.4495, 127.421),
                        new LatLng(36.4501, 127.4224),
                        new LatLng(36.4502, 127.4233),
                        new LatLng(36.4504, 127.425),
                        new LatLng(36.4511, 127.4288),
                        new LatLng(36.45, 127.429),
                        new LatLng(36.4497, 127.4291)

                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D10[0].setPosition(new LatLng(36.3271, 127.3376));
                marker_D10[0].setCaptionText("신학대학주차장");
                marker_D10[1].setPosition(new LatLng( 36.3532, 127.3415));
                marker_D10[1].setCaptionText("유성온천 7번 출구");
                marker_D10[2].setPosition(new LatLng(36.3614, 127.3446));
                marker_D10[2].setCaptionText("충대 온누리 교회 버스승강장");
                marker_D10[3].setPosition(new LatLng(36.3607, 127.3495));
                marker_D10[3].setCaptionText("궁동 소비자마트 맞은 편");
                marker_D10[4].setPosition(new LatLng(36.3616, 127.3572));
                marker_D10[4].setCaptionText("유성구청");
                marker_D10[5].setPosition(new LatLng(36.3648, 127.3758));
                marker_D10[5].setCaptionText("한아름아파트 102동 앞");
                marker_D10[6].setPosition(new LatLng(36.3789, 127.3906));
                marker_D10[6].setCaptionText("외국인 유학생 기숙사");
                marker_D10[7].setPosition(new LatLng(36.3998, 127.4038));
                marker_D10[7].setCaptionText("전민동 세종아파트");
                marker_D10[8].setPosition(new LatLng(36.421, 127.3814));
                marker_D10[8].setCaptionText("테크노벨리 날망집 버스주차장");
                marker_D10[9].setPosition(new LatLng(36.4317, 127.3877));
                marker_D10[9].setCaptionText("송강체육관 맞은 편");
                marker_D10[10].setPosition(new LatLng(36.4493, 127.413));
                marker_D10[10].setCaptionText("목상동 주민센터");
                marker_D10[11].setPosition(new LatLng(36.4497, 127.429));
                marker_D10[11].setCaptionText("신탄진역");


                for(int j=0; j< marker_D10.length; j++)
                {
                    marker_D10[j].setWidth(80);
                    marker_D10[j].setHeight(110);
                    marker_D10[j].setMap(naverMap);
                }

                cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(36.3271, 127.3376), 11).animate(CameraAnimation.Fly, 1000);;
                naverMap.moveCamera(cameraUpdate);
                break;
            case "하교버스4번" :
                path.setCoords(Arrays.asList(
                        new LatLng(36.5135, 127.2591),
                        new LatLng(36.5121, 127.2595),
                        new LatLng(36.5112, 127.26),
                        new LatLng(36.5098, 127.2609),
                        new LatLng(36.5086, 127.2614),
                        new LatLng(36.5078, 127.2617),
                        new LatLng(36.5068, 127.2618),
                        new LatLng(36.5046, 127.2612),
                        new LatLng(36.4998, 127.2589),
                        new LatLng(36.4974, 127.2574),
                        new LatLng(36.4944, 127.2563),
                        new LatLng(36.4912, 127.2563),
                        new LatLng(36.4892, 127.2567),
                        new LatLng(36.486, 127.2582),
                        new LatLng(36.484, 127.2596),
                        new LatLng(36.4723, 127.268),
                        new LatLng(36.4717, 127.2686),
                        new LatLng(36.4711, 127.2699),
                        new LatLng(36.4692, 127.269),
                        new LatLng(36.4687, 127.2719),
                        new LatLng(36.4692, 127.2747),
                        new LatLng(36.4647, 127.2753),
                        new LatLng(36.4625, 127.2763),
                        new LatLng(36.4568, 127.2821),
                        new LatLng(36.4554, 127.2833),
                        new LatLng(36.4538, 127.2842),
                        new LatLng(36.452, 127.285),
                        new LatLng(36.4502, 127.2855),
                        new LatLng(36.4487, 127.2857),
                        new LatLng(36.4389, 127.2857),
                        new LatLng(36.4372, 127.2855),
                        new LatLng(36.4362, 127.2849),
                        new LatLng(36.4355, 127.2838),
                        new LatLng(36.4355, 127.2859),
                        new LatLng(36.4332, 127.2861),
                        new LatLng(36.4304, 127.287),
                        new LatLng(36.4284, 127.2888),
                        new LatLng(36.4233, 127.2956),
                        new LatLng(36.4221, 127.2972),
                        new LatLng(36.4207, 127.2982),
                        new LatLng(36.4188, 127.2989),
                        new LatLng(36.4151, 127.2996),
                        new LatLng(36.4103, 127.3012),
                        new LatLng(36.406, 127.304),
                        new LatLng(36.3996, 127.3097),
                        new LatLng(36.3959, 127.3117),
                        new LatLng(36.3843, 127.3203),
                        new LatLng(36.3806, 127.3181),
                        new LatLng(36.3797, 127.3179),
                        new LatLng(36.3668, 127.3179),
                        new LatLng(36.3667, 127.3307),
                        new LatLng(36.3643, 127.3378),
                        new LatLng(36.364, 127.3378),
                        new LatLng(36.3625, 127.3369),
                        new LatLng(36.3607, 127.3357),
                        new LatLng(36.3578, 127.3318),
                        new LatLng(36.3542, 127.3303),
                        new LatLng(36.3524, 127.3291),
                        new LatLng(36.3508, 127.335),
                        new LatLng(36.3502, 127.34),
                        new LatLng(36.3486, 127.3402),
                        new LatLng(36.3459, 127.3401),
                        new LatLng(36.343, 127.3394),
                        new LatLng(36.3382, 127.3354),
                        new LatLng(36.3382, 127.3382),
                        new LatLng(36.3292, 127.3382)
                ));

                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D11[0].setPosition(new LatLng(36.3271, 127.3376));
                marker_D11[0].setCaptionText("신학대학주차장");
                marker_D11[1].setPosition(new LatLng( 36.3508, 127.3356));
                marker_D11[1].setCaptionText("유성고등학교  건너");
                marker_D11[2].setPosition(new LatLng(36.3516, 127.3324));
                marker_D11[2].setCaptionText("정림스토어");
                marker_D11[3].setPosition(new LatLng(36.3558,127.3311));
                marker_D11[3].setCaptionText("구암역 건너");
                marker_D11[4].setPosition(new LatLng(36.361, 127.3361));
                marker_D11[4].setCaptionText("유성 새마을금고 건너");
                marker_D11[5].setPosition(new LatLng(36.3744, 127.318));
                marker_D11[5].setCaptionText("노은역 3번 출구");
                marker_D11[6].setPosition(new LatLng(36.3853, 127.3201));
                marker_D11[6].setCaptionText("지족역건너 버스승강장");
                marker_D11[7].setPosition(new LatLng(36.3927, 127.3145));
                marker_D11[7].setCaptionText("반석역5번출구");
                marker_D11[8].setPosition(new LatLng(36.469, 127.2734));
                marker_D11[8].setCaptionText("세종버스터미널");
                marker_D11[9].setPosition(new LatLng(36.4813, 127.262));
                marker_D11[9].setCaptionText("첫마을 2단지 버스승강장");
                marker_D11[10].setPosition(new LatLng(36.4879,127.2574));
                marker_D11[10].setCaptionText("세종 연세에스의원");
                marker_D11[11].setPosition(new LatLng(36.5016, 127.2601));
                marker_D11[11].setCaptionText("세종청사 남측");
                marker_D11[12].setPosition(new LatLng(36.512, 127.26));
                marker_D11[12].setCaptionText("도램마을 지선 버스승강장");

                for(int j=0; j< marker_D11.length; j++)
                {
                    marker_D11[j].setWidth(80);
                    marker_D11[j].setHeight(110);
                    marker_D11[j].setMap(naverMap);
                }

                cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(36.3271, 127.3376), 11).animate(CameraAnimation.Fly, 1000);;
                naverMap.moveCamera(cameraUpdate);
                break;
            case "하교버스5번" :
                path.setCoords(Arrays.asList(
                        new LatLng(36.2788, 127.2377),
                        new LatLng(36.2791, 127.2378),
                        new LatLng(36.2812, 127.2397),
                        new LatLng(36.2814, 127.2399),
                        new LatLng(36.2818, 127.2399),
                        new LatLng(36.2821, 127.2391),
                        new LatLng(36.2824, 127.2393),
                        new LatLng(36.2885, 127.2417),
                        new LatLng(36.288, 127.2436),
                        new LatLng(36.288, 127.2441),
                        new LatLng(36.2885, 127.245),
                        new LatLng(36.2931, 127.2426),
                        new LatLng(36.2936, 127.2428),
                        new LatLng(36.2944, 127.2438),
                        new LatLng(36.295, 127.2449),
                        new LatLng(36.2957, 127.246),
                        new LatLng(36.2967, 127.2462),
                        new LatLng(36.2999, 127.2423),
                        new LatLng(36.2999, 127.2359),
                        new LatLng(36.2988, 127.2359),
                        new LatLng(36.2982, 127.2363),
                        new LatLng(36.2973, 127.2377),
                        new LatLng(36.2946, 127.2413),
                        new LatLng(36.2935, 127.2423),
                        new LatLng(36.2884, 127.2449),
                        new LatLng(36.2845, 127.2474),
                        new LatLng(36.2825, 127.25),
                        new LatLng(36.2819, 127.2506),
                        new LatLng(36.2786, 127.2518),
                        new LatLng(36.2763, 127.2526),
                        new LatLng(36.2757, 127.2533),
                        new LatLng(36.2726, 127.25),
                        new LatLng(36.2721, 127.2508),
                        new LatLng(36.2721, 127.2513),
                        new LatLng(36.2727, 127.2521),
                        new LatLng(36.2726, 127.2533),
                        new LatLng(36.2712, 127.2554),
                        new LatLng(36.2712, 127.2554),
                        new LatLng(36.2705, 127.2586),
                        new LatLng(36.2679, 127.2602),
                        new LatLng(36.2688, 127.2625),
                        new LatLng(36.2687, 127.2633),
                        new LatLng(36.2684, 127.2642),
                        new LatLng(36.2684, 127.2673),
                        new LatLng(36.2683, 127.2685),
                        new LatLng(36.2672, 127.2706),
                        new LatLng(36.265, 127.2737),
                        new LatLng(36.2624, 127.2756),
                        new LatLng(36.2629, 127.2775),
                        new LatLng(36.2635, 127.2786),
                        new LatLng(36.2668, 127.2814),
                        new LatLng(36.2668, 127.283),
                        new LatLng(36.2667, 127.2871),
                        new LatLng(36.2669, 127.2886),
                        new LatLng(36.2677, 127.29),
                        new LatLng(36.2688, 127.2907),
                        new LatLng(36.2722, 127.2911),
                        new LatLng(36.2737, 127.2914),
                        new LatLng(36.2776, 127.2944),
                        new LatLng(36.2787, 127.2959),
                        new LatLng(36.2793, 127.2973),
                        new LatLng(36.2799, 127.3),
                        new LatLng(36.2803, 127.301),
                        new LatLng(36.2812, 127.3026),
                        new LatLng(36.2815, 127.3044),
                        new LatLng(36.2817, 127.3062),
                        new LatLng(36.2834, 127.3096),
                        new LatLng(36.285, 127.3109),
                        new LatLng(36.2856, 127.3118),
                        new LatLng(36.2858, 127.3129),
                        new LatLng(36.2857, 127.3162),
                        new LatLng(36.2859, 127.3172),
                        new LatLng(36.2873, 127.3194),
                        new LatLng(36.2882, 127.3199),
                        new LatLng(36.2892, 127.3201),
                        new LatLng(36.2917, 127.3201),
                        new LatLng(36.2966, 127.322),
                        new LatLng(36.2984, 127.3234),
                        new LatLng(36.2992, 127.3243),
                        new LatLng(36.2997, 127.325),
                        new LatLng(36.3013, 127.3297),
                        new LatLng(36.3015, 127.3306),
                        new LatLng(36.3013, 127.335),
                        new LatLng(36.3014, 127.3365),
                        new LatLng(36.302, 127.3388),
                        new LatLng(36.3031, 127.3443),
                        new LatLng(36.304, 127.3488),
                        new LatLng(36.3052, 127.3534),
                        new LatLng(36.3167, 127.3485),
                        new LatLng(36.3177, 127.3481),
                        new LatLng(36.3323, 127.3483),
                        new LatLng(36.3323, 127.3381),
                        new LatLng(36.3292, 127.3381)

                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_D12[0].setPosition(new LatLng(36.3271, 127.3377));
                marker_D12[0].setCaptionText("신학대학주차장");
                marker_D12[1].setPosition(new LatLng( 36.3208, 127.3479));
                marker_D12[1].setCaptionText("엘드수목토아파트 101동 횡단보도");
                marker_D12[2].setPosition(new LatLng(36.3047, 127.3508));
                marker_D12[2].setCaptionText("가수원 육교");
                marker_D12[3].setPosition(new LatLng(36.302, 127.3376));
                marker_D12[3].setCaptionText("관저동KT");
                marker_D12[4].setPosition(new LatLng(36.2985, 127.3231));
                marker_D12[4].setCaptionText("진잠4거리");
                marker_D12[5].setPosition(new LatLng(36.2673, 127.2705));
                marker_D12[5].setCaptionText("포스코더샾아파트 정문");
                marker_D12[6].setPosition(new LatLng(36.2699, 127.2569));
                marker_D12[6].setCaptionText("금암 주공아파트 건너");
                marker_D12[7].setPosition(new LatLng(36.274, 127.2517));
                marker_D12[7].setCaptionText("계룡시청4거리");
                marker_D12[8].setPosition(new LatLng(36.2975, 127.2453));
                marker_D12[8].setCaptionText("삼위일체 성당건너");
                marker_D12[9].setPosition(new LatLng(36.2999, 127.2414));
                marker_D12[9].setCaptionText("초록델리");
                marker_D12[10].setPosition(new LatLng(36.2884, 127.2424));
                marker_D12[10].setCaptionText("사계절마트 건너");
                marker_D12[11].setPosition(new LatLng(36.2787, 127.2375));
                marker_D12[11].setCaptionText("대동아파트");


                for(int j=0; j< marker_D12.length; j++)
                {
                    marker_D12[j].setWidth(80);
                    marker_D12[j].setHeight(110);
                    marker_D12[j].setMap(naverMap);
                }

            case "테스트1번" :
                path.setCoords(Arrays.asList(
                        // 동신빌라 -> 정림초등학교
                        new LatLng(36.30239051932907, 127.36860207051056), // 동신빌라
                        new LatLng(36.30289610918734, 127.36830105902497),
                        new LatLng(36.302765901450734, 127.36741811036447),
                        new LatLng(36.30253771840269, 127.36615616542801),
                        new LatLng(36.30361099645038, 127.36587170622239), // 정림초등학교

                        // 정림초등학교 -> 정림삼거리
                        new LatLng(36.306260555634935, 127.36510748094567),
                        new LatLng(36.30674789234018, 127.36487871916485) // 정림삼거리

                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_TEST1[0].setPosition(new LatLng(36.30239051932907, 127.36860207051056)); //
                marker_TEST1[0].setCaptionText("동신빌라");
                marker_TEST1[1].setPosition(new LatLng(36.30361099645038, 127.36587170622239)); //
                marker_TEST1[1].setCaptionText("정림초등학교");
                marker_TEST1[2].setPosition(new LatLng(36.30674789234018, 127.36487871916485)); //
                marker_TEST1[2].setCaptionText("정림삼거리");

                for(int j=0; j< 3; j++)
                {
                    marker_TEST1[j].setWidth(80);
                    marker_TEST1[j].setHeight(110);
                    marker_TEST1[j].setMap(naverMap);
                }
                break;

            case "테스트2번" :
                path.setCoords(Arrays.asList(
                        // 목원대학교입구 -> 중앙로터리
                        new LatLng(36.32957020487718, 127.33809106471156), // 목원대학교입구
                        new LatLng(36.32902726668108, 127.33808315036117),
                        new LatLng(36.327375032315885, 127.33837392988141), // 중앙로터리

                        // 중앙로터리 -> 중앙도서관
                        new LatLng(36.32654327847352, 127.33852068288307),
                        new LatLng(36.326087022851056, 127.33813448600094),
                        new LatLng(36.32581688481989, 127.33805814501436),
                        new LatLng(36.32528495100832, 127.3381421575817), // 중앙도서관

                        // 중앙도서관 -> 공과대학
                        new LatLng(36.32237941209854, 127.33866693589721) // 공과대학
                ));
                path.setColor(0xFFA72B43);
                path.setMap(naverMap);

                marker_TEST2[0].setPosition(new LatLng(36.32957020487718, 127.33809106471156));
                marker_TEST2[0].setCaptionText("목원대학교입구");
                marker_TEST2[1].setPosition(new LatLng(36.327375032315885, 127.33837392988141));
                marker_TEST2[1].setCaptionText("중앙로터리");
                marker_TEST2[2].setPosition(new LatLng(36.32528495100832, 127.3381421575817));
                marker_TEST2[2].setCaptionText("중앙도서관");
                marker_TEST2[3].setPosition(new LatLng(36.32237941209854, 127.33866693589721));
                marker_TEST2[3].setCaptionText("공과대학");

                for(int j=0; j< 4; j++)
                {
                    marker_TEST2[j].setWidth(80);
                    marker_TEST2[j].setHeight(110);
                    marker_TEST2[j].setMap(naverMap);
                }
                break;

            default:
                path.setMap(null);
                break;
        }
    }

    // 뒤로가기
    private long tempTime = System.currentTimeMillis();
    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
        if (System.currentTimeMillis() > tempTime + 2000)
        {
            tempTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르시면 창이 닫힙니다", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= tempTime + 2000)
        {
            finish();
        }
    }
}
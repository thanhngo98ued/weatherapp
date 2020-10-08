package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class sevenDayWeather extends AppCompatActivity {
    String bienao  = ""; //dùng để check điều kiện bên intent chuyển qua là chuỗi rỗng hay là 1 string
    ImageView imgComeBack;
    TextView txtNameThanhPho;
    ListView lvView;
    customAdapter adapter;
    ArrayList<weather> weatherArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_day_weather);
        Anhxa();
        Intent intent = getIntent();
        String cityto = intent.getStringExtra("sendto");
//        Log.d("aaa",cityto); // kien tra co ket qua gui qua khong.
         if(cityto.equals("")){
            bienao = "Saigon";
            get7data(bienao);
        }
        else{
            bienao = cityto;
            get7data(bienao);

        }
        imgComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //tro ve man hinh hien tai
            }
        });
    }

    private void get7data(String data){
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=3bad100b63fdf15d6c435e064a7a49ee";
        RequestQueue requestQueue = Volley.newRequestQueue(sevenDayWeather.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObjectCiTy = jsonObject.getJSONObject("city");
                            String nameCiTy = jsonObjectCiTy.getString("name");
                            txtNameThanhPho.setText("City Name : " + nameCiTy);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for(int i = 0; i< jsonArrayList.length();i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");
                                long j = Long.valueOf(ngay);
                                Date date = new Date(j*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd   ");
                                //ép kiểu
                                String dateAo = simpleDateFormat.format(date);

                                JSONObject jsonObject1Main = jsonObjectList.getJSONObject("main");
                                String lonnhat = jsonObject1Main.getString("temp_max");
                                String nhonhat = jsonObject1Main.getString("temp_min");
//
                                Double nbmax = Double.valueOf(lonnhat);
                                String NBMAX = String.valueOf(nbmax.intValue());
                                Double nbmin = Double.valueOf(nhonhat);
                                String NBMin = String.valueOf(nbmin.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status2 = jsonObjectWeather.getString("description");
                                String icon2 = jsonObjectWeather.getString("icon");


                                weatherArrayList.add(new weather(dateAo,status2,icon2,NBMAX,NBMin));
                                Log.d("AAA",ngay);
                            }
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(sevenDayWeather.this, "Loi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(stringRequest);

    } //fuction goi du lieu ten wed ve
    private void Anhxa(){
        imgComeBack = (ImageView) findViewById(R.id.imgback);
        txtNameThanhPho = (TextView) findViewById(R.id.txtthanhpho2);
        lvView = (ListView) findViewById(R.id.lsView);
        weatherArrayList = new ArrayList<weather>();
        adapter = new customAdapter(sevenDayWeather.this, weatherArrayList);
        lvView.setAdapter(adapter);
    }
}
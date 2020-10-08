package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnNextDay;
    TextView nameCity, nameStacte;
    ImageView imgWeather;
    TextView txtNhietDo, txtTrangThai;
    TextView txtSNuoc, txtSMay, txtSGio;
    TextView txtToDay;
    String CITY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        GetCurrentWetherData("Saigon");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String nhapTp = edtSearch.getText().toString();
                    if(nhapTp.equals("")) {
                            CITY = "Saigon";
                            GetCurrentWetherData(CITY);//goi function cho nos chay roi
                    }
                    else {
                        CITY = nhapTp;
                        GetCurrentWetherData(CITY);
                    }


            }
        });
        btnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,sevenDayWeather.class);
                String nhapTp = edtSearch.getText().toString();
                intent.putExtra("sendto",nhapTp);
                startActivity(intent);
            }
        });
    }
    public void GetCurrentWetherData(final String data){
        //data la ten cua cac thanh pho.
        //tao request de get tren wed ve
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //tao 1 string request roi truyen vao 4 tham so,
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=3bad100b63fdf15d6c435e064a7a49ee";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // lấy được json của nó về vì nó chỉ có 1 value
                            String day = jsonObject.getString( "dt");
                            String name = jsonObject.getString("name");
                            nameCity.setText("City : " + name);
                            long i = Long.valueOf(day); //ép kiểu về dạng date
                            Date date = new Date(i*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd hh-mm-ss");
                            //ép kiểu
                            String dataofB = simpleDateFormat.format(date);
                            txtToDay.setText(dataofB);
                            // vì trong mảng  weather có 1 object cho nên khai báo mảng json để lấy các thuộc tính con ra
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather"); // khai báo mảng json
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0); // index bằng 0 vì chỉ có 1 objct
                            String status = jsonObjectWeather.getString("main");
                            String iconImg = jsonObjectWeather.getString("icon");
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/"+iconImg+".png").into(imgWeather);
                            //load hình trên mạng về theo link và cách lấy hình giống cách lấy tên thành phố
                            txtTrangThai.setText("Right Now: "+status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String NHIETDO = String.valueOf(a.intValue());
                            txtNhietDo.setText(NHIETDO + " °C");
                            txtSNuoc.setText(doam+"%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String Speed = jsonObjectWind.getString("speed");
                            Double b = Double.valueOf(Speed);
                            String sucgio = String.valueOf(b.intValue());
                            txtSGio.setText(sucgio + "m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClouds.getString("all");
                            txtSMay.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String quocgia = jsonObjectSys.getString("country");
                            nameStacte.setText("Country: "+ quocgia);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( MainActivity.this, "Khong co thanh pho", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        requestQueue.add(stringRequest);

    }


    private void anhXa(){
            edtSearch    = (EditText) findViewById(R.id.edtSearch);
            btnSearch    = (Button) findViewById(R.id.btnOk);
            btnNextDay   = (Button) findViewById(R.id.btnCacngaytieptheo);
            nameCity     = (TextView) findViewById(R.id.txtThanhpho);
            nameStacte   = (TextView) findViewById(R.id.txtQuocGia);
            imgWeather    = (ImageView) findViewById(R.id.imgIcon);
            txtNhietDo   = (TextView) findViewById(R.id.txtNhietDo);
            txtTrangThai = (TextView) findViewById(R.id.txtTrangThai);
            txtSNuoc     = (TextView) findViewById(R.id.txtDoAm);
            txtSGio      = (TextView) findViewById(R.id.txtGio);
            txtSMay      = (TextView) findViewById(R.id.txtMay);
            txtToDay     = (TextView) findViewById(R.id.txtNgayudate);
    }
}
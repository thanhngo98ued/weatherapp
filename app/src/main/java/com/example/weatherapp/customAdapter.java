package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class customAdapter extends BaseAdapter {
    Context context;
    ArrayList<weather> arrayList;

    public customAdapter(Context context, ArrayList<weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listweather,null);

        TextView day = (TextView) view.findViewById(R.id.txtupdateDay);
        TextView sta = (TextView) view.findViewById(R.id.txtStatus2);
        TextView tempmax = (TextView) view.findViewById(R.id.txttempMax);
        TextView tempmin = (TextView) view.findViewById(R.id.txttempMin);
        ImageView imgStatus = (ImageView) view.findViewById(R.id.Imga2);

        weather weatherobj = arrayList.get(position);

        day.setText(weatherobj.DAY);
        sta.setText(weatherobj.STATUS);
        tempmax.setText(weatherobj.MaxTemp + " °C");
        tempmin.setText(weatherobj.MinTemp + " °C" );

        Picasso.with(context).load("http://openweathermap.org/img/wn/"+weatherobj.Image+".png").into(imgStatus);


        return view;
    }
}

package com.example.weatherapp;

public class weather {
    public String DAY;
    public String STATUS;
    public String Image;
    public String MaxTemp;
    public String MinTemp;

    public weather(String DAY, String STATUS, String image, String maxTemp, String minTemp) {
        this.DAY = DAY;
        this.STATUS = STATUS;
        Image = image;
        MaxTemp = maxTemp;
        MinTemp = minTemp;
    }
}

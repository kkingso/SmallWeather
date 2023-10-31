// IWeatherManager.aidl
package com.kkw.smallweather;

interface IWeatherManager {
    String getNowWeather(in String city);
}
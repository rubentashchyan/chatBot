package org.example;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherService {


    private String apiKey = "45c730c32dc81d80234c28d784559484";
    private String city;

    public WeatherService() {
    }

    public WeatherService(String city) {
         this.city = city;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getApiKey() {
        return apiKey;
    }


    public String buildApiUrl() {


        return "https://api.openweathermap.org/data/2.5/weather?q=" + city
                + "&appid=" + apiKey
                + "&units=metric&lang=ru";

    }


    public String  getWeather() throws IOException {
        URL url = new URL(buildApiUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        if ((line = bufferedReader.readLine()) != null) {
         stringBuilder.append(line);
        }
        String result = stringBuilder.toString();
        bufferedReader.close();

        JSONObject jsonObject = new JSONObject(result);
        String name = (String) jsonObject.get("name");
        JSONObject main=  jsonObject.getJSONObject("main");
        double temp =  main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        JSONArray weather= jsonObject.getJSONArray("weather");
        String weatherInfo = weather.getJSONObject(0).getString("description");
        String reply = name+temp+feelsLike+weatherInfo;
        connection.disconnect();
        return reply;

    }
}

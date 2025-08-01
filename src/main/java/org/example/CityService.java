package org.example;

import java.io.IOException;

public class CityService {
    String cityName;

    public CityService(String cityName) {
        this.cityName = cityName;
    }

    public CityService() {
    }

    public String processCity(String cityName) throws IOException {
        WeatherService weatherService = new WeatherService(cityName);
       String result = weatherService.getWeather();
       return result;
    }
}

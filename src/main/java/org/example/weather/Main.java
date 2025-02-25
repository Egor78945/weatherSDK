package org.example.weather;

import org.example.weather.service.DefaultWeatherService;
import org.example.weather.service.WeatherService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        WeatherService weatherService = new DefaultWeatherService();
        try {
            System.out.println(weatherService.getCityWeather("novocheboksarsk"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
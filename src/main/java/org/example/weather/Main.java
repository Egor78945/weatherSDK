package org.example.weather;

import org.example.weather.configuration.environment.WeatherProperties;
import org.example.weather.configuration.web.WebClientConfiguration;
import org.example.weather.mapper.CityWeatherJsonMapper;
import org.example.weather.mapper.JsonMapper;
import org.example.weather.model.dto.CityWeatherDTO;
import org.example.weather.service.DefaultWeatherService;
import org.example.weather.service.WeatherService;
import org.example.weather.service.cache.CacheService;
import org.example.weather.service.cache.WeatherCacheService;
import org.example.weather.service.web.WeatherClientService;
import org.example.weather.service.web.WebClientService;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        JsonMapper<CityWeatherDTO> cityWeatherDTOJsonMapper = new CityWeatherJsonMapper();
        WeatherProperties weatherProperties = new WeatherProperties();
        WebClientService<CityWeatherDTO> webClientService = new WeatherClientService(new WebClientConfiguration(), cityWeatherDTOJsonMapper);
        CacheService<CityWeatherDTO> cacheService = new WeatherCacheService(new ReentrantLock(), weatherProperties.getCACHE_LIMIT());
        WeatherService<CityWeatherDTO> weatherService = new DefaultWeatherService(cityWeatherDTOJsonMapper, webClientService, weatherProperties, cacheService);
        try {
            System.out.println(weatherService.getCityWeather("novocheboksarsk"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
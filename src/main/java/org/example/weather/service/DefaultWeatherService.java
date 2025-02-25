package org.example.weather.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.example.weather.configuration.environment.WeatherProperties;
import org.example.weather.enumeration.SdkMode;
import org.example.weather.mapper.JsonMapper;
import org.example.weather.model.dto.CacheNode;
import org.example.weather.model.dto.CityWeatherDTO;
import org.example.weather.model.dto.WeatherCacheNode;
import org.example.weather.model.mode.SdkModeExecutor;
import org.example.weather.service.cache.CacheService;
import org.example.weather.service.validator.CityValidator;
import org.example.weather.service.web.WebClientService;

import java.io.IOException;

/**
 * Default realisation of {@link WeatherService}.
 */
public class DefaultWeatherService implements WeatherService<CityWeatherDTO> {
    private final WebClientService<CityWeatherDTO> webClientService;
    private final JsonMapper<CityWeatherDTO> cityWeatherJsonMapper;
    private final WeatherProperties weatherProperties;
    private final CacheService<CityWeatherDTO> cacheService;
    private boolean executorIsEnabled;

    public DefaultWeatherService(JsonMapper<CityWeatherDTO> cityWeatherJsonMapper, WebClientService<CityWeatherDTO> webClientService, WeatherProperties weatherProperties, CacheService<CityWeatherDTO> cacheService) {
        this.cityWeatherJsonMapper = cityWeatherJsonMapper;
        this.webClientService = webClientService;
        this.weatherProperties = weatherProperties;
        this.cacheService = cacheService;
    }

    @Override
    public String getCityWeather(String city) throws IOException {
        if (!executorIsEnabled) {
            startModeExecutor();
            executorIsEnabled = true;
        }
        if (CityValidator.isValidCity(city)) {
            city = city.replace(" ", "%20").toLowerCase();
            CacheNode<CityWeatherDTO> cached = cacheService.get(city);
            if (cached == null || cacheService.isExpired(city, weatherProperties.getEXPIRATION_LIMIT_IN_MINUTES())) {
                CityWeatherDTO cityWeatherDTO = webClientService.get(new HttpGet(String.format("%s?q=%s&appid=%s", weatherProperties.getBASE_URI(), city, weatherProperties.getAPI_KEY())));
                WeatherCacheNode newNode = new WeatherCacheNode(cityWeatherDTO);
                cacheService.put(city, newNode);
                cached = newNode;
            }
            return cityWeatherJsonMapper.mapTo(cached.getPayload());
        }
        throw new RuntimeException("Invalid city format. City length must to be less or equal 50 and it must to contain only letters and spaces.");
    }

    private void startModeExecutor() {
        if (weatherProperties.getSdkMode().equals(SdkMode.POLLING)) {
            new Thread(new SdkModeExecutor.PollingModeExecutor(cacheService, webClientService, weatherProperties)).start();
        }
    }
}

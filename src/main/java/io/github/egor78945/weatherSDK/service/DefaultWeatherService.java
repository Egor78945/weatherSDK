package io.github.egor78945.weatherSDK.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import io.github.egor78945.weatherSDK.configuration.environment.WeatherProperties;
import io.github.egor78945.weatherSDK.enumeration.SdkMode;
import io.github.egor78945.weatherSDK.mapper.JsonMapper;
import io.github.egor78945.weatherSDK.model.dto.CacheNode;
import io.github.egor78945.weatherSDK.model.dto.CityWeatherDTO;
import io.github.egor78945.weatherSDK.model.dto.WeatherCacheNode;
import io.github.egor78945.weatherSDK.model.mode.SdkModeExecutor;
import io.github.egor78945.weatherSDK.service.cache.CacheService;
import io.github.egor78945.weatherSDK.service.validator.CityValidator;
import io.github.egor78945.weatherSDK.service.web.WebClientService;

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

    /**
     * Returns information about weather in city by JSON format.
     * @param city City name.
     * @return JSON representation of weather.
     * @throws IOException Throws an exception, can be after execution HTTP request.
     */
    @Override
    public String getCityWeather(String city) throws IOException {
        if (!executorIsEnabled) {
            startModeExecutor();
            executorIsEnabled = true;
        }
        if (CityValidator.isValidCity(city)) {
            city = city.replace(" ", "%20").toLowerCase();
            CacheNode<CityWeatherDTO> cached = cacheService.get(city);
            if (cached == null || cacheService.isExpired(city, weatherProperties.getCACHE_EXPIRATION_LIMIT())) {
                CityWeatherDTO cityWeatherDTO = webClientService.get(new HttpGet(String.format("%s?q=%s&appid=%s", weatherProperties.getBASE_URI(), city, weatherProperties.getAPI_KEY())));
                WeatherCacheNode newNode = new WeatherCacheNode(cityWeatherDTO);
                cacheService.put(city, newNode);
                cached = newNode;
            }
            return cityWeatherJsonMapper.mapTo(cached.getPayload());
        }
        throw new RuntimeException("Invalid city format. City length must to be less or equal 50 and it must to contain only letters and spaces.");
    }

    /**
     * Starting new thread, which executes business logic of concrete {@link SdkMode}.
     */
    private void startModeExecutor() {
        if (weatherProperties.getSDK_MODE().equals(SdkMode.POLLING)) {
            new Thread(new SdkModeExecutor.PollingModeExecutor(cacheService, webClientService, weatherProperties)).start();
        }
    }
}

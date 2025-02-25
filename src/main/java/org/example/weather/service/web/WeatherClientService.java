package org.example.weather.service.web;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.weather.configuration.web.WebClientConfiguration;
import org.example.weather.mapper.CityWeatherJsonMapper;
import org.example.weather.mapper.JsonMapper;
import org.example.weather.model.dto.CityWeatherDTO;

import java.io.IOException;

/**
 * Realisation of {@link WebClientService} by {@link CityWeatherDTO}.
 */
public class WeatherClientService implements WebClientService<CityWeatherDTO> {
    private final WebClientConfiguration webClientConfiguration;
    private final JsonMapper<CityWeatherDTO> cityWeatherJsonMapper;

    /**
     * Creates default {@link WeatherClientService}
     * @param webClientConfiguration Configuration fo web client.
     * @param cityWeatherJsonMapper Mapper for JSON responses.
     */
    public WeatherClientService(WebClientConfiguration webClientConfiguration, JsonMapper<CityWeatherDTO> cityWeatherJsonMapper) {
        this.webClientConfiguration = webClientConfiguration;
        this.cityWeatherJsonMapper = cityWeatherJsonMapper;
    }

    @Override
    public CityWeatherDTO get(HttpGet httpRequest) throws IOException {
            return webClientConfiguration.getHttpClient().execute(httpRequest, classicHttpResponse -> {
                if(classicHttpResponse.getCode() != 200){
                    throw new HttpException(String.format("%s:%s", classicHttpResponse.getCode(), classicHttpResponse.getReasonPhrase()));
                }
                return cityWeatherJsonMapper.mapTo(EntityUtils.toString(classicHttpResponse.getEntity()));
            });
    }
}

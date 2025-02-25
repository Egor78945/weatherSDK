package org.example.weather.model.dto;

/**
 * Custom realization of {@link CacheNode} for {@link CityWeatherDTO}.
 */
public class WeatherCacheNode extends CacheNode<CityWeatherDTO> {
    public WeatherCacheNode(CityWeatherDTO cityWeatherDTO) {
        super(cityWeatherDTO);
    }
}

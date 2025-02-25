package org.example.weather.service;

import java.io.IOException;

/**
 * The main interface, performing the main business logic.
 */
public interface WeatherService<T> {
    /**
     * Returns information about weather in a city.
     * @param city City name.
     * @return JSON with information about weather in a city.
     * @throws IOException Expecting exceptions with HTTP request.
     */
    String getCityWeather(String city) throws IOException;
}

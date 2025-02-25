package io.github.egor78945.weatherSDK.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.egor78945.weatherSDK.mapper.gson.deserializer.CityWeatherDTODeserializer;
import io.github.egor78945.weatherSDK.model.dto.CityWeatherDTO;

/**
 * Realization of {@link JsonMapper} for {@link CityWeatherDTO}.
 */
public class CityWeatherJsonMapper implements JsonMapper<CityWeatherDTO> {
    private final Gson gson;

    /**
     * Creates {@code WeatherJsonMapper} with {@code Gson} by custom deserializers.
     */
    public CityWeatherJsonMapper() {
        this.gson = new GsonBuilder().
                registerTypeAdapter(CityWeatherDTO.class, new CityWeatherDTODeserializer(new Gson()))
                .create();
    }

    /**
     *
     * @param weatherJson Raw JSON string with weather information from {@code OpenWeatherMapAPI}.
     * @return Custom {@code CityWeatherDTO} with all needed points of weather.
     */
    public CityWeatherDTO mapTo(String weatherJson) {
        return gson.fromJson(weatherJson, CityWeatherDTO.class);
    }

    /**
     * @param cityWeatherDTO Custom {@code CityWeatherDTO} with all needed points of weather.
     * @return JSON string representation of {@code CityWeatherDTO}.
     */
    public String mapTo(CityWeatherDTO cityWeatherDTO) {
        return gson.toJson(cityWeatherDTO);
    }
}

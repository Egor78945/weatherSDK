package org.example.weather.mapper.gson.deserializer;

import com.google.gson.*;
import org.example.weather.model.dto.CityWeatherDTO;

import java.lang.reflect.Type;

/**
 * Custom JSON deserializer for json of weather response.
 */
public class CityWeatherDTODeserializer implements JsonDeserializer<CityWeatherDTO> {
    private final Gson gson;

    public CityWeatherDTODeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public CityWeatherDTO deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return CityWeatherDTO.builder()
                .setWeather(gson.fromJson(jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject(), CityWeatherDTO.Weather.class))
                .setTemperature(gson.fromJson(jsonObject.getAsJsonObject("main"), CityWeatherDTO.Temperature.class))
                .setVisibility(jsonObject.get("visibility").getAsInt())
                .setWind(gson.fromJson(jsonObject.getAsJsonObject("wind"), CityWeatherDTO.Wind.class))
                .setDatetime(jsonObject.get("dt").getAsLong())
                .setSys(gson.fromJson(jsonObject.getAsJsonObject("sys"), CityWeatherDTO.Sys.class))
                .setTimezone(jsonObject.get("timezone").getAsInt())
                .setName(jsonObject.get("name").getAsString())
                .build();
    }
}

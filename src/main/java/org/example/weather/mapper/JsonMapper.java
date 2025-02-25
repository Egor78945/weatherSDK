package org.example.weather.mapper;

/**
 * Mapper for JSON HTTP response.
 */
public interface JsonMapper <T> {
    T mapTo(String json);
    String mapTo(T object);
}

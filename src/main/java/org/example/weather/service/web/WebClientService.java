package org.example.weather.service.web;

import org.apache.hc.client5.http.classic.methods.HttpGet;

import java.io.IOException;

/**
 * Service, sending http requests.
 * @param <T> Type of response.
 */
public interface WebClientService <T> {
    /**
     * Get HTTP request.
     * @param httpRequest request.
     * @return Custom type of HTTP response.
     * @throws IOException Expecting exceptions with HTTP.
     */
    T get(HttpGet httpRequest) throws IOException;
}

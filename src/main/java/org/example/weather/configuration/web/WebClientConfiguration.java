package org.example.weather.configuration.web;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

/**
 * Configuring web client and provides methods to access it.
 */
public class WebClientConfiguration {
    private final CloseableHttpClient httpClient;

    /**
     * Creates {@code WebClientConfiguration} by default.
     */
    public WebClientConfiguration(){
        this.httpClient = HttpClients.createDefault();
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }
}

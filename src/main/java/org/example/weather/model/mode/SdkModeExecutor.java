package org.example.weather.model.mode;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.example.weather.configuration.environment.WeatherProperties;
import org.example.weather.model.dto.CityWeatherDTO;
import org.example.weather.model.dto.WeatherCacheNode;
import org.example.weather.service.cache.CacheService;
import org.example.weather.service.web.WebClientService;

import java.io.IOException;

/**
 * Class, represents executors for {@link org.example.weather.enumeration.SdkMode}.
 */
public class SdkModeExecutor {

    /**
     * Executor for {@code POLLING} {@link org.example.weather.enumeration.SdkMode}.
     */
    public static class PollingModeExecutor implements Runnable {
        private final CacheService<CityWeatherDTO> cacheService;
        private final WebClientService<CityWeatherDTO> webClientService;
        private final WeatherProperties weatherProperties;

        public PollingModeExecutor(CacheService<CityWeatherDTO> cacheService, WebClientService<CityWeatherDTO> webClientService, WeatherProperties weatherProperties) {
            this.cacheService = cacheService;
            this.webClientService = webClientService;
            this.weatherProperties = weatherProperties;
        }

        /**
         * Regularly updates cached data.
         */
        @Override
        public void run() {
            while (true) {
                for (String k : cacheService.getCacheMap().keySet()) {
                    if (cacheService.isExpired(k, weatherProperties.getEXPIRATION_LIMIT_IN_MINUTES())) {
                        System.out.println("expired, change.");
                        try {
                            CityWeatherDTO t = webClientService.get(new HttpGet(String.format("%s?q=%s&appid=%s", weatherProperties.getBASE_URI(), k, weatherProperties.getAPI_KEY())));
                            cacheService.put(k, new WeatherCacheNode(t));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                try {
                    Thread.sleep((long) weatherProperties.getCACHE_UPDATE_IN_MINUTES() * 60 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

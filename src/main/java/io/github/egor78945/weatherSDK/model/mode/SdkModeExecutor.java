package io.github.egor78945.weatherSDK.model.mode;

import io.github.egor78945.weatherSDK.enumeration.SdkMode;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import io.github.egor78945.weatherSDK.configuration.environment.WeatherProperties;
import io.github.egor78945.weatherSDK.model.dto.CityWeatherDTO;
import io.github.egor78945.weatherSDK.model.dto.WeatherCacheNode;
import io.github.egor78945.weatherSDK.service.cache.CacheService;
import io.github.egor78945.weatherSDK.service.web.WebClientService;

import java.io.IOException;

/**
 * Class, represents executors for {@link SdkMode}.
 */
public class SdkModeExecutor {

    /**
     * Executor for {@code POLLING} {@link SdkMode}.
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
                    if (cacheService.isExpired(k, weatherProperties.getCACHE_EXPIRATION_LIMIT())) {
                        try {
                            CityWeatherDTO t = webClientService.get(new HttpGet(String.format("%s?q=%s&appid=%s", weatherProperties.getBASE_URI(), k, weatherProperties.getAPI_KEY())));
                            cacheService.put(k, new WeatherCacheNode(t));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                try {
                    Thread.sleep((long) weatherProperties.getCACHE_UPDATE_TIME() * 60 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

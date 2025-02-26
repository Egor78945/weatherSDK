package io.github.egor78945.weatherSDK.configuration.environment;

import io.github.egor78945.weatherSDK.enumeration.SdkMode;
import io.github.egor78945.weatherSDK.service.validator.IntegerValidator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 Provides properties from {@code application.properties} by the path {@code src/main/resources/application.properties}
 */
public class WeatherProperties {
    private final Properties PROPERTIES;
    /**
     * Uri to {@code OpenWeatherMapAPI}.
     */
    private final String BASE_URI;
    /**
     * Current mode of SDK work.
     */
    private final SdkMode SDK_MODE;
    /**
     * Secret key to {@code OpenWeatherMapAPI}.
     */
    private final String API_KEY;
    /**
     * Time after which cache will be considered expired.
     */
    private final int CACHE_EXPIRATION_LIMIT;
    /**
     * Time after which cache will be updating.
     */
    private final int CACHE_UPDATE_TIME;
    /**
     * Maximum count of caching objects.
     */
    private final int CACHE_LIMIT;

    public WeatherProperties() {
        this.BASE_URI = "http://api.openweathermap.org/data/2.5/weather";
        this.PROPERTIES = new Properties();
        loadProperties();
        this.SDK_MODE = loadSdkMode();
        this.API_KEY = loadAPI_KEY();
        this.CACHE_EXPIRATION_LIMIT = loadEXPIRATION_LIMIT_IN_MINUTES();
        this.CACHE_UPDATE_TIME = loadCACHE_UPDATE_IN_MINUTES();
        this.CACHE_LIMIT = loadCACHE_LIMIT();
    }

    /**
     Downloads all existing properties from {@code application.properties}
     */
    private void loadProperties() {
        InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
        if (input == null) {
            throw new RuntimeException("Properties file not found.");
        }
        try {
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     @return Loaded SDK work mode from {@code application.properties} and throws an exception if mode is unknown.
     */
    public SdkMode loadSdkMode() {
        return SdkMode.valueOf(PROPERTIES.getProperty("weather.mode", SdkMode.ON_DEMAND.name()).toUpperCase());
    }

    /**
     @return Loaded secret key for access to {@code OpenWeatherMapAPI} and throws and exception if key is in invalid format or is not exists.
     */
    public String loadAPI_KEY() {
        String secret = PROPERTIES.getProperty("weather.secret");
        if(secret == null || secret.isEmpty() || secret.isBlank()){
            throw new RuntimeException("Secret key is not specified.");
        }
        return secret;
    }

    /**
     * @return Loaded time in minutes, after which cached data is considered obsolete.
     */
    public int loadEXPIRATION_LIMIT_IN_MINUTES(){
        int value = Integer.parseInt(PROPERTIES.getProperty("weather.cache.expiration", "10"));
        if(!IntegerValidator.isBetween(value, 1, 1440)){
            throw new IllegalArgumentException("weather.cache.expiration must be more 0 and less 1441.");
        }
        return value;
    }

    /**
     * @return Loaded time, after which cached data must be updated.
     */
    public int loadCACHE_UPDATE_IN_MINUTES(){
        int value = Integer.parseInt(PROPERTIES.getProperty("weather.cache.update", "3"));
        if(!IntegerValidator.isBetween(value, 1, 1440)){
            throw new IllegalArgumentException("weather.cache.expiration must be more 0 and less 1441.");
        }
        return value;
    }

    /**
     * @return Loaded maximum count of caching data.
     */
    public int loadCACHE_LIMIT(){
        int value = Integer.parseInt(PROPERTIES.getProperty("weather.cache.limit", "10"));
        if(!IntegerValidator.isBetween(value, 0, 100)){
            throw new IllegalArgumentException("weather.cache.limit must be more or equal 0 and less 101.");
        }
        return value;
    }

    /**
     * @return Uri to {@code OpenWeatherMapAPI}.
     */
    public String getBASE_URI() {
        return BASE_URI;
    }

    /**
     * @return Current SDK mode.
     */
    public SdkMode getSDK_MODE() {
        return SDK_MODE;
    }

    /**
     * @return Secret {@code OpenWeatherMapAPI} key.
     */
    public String getAPI_KEY() {
        return API_KEY;
    }

    /**
     * @return Time after which cache will be considered expired.
     */
    public int getCACHE_EXPIRATION_LIMIT() {
        return CACHE_EXPIRATION_LIMIT;
    }

    /**
     * @return Time after which cache will be updating.
     */
    public int getCACHE_UPDATE_TIME() {
        return CACHE_UPDATE_TIME;
    }

    /**
     * @return Maxim count of caching objects.
     */
    public int getCACHE_LIMIT() {
        return CACHE_LIMIT;
    }
}

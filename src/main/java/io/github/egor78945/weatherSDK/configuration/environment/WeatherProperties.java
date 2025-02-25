package io.github.egor78945.weatherSDK.configuration.environment;

import io.github.egor78945.weatherSDK.enumeration.SdkMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 Provides properties from {@code application.properties} by the path {@code src/main/resources/application.properties}
 */
public class WeatherProperties {
    private final Properties PROPERTIES;
    private final String BASE_URI;

    public WeatherProperties() {
        this.BASE_URI = "http://api.openweathermap.org/data/2.5/weather";
        this.PROPERTIES = new Properties();
        loadProperties();
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
     @return Selected SDK work mode from {@code application.properties} and throws an exception if mode is unknown.
     */
    public SdkMode getSdkMode() {
        return SdkMode.valueOf(PROPERTIES.getProperty("weather.mode", SdkMode.ON_DEMAND.name()).toUpperCase());
    }

    /**
     @return Secret key for access to {@code OpenWeatherMapAPI} and throws and exception if key is in invalid format or is not exists.
     */
    public String getAPI_KEY() {
        String secret = PROPERTIES.getProperty("weather.secret");
        if(secret == null || secret.isEmpty() || secret.isBlank()){
            throw new RuntimeException("Secret key is not specified.");
        }
        return secret;
    }

    /**
     * @return A time in minutes, after which cached data is considered obsolete.
     */
    public int getEXPIRATION_LIMIT_IN_MINUTES(){
        return Integer.parseInt(PROPERTIES.getProperty("weather.cache.expiration", "10"));
    }

    /**
     * @return A time, after which cached data must be updated.
     */
    public int getCACHE_UPDATE_IN_MINUTES(){
        return Integer.parseInt(PROPERTIES.getProperty("weather.cache.update", "3"));
    }

    /**
     * @return Maximum count of caching data.
     */
    public int getCACHE_LIMIT(){
        return Integer.parseInt(PROPERTIES.getProperty("weather.cache.limit", "10"));
    }

    /**
     * @return Uri to {@code OpenWeatherMapAPI}
     */
    public String getBASE_URI() {
        return BASE_URI;
    }
}

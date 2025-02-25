package io.github.egor78945.weatherSDK.enumeration;

/**
 * Represents SDK work mode types.
 */
public enum SdkMode {
    /**
     * Cached weather data updates by user request.
     */
    ON_DEMAND,
    /**
     * Cached weather data updates automatically by individual thread.
     */
    POLLING
}

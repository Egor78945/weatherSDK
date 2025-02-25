package org.example.weather.service.cache;

import org.example.weather.model.dto.CacheNode;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Service, provides manipulation with cashing data.
 * @param <T> Type of cashing data.
 */
public abstract class CacheService<T> {
    protected final Map<String, CacheNode<T>> cacheMap = new HashMap<>();

    public CacheNode<T> get(String key) {
        return cacheMap.get(key);
    }

    public void put(String key, CacheNode<T> value) {
        cacheMap.put(key, value);
    }

    public void remove(String key) {
        cacheMap.remove(key);
    }

    /**
     * Checks if storage time of cached data has expired.
     * @param key Key to cashed data.
     * @param expirationLimitInMinutes The time, after which cashed data is expired.
     * @return Expired or not.
     */
    public boolean isExpired(String key, int expirationLimitInMinutes) {
        return Duration.between(cacheMap.get(key).getLocalDateTime(), LocalDateTime.now()).toMinutes() >= expirationLimitInMinutes;
    }

    public Map<String, CacheNode<T>> getCacheMap() {
        return cacheMap;
    }
}

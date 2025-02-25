package org.example.weather.service.cache;

import org.example.weather.model.dto.CacheNode;
import org.example.weather.model.dto.CityWeatherDTO;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/**
 * Realization of {@link CacheService} for {@link CityWeatherDTO}.
 */
public class WeatherCacheService extends CacheService<CityWeatherDTO> {
    private final Lock lock;
    /**
     * Current count of cashing objects.
     */
    private volatile AtomicInteger cacheCount;
    /**
     * Maximum count of cashing objects.
     */
    private final int cacheLimit;

    public WeatherCacheService(Lock lock, int cacheLimit) {
        this.lock = lock;
        this.cacheCount = new AtomicInteger(0);
        this.cacheLimit = cacheLimit;
    }

    @Override
    public void put(String key, CacheNode<CityWeatherDTO> value) {
        lock.lock();
        try {
            if (cacheCount.get() < cacheLimit) {
                cacheMap.put(key, value);
                cacheCount.incrementAndGet();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public CacheNode<CityWeatherDTO> get(String key) {
        lock.lock();
        try {
            return cacheMap.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void remove(String key) {
        lock.lock();
        try {
            cacheMap.remove(key);
            cacheCount.decrementAndGet();
        } finally {
            lock.unlock();
        }
    }
}

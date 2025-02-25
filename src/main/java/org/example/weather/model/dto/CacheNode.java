package org.example.weather.model.dto;

import java.time.LocalDateTime;

/**
 * Class for structure cache information.
 * @param <T> A class type for {{@link #payload}}.
 */
public abstract class CacheNode <T> {
    /**
     * Time of creating {@link CacheNode}.
     */
    protected final LocalDateTime localDateTime;
    /**
     * Caching object.
     */
    protected final T payload;

    /**
     * Creating default {@link CacheNode}.
     * @param payload Cashing object.
     */
    public CacheNode(T payload) {
        this.localDateTime = LocalDateTime.now();
        this.payload = payload;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public T getPayload() {
        return payload;
    }
}

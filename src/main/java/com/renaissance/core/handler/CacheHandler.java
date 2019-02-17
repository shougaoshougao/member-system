package com.renaissance.core.handler;

import org.ehcache.Cache;
import org.ehcache.CacheManager;

/**
 * @author Wilson
 */
public class CacheHandler {

    private CacheManager cacheManager;

    public CacheHandler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void writeCache(String cacheName, String key, Object value) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName, String.class, Object.class);
        cache.put(key, value);
    }

    public <T> T readCache(String cacheName, String key, Class<T> tClass) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName, String.class, Object.class);
        return (T)cache.get(key);
    }

    public boolean exist(String cacheName, String key) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName, String.class, Object.class);
        return cache.containsKey(key);
    }

}

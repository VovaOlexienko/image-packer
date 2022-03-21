package com.vova.imagepacker.cache;

import com.vova.imagepacker.domain.Image;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Cached {

    @Caching(
            cacheable = @Cacheable(cacheNames = "images", key = "#id", condition = "#images == null"),
            put = @CachePut(cacheNames = "images", key = "#id", condition = "#images != null")
    )
    public List<Image> images(Long id, List<Image> images) {
        return images;
    }

    @CacheEvict(value = "images", key = "#id")
    public void evictImagesCache(Long id) {
        // cache evict
    }
}

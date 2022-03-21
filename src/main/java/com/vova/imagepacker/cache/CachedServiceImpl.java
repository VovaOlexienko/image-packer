package com.vova.imagepacker.cache;

import com.vova.imagepacker.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CachedServiceImpl implements CachedService {

    private final Cached cached;

    @Override
    public List<Image> getCachedImages(Long id) {
        return cached.images(id, null);
    }

    @Override
    public void updateCachedImages(Long id, Consumer<List<Image>> consumer) {
        List<Image> oldCachedImages = cached.images(id, null);
        List<Image> newCachedImages = Objects.nonNull(oldCachedImages) ? oldCachedImages : new ArrayList<>();
        consumer.accept(newCachedImages);
        cached.images(id, newCachedImages);
    }

    @Override
    public void clearCachedImages(Long id) {
        cached.evictImagesCache(id);
    }
}

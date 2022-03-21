package com.vova.imagepacker.cache;

import com.vova.imagepacker.domain.Image;

import java.util.List;
import java.util.function.Consumer;

public interface CachedService {

    List<Image> getCachedImages(Long id);

    void updateCachedImages(Long id, Consumer<List<Image>> consumer);

    void clearCachedImages(Long id);
}

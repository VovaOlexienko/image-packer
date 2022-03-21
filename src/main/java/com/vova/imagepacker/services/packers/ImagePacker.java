package com.vova.imagepacker.services.packers;

import com.vova.imagepacker.domain.Image;

import java.io.File;
import java.util.List;

@FunctionalInterface
public interface ImagePacker {
    File packImages(String filename, List<Image> images);
}

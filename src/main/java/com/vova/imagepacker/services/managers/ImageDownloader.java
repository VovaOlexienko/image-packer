package com.vova.imagepacker.services.managers;

@FunctionalInterface
public interface ImageDownloader {
    byte[] downloadImage(String imagePath);
}

package com.vova.imagepacker;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@EnableCaching
@SpringBootApplication
@SuppressWarnings({"rawtypes", "unchecked"})
public class ImagePackerApplication {

    @Bean
    public TelegramBot imagePackerTelegramBot(@Value("${telegram.bot.token}") String token) {
        return new TelegramBot(token);
    }

   @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(50);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(ImagePackerApplication.class, args);
    }
}
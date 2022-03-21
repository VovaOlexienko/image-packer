package com.vova.imagepacker.services.managers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class TelegramImageDownloader implements ImageDownloader {

    private static final String DOWNLOAD_LINK = "https://api.telegram.org/file/bot%s/%s";

    private final TelegramBot telegramBot;

    @Value("${telegram.bot.token}")
    private String token;

    @SneakyThrows
    @Override
    public byte[] downloadImage(String imagePath) {
        GetFileResponse getFileResponse = telegramBot.execute(new GetFile(imagePath));
        String path = String.format(DOWNLOAD_LINK, token, getFileResponse.file().filePath());
        try (InputStream inputStream = new URL(path).openStream()) {
            return IOUtils.toByteArray(inputStream);
        }
    }
}

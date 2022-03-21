package com.vova.imagepacker.controllers;

import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.vova.imagepacker.cache.CachedService;
import com.vova.imagepacker.domain.Image;
import com.vova.imagepacker.domain.requests.AddImageRequest;
import com.vova.imagepacker.domain.requests.CreateDocumentRequest;
import com.vova.imagepacker.services.managers.ImageDownloader;
import com.vova.imagepacker.services.packers.ImagePacker;
import com.vova.imagepacker.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@BotController
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class ImagePackerController implements TelegramMvcController {

    private final CachedService cachedService;
    private final ImageDownloader imageDownloader;
    private final ImagePacker imagePackager;
    private final Validator<AddImageRequest> addImageRequestValidator;
    private final Validator<CreateDocumentRequest> createDocumentRequestValidator;

    @MessageRequest(value = "/start")
    public String start() {
        return "Hello, send me photo to start!";
    }

    @MessageRequest(value = "**")
    public BaseRequest addImageInCache(Chat chat, Update update) {
        return addImageRequestValidator.validate(new AddImageRequest(chat, update)).orElseGet(() -> {
            PhotoSize photoSize = update.message().photo()[2];
            byte[] bytes = imageDownloader.downloadImage(photoSize.fileId());
            cachedService.updateCachedImages(chat.id(), images -> images.add(new Image(bytes, photoSize.height(), photoSize.width())));
            return new SendMessage(chat.id(), "Photo is received!");
        });
    }

    @MessageRequest(value = "/getFile **")
    public BaseRequest getFile(Chat chat, String message) {
        String filename = message.split(" ", 2)[1];
        return createDocumentRequestValidator.validate(new CreateDocumentRequest(chat, filename)).orElseGet(() -> {
            List<Image> images = cachedService.getCachedImages(chat.id());
            cachedService.clearCachedImages(chat.id());
            return new SendDocument(chat.id(), imagePackager.packImages(filename, images));
        });
    }

    @Value("${telegram.bot.token}")
    private String token;

    @Override
    public String getToken() {
        return token;
    }
}

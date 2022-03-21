package com.vova.imagepacker.validators;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.vova.imagepacker.cache.CachedService;
import com.vova.imagepacker.domain.requests.AddImageRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class AddImageRequestValidator implements Validator<AddImageRequest> {

    private final CachedService cachedService;

    @Override
    public Optional<BaseRequest> validate(AddImageRequest request) {
        if (Objects.isNull(request.getUpdate().message()) || Objects.isNull(request.getUpdate().message().photo())) {
            return Optional.of(new SendMessage(request.getChat().id(), ""));
        }
        if (request.getUpdate().message().photo().length < 3) {
            return Optional.of(new SendMessage(request.getChat().id(), "Telegram error"));
        }
        if (CollectionUtils.size(cachedService.getCachedImages(request.getChat().id())) > 9) {
            return Optional.of(new SendMessage(request.getChat().id(), "You can't send more than 10 images"));
        }
        return Optional.empty();
    }
}

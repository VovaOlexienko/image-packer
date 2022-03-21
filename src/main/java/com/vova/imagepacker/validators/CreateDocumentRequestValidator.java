package com.vova.imagepacker.validators;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.vova.imagepacker.cache.CachedService;
import com.vova.imagepacker.domain.requests.CreateDocumentRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class CreateDocumentRequestValidator implements Validator<CreateDocumentRequest> {

    private final CachedService cachedService;

    @Override
    public Optional<BaseRequest> validate(CreateDocumentRequest request) {
        if (CollectionUtils.isEmpty(cachedService.getCachedImages(request.getChat().id()))) {
            return Optional.of(new SendMessage(request.getChat().id(), "Send images before creating document"));
        }
        if (StringUtils.isBlank(request.getFilename())) {
            return Optional.of(new SendMessage(request.getChat().id(), "Send filename to create a document"));
        }
        if (!request.getFilename().matches("^[A-Za-z0-9_.]+$")) {
            return Optional.of(new SendMessage(request.getChat().id(), "Filename has invalid format"));
        }
        return Optional.empty();
    }
}

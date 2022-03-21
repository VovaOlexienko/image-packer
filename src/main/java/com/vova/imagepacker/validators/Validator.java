package com.vova.imagepacker.validators;

import com.pengrad.telegrambot.request.BaseRequest;

import java.util.Optional;

@FunctionalInterface
@SuppressWarnings("rawtypes")
public interface Validator<T> {
    Optional<BaseRequest> validate(T obj);
}

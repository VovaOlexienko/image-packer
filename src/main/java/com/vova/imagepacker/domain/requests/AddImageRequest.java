package com.vova.imagepacker.domain.requests;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddImageRequest extends ChatRequest {

    private Update update;

    public AddImageRequest(Chat chat, Update update) {
        super(chat);
        this.update = update;
    }
}

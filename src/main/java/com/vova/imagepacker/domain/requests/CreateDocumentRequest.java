package com.vova.imagepacker.domain.requests;

import com.pengrad.telegrambot.model.Chat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDocumentRequest extends ChatRequest {

    private String filename;

    public CreateDocumentRequest(Chat chat, String filename) {
        super(chat);
        this.filename = filename;
    }
}

package com.vova.imagepacker.domain.requests;

import com.pengrad.telegrambot.model.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatRequest {
    private Chat chat;
}

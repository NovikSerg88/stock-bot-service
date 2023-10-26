package ru.novik.telegrambotservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@RequiredArgsConstructor
public class StockBotFacade {

    private final ResponseHandler responseHandler;

    public SendMessage handleUpdate(Update update) {
        Long chatId;
        SendMessage response = new SendMessage();

        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            response = responseHandler.callBackResponse(data, chatId);
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            if (text.startsWith("/")) {
                response = responseHandler.commandResponse(chatId, update);
            } else {
                response = responseHandler.messageResponse(chatId, update);
            }
        }
        return response;
    }
}

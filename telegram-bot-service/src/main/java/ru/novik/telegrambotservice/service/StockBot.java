package ru.novik.telegrambotservice.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
public class StockBot extends TelegramLongPollingBot {

    private final StockBotFacade stockBotFacade;

    @Value("${bot.name}")
    private String botName;

    public StockBot(@Value("${bot.token}") String botToken,
                    StockBotFacade stockBotFacade) {
        super(botToken);
        this.stockBotFacade = stockBotFacade;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        SendMessage message = stockBotFacade.handleUpdate(update);
        execute(message);
    }
}


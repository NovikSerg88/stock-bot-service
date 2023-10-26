package ru.novik.telegrambotservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novik.telegrambotservice.constants.BotState;
import ru.novik.telegrambotservice.exception.ChatNotFoundException;
import ru.novik.telegrambotservice.model.Chat;
import ru.novik.telegrambotservice.repository.ChatRepository;


@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public void setBotState(Long chatId, BotState botState) {
        Chat chat = chatRepository.findByChatId(chatId)
                .orElse(new Chat());
        chat.setChatId(chatId);
        chat.setState(botState);
        chatRepository.save(chat);
    }

    public BotState getBotState(Long chatId) {
        return chatRepository.findByChatId(chatId)
                .map(Chat::getState)
                .orElseThrow(() -> new ChatNotFoundException(String.format("Chat with id = %s not found", chatId)));
    }
}

package ru.novik.telegrambotservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novik.telegrambotservice.model.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatId(Long chatId);
}

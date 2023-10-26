package ru.novik.telegrambotservice.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static ru.novik.telegrambotservice.constants.KeyboardCommands.STOCK_LIST;

@Component
public class KeyboardFactory {

    public ReplyKeyboardMarkup startKeyboard() {
        KeyboardRow row = new KeyboardRow();
        row.add(STOCK_LIST);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(List.of(row));
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup pageStocks() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton backwardButton = new InlineKeyboardButton();
        backwardButton.setText("<< Назад");
        backwardButton.setCallbackData("BACK");
        row.add(backwardButton);

        InlineKeyboardButton forwardButton = new InlineKeyboardButton();
        InlineKeyboardButton returnButton = new InlineKeyboardButton();
        returnButton.setText("В начало");
        returnButton.setCallbackData("RETURN");
        row.add(returnButton);

        forwardButton.setText("Вперед >>");
        forwardButton.setCallbackData("FORWARD");
        row.add(forwardButton);

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public InlineKeyboardMarkup fullReport(String data) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton fullReportButton = new InlineKeyboardButton();
        fullReportButton.setText("Полный отчет");
        fullReportButton.setCallbackData(data);
        row.add(fullReportButton);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}

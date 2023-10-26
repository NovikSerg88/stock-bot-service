package ru.novik.telegrambotservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.novik.stockservice.dto.StockDto;
import ru.novik.stockservice.dto.StockShortDto;
import ru.novik.telegrambotservice.constants.BotState;
import ru.novik.telegrambotservice.keyboard.KeyboardFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.novik.telegrambotservice.constants.KeyboardCommands.BACK;
import static ru.novik.telegrambotservice.constants.KeyboardCommands.FORWARD;
import static ru.novik.telegrambotservice.constants.KeyboardCommands.RETURN;
import static ru.novik.telegrambotservice.constants.KeyboardCommands.STOCK_LIST;
import static ru.novik.telegrambotservice.constants.MenuCommands.INFO;
import static ru.novik.telegrambotservice.constants.MenuCommands.START;
import static ru.novik.telegrambotservice.constants.MenuCommands.STOCKS;
import static ru.novik.telegrambotservice.constants.MessageConstants.INFO_TEXT;
import static ru.novik.telegrambotservice.constants.MessageConstants.START_TEXT;


@RequiredArgsConstructor
@Component
public class ResponseHandler {

    private final StockService stockService;
    private final KeyboardFactory keyboardFactory;
    private final ChatService chatService;

    private int pageSize = 25;
    private int currentPage = 0;


    public SendMessage messageResponse(long chatId, Update update) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setReplyMarkup(keyboardFactory.startKeyboard());
        String message = update.getMessage().getText();
        if (message.equals(STOCK_LIST)) {
            chatService.setBotState(chatId, BotState.DEFAULT);
            return callBackResponse(message, chatId);
        } else {
            StockShortDto stockShortDto = stockService.getStockShortByTicker(message);
            response.setText(stockShortDto.toString());
            response.setReplyMarkup(keyboardFactory.fullReport(message));
            chatService.setBotState(chatId, BotState.FULL_REPORT);
            return response;
        }
    }


    public SendMessage callBackResponse(String data, long chatId) {
        switch (data) {
            case BACK:
                currentPage--;
                break;
            case FORWARD:
                currentPage++;
                break;
            case RETURN:
                currentPage = 0;
                break;
        }
        if (BotState.FULL_REPORT.equals(chatService.getBotState(chatId))) {
            SendMessage response = new SendMessage();
            StockDto stockDto = stockService.getStockByTicker(data);
            response.setText(stockDto.toString());
            response.setChatId(chatId);
            return response;
        } else {
            return sendStocksPage(currentPage, chatId);
        }
    }

    public SendMessage sendStocksPage(int page, long chatId) {
        Map<String, String> allStocks = stockService.getAllStocksNames();
        int fromIndex = page * pageSize;
        int toIndex = Math.min((page + 1) * pageSize, allStocks.size());

        List<Map.Entry<String, String>> stocksPage = new ArrayList<>(allStocks.entrySet())
                .subList(fromIndex, toIndex);

        String namesWithSlash = stocksPage.stream()
                .map(entry -> "/" + entry.getKey() + "  (" + entry.getValue() + ")")
                .collect(Collectors.joining("\n"));

        /* Создание сообщения с текстом страницы списка акций */
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(String.join("\n", namesWithSlash));

        /* Создание клавиатуры с кнопками "назад" и "вперед" */
        InlineKeyboardMarkup keyboardMarkup = keyboardFactory.pageStocks();
        response.setReplyMarkup(keyboardMarkup);

        return response;
    }

    public SendMessage commandResponse(long chatId, Update update) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setReplyMarkup(keyboardFactory.startKeyboard());
        String command = update.getMessage().getText();
        chatService.setBotState(chatId, BotState.DEFAULT);
        switch (command) {
            case START:
                String userName = update.getMessage().getChat().getFirstName();
                String greeting = String.format(START_TEXT, userName);
                response.setText(greeting);
                break;
            case INFO:
                response.setText(INFO_TEXT);
                break;
            case STOCKS:
                response = callBackResponse(command, chatId);
                break;
            default:
                StockShortDto stockShortDto = stockService.getStockShortByTicker(command);
                response.setText(stockShortDto.toString());
                response.setReplyMarkup(keyboardFactory.fullReport(command));
                chatService.setBotState(chatId, BotState.FULL_REPORT);
                break;
        }
        return response;
    }
}

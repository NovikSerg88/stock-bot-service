package ru.novik.stockservice.service;

import ru.novik.stockservice.dto.StockDto;
import ru.novik.stockservice.dto.StockShortDto;

import java.util.Map;

public interface StockService {

    StockShortDto getShortStockByTicker(String ticker);

    StockDto getStockByTicker(String ticker);

    Map<String, String> getAllStocksName();
}

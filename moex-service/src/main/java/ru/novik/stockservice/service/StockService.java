package ru.novik.stockservice.service;

import ru.novik.stockservice.dto.SecurityDto;
import ru.novik.stockservice.dto.StockDto;
import ru.novik.stockservice.dto.StockShortDto;

import java.util.List;

public interface StockService {

    StockShortDto getShortStockByTicker(String ticker);

    StockDto getStockByTicker(String ticker);

    List<SecurityDto> getAllStocksName();
}

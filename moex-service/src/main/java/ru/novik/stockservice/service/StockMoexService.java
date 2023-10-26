package ru.novik.stockservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.novik.stockservice.client.MoexClient;
import ru.novik.stockservice.dto.StockDto;
import ru.novik.stockservice.mapper.StockMapper;
import ru.novik.stockservice.model.Stock;
import ru.novik.stockservice.parser.Parser;
import ru.novik.stockservice.dto.StockShortDto;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StockMoexService implements StockService {

    private final MoexClient client;
    private final Parser parser;
    private final StockMapper mapper;

    @Override
    public StockShortDto getShortStockByTicker(String ticker) {
        String stockAsString = client.getStockFromMoex(ticker);
        Stock stock = parser.parse(stockAsString);
        return mapper.mapToShortDto(stock);
    }

    @Override
    public StockDto getStockByTicker(String ticker) {
        String stockAsString = client.getStockFromMoex(ticker);
        Stock stock = parser.parse(stockAsString);
        return mapper.mapToDto(stock);
    }

    @Override
    public Map<String, String> getAllStocksName() {
        String allStocksAsString = client.getAllStocksName();
        return parser.stocksNamesParse(allStocksAsString);
    }
}

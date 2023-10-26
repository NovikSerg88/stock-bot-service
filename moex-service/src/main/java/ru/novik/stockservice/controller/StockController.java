package ru.novik.stockservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.novik.stockservice.service.StockService;
import ru.novik.stockservice.dto.StockDto;
import ru.novik.stockservice.dto.StockShortDto;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StockController {
    private final StockService stockService;

    @GetMapping("/stocks/short/{ticker}")
    public StockShortDto getShortStockByTicker(@PathVariable String ticker) {
        log.info("Receiving GET request to find stock by ticker = {}", ticker);
        return stockService.getShortStockByTicker(ticker);
    }

    @GetMapping("/stocks/{ticker}")
    public StockDto getStockByTicker(@PathVariable String ticker) {
        log.info("Receiving GET request to find stock by ticker = {}", ticker);
        return stockService.getStockByTicker(ticker);
    }

    @GetMapping("/stocks")
    public Map<String, String> getAllStocksNames() {
        log.info("Receiving GET request to find stocks names");
        return stockService.getAllStocksName();
    }
}

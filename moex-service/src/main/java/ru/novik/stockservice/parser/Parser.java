package ru.novik.stockservice.parser;

import ru.novik.stockservice.model.Stock;

import java.util.Map;

public interface Parser {

    Stock parse(String stockAsString);

    Map<String, String> stocksNamesParse(String allStocksAsString);
}

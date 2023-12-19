package ru.novik.stockservice.parser;

import ru.novik.stockservice.dto.SecurityDto;
import ru.novik.stockservice.model.Stock;

import java.util.List;

public interface Parser {

    Stock parse(String stockAsString);

    List<SecurityDto> stocksNamesParse(String allStocksAsString);
}

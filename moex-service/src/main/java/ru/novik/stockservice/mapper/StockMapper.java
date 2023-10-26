package ru.novik.stockservice.mapper;

import org.springframework.stereotype.Component;
import ru.novik.stockservice.dto.StockDto;
import ru.novik.stockservice.dto.StockShortDto;
import ru.novik.stockservice.model.Stock;

@Component
public class StockMapper {

    public StockShortDto mapToShortDto(Stock stock) {
        return StockShortDto.builder()
                .shortName(stock.getShortName())
                .lastPrice(stock.getLastPrice())
                .build();
    }

    public StockDto mapToDto(Stock stock) {
        return StockDto.builder()
                .secId(stock.getSecId())
                .boardId(stock.getBoardId())
                .shortName(stock.getShortName())
                .isin(stock.getIsin())
                .openPrice(stock.getOpenPrice())
                .lowPrice(stock.getLowPrice())
                .highPrice(stock.getHighPrice())
                .lastPrice(stock.getLastPrice())
                .build();
    }
}

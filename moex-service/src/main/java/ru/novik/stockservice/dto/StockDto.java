package ru.novik.stockservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
    private String secId;
    private String boardId;
    private String shortName;
    private String isin;
    private Double openPrice;
    private Double lowPrice;
    private Double highPrice;
    private Double lastPrice;

    @Override
    public String toString() {
        return "secId: " + secId + '\n' +
                "boardId: " + boardId + '\n' +
                "shortName: " + shortName + '\n' +
                "isin: " + isin + '\n' +
                "openPrice: " + openPrice + '\n' +
                "lowPrice: " + lowPrice + '\n' +
                "highPrice: " + highPrice + '\n' +
                "lastPrice: " + lastPrice + '\n';
    }
}

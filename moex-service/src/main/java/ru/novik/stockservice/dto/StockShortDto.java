package ru.novik.stockservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockShortDto {
    private String shortName;
    private Double lastPrice;

    @Override
    public String toString() {
        return shortName + '\n' +
                "Последняя цена: " + lastPrice + '\n';
    }
}

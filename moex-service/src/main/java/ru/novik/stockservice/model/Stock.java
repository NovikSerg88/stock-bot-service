package ru.novik.stockservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Stock {
    private String secId;
    private String boardId;
    private String shortName;
    private String isin;
    private Double openPrice;
    private Double lowPrice;
    private Double highPrice;
    private Double lastPrice;
    private Integer qty;
}

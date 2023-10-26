package ru.novik.stockservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StocksDto {
    Set<String> instruments;

    @Override
    public String toString() {
        return "StocksDto{" +
                "instruments=" + instruments +
                '}';
    }
}

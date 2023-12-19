package ru.novik.telegrambotservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.novik.stockservice.dto.SecurityDto;
import ru.novik.stockservice.dto.StockDto;
import ru.novik.stockservice.dto.StockShortDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class StockService {

    @Value("${moex.server.url}")
    private String moexUrl;

    private final WebClient webClient;

    public StockDto getStockByTicker(String ticker) {
        StockDto stockDto = webClient.get()
                .uri(moexUrl + "/stocks/" + ticker)
                .retrieve()
                .bodyToMono(StockDto.class)
                .block();
        return stockDto;
    }

    public StockShortDto getStockShortByTicker(String ticker) {
        StockShortDto stockShortDto = webClient.get()
                .uri(moexUrl + "/stocks/short/" + ticker)
                .retrieve()
                .bodyToMono(StockShortDto.class)
                .block();
        return stockShortDto;
    }

    public List<SecurityDto> getAllStocksNames() {
        Mono<List<SecurityDto>> response = webClient.get()
                .uri(moexUrl + "/stocks/")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        return response.block();
    }
}

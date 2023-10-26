package ru.novik.stockservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "moexClient", url = "${moex.stocks.url}")
public interface MoexClient {

    //get xml of stock by secId from url
    @GetMapping("/{secId}")
    String getStockFromMoex(@PathVariable("secId") String secId);

    //get xml of all stocks from url
    @GetMapping()
    String getAllStocksName();
}

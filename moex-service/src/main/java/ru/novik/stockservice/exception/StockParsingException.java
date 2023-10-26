package ru.novik.stockservice.exception;

public class StockParsingException extends RuntimeException {

    public StockParsingException(String message, Exception ex) {
        super(message, ex);
    }
}

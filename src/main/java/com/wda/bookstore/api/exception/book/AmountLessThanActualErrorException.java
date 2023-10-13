package com.wda.bookstore.api.exception.book;

public class AmountLessThanActualErrorException extends Throwable {
    public AmountLessThanActualErrorException(String message) {
        super (message);
    }
}

package com.wda.bookstore.api.exception.book;

public class BookRentExists extends Throwable {
    public BookRentExists(String message) {
        super(message);
    }
}

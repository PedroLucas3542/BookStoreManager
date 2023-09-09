package com.wda.bookstore.api.exception.publisher;

public class PublisherHasBooksException extends RuntimeException {
    public PublisherHasBooksException(String message) {
        super(message);
    }
}

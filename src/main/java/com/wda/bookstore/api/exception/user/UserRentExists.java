package com.wda.bookstore.api.exception.user;

public class UserRentExists extends RuntimeException {
    public UserRentExists(String message) {
        super(message);
    }
}

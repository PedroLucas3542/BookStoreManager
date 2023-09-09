package com.wda.bookstore.api.exception.user;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {
    public UserAlreadyExistsException(String name, String email) {
        super(String.format("User with name %s or email %s already exists!", name, email));
    }
}

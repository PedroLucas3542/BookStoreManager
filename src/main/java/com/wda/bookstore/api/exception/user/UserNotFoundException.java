package com.wda.bookstore.api.exception.user;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id %s does not exists!", id));
    }
}

package com.wda.bookstore.api.exception.user;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {
    public UserAlreadyExistsException(String email) {
        super(String.format("Usuário com o email %s já existe!", email));
    }
}

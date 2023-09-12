package com.wda.bookstore.api.exception.user;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {
    public UserAlreadyExistsException(String name, String email) {
        super(String.format("Usuário com o nome %s ou o email %s já existe!", name, email));
    }
}

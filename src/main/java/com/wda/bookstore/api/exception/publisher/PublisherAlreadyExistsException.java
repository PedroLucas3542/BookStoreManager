package com.wda.bookstore.api.exception.publisher;

import javax.persistence.EntityExistsException;

public class PublisherAlreadyExistsException extends EntityExistsException {
    public PublisherAlreadyExistsException(String name) {
        super(String.format("Editora com o nome %s já existe!", name));
    }
}

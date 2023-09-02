package com.wda.bookstore.publisher.exception;

import javax.persistence.EntityExistsException;

public class PublisherAlreadyExistsException extends EntityExistsException {
    public PublisherAlreadyExistsException(String name) {
        super(String.format("Publisher with name %s already exists!", name));
    }
}

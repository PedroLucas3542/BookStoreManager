package com.wda.bookstore.api.exception.book;

import javax.persistence.EntityExistsException;

public class BookNotFoundException extends EntityExistsException {
    public BookNotFoundException(Long id) {
        super(String.format("Book with id %s does not exists!", id));
    }

}

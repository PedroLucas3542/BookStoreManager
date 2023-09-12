package com.wda.bookstore.api.exception.book;

import javax.persistence.EntityExistsException;

public class BookNotFoundException extends EntityExistsException {
    public BookNotFoundException(Long id) {
        super(String.format("Livro com id %s não existe!", id));
    }

}

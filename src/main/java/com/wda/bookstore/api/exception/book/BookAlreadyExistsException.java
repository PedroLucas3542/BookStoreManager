package com.wda.bookstore.api.exception.book;

import javax.persistence.EntityExistsException;

public class BookAlreadyExistsException extends EntityExistsException {

        public BookAlreadyExistsException(String message) {
            super(message);
        }

        public BookAlreadyExistsException(String message, Throwable cause) {
            super(message, cause);
    }

}

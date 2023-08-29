package com.wda.bookstore.books.repository;

import com.wda.bookstore.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

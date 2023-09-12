package com.wda.bookstore.api.repository;

import com.wda.bookstore.api.entity.BookEntity;
import com.wda.bookstore.api.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    boolean existsByNameAndPublisher(String name, PublisherEntity foundPublisher);

    boolean existsBooksByPublisher(@Param("publisher") PublisherEntity publisher);
}

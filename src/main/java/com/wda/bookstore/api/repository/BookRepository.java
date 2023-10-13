package com.wda.bookstore.api.repository;

import com.wda.bookstore.api.entity.BookEntity;
import com.wda.bookstore.api.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM BookEntity b WHERE LOWER(b.name) = LOWER(:bookName) AND b.publisher = :publisher")
    boolean existsByNameAndPublisher(String bookName, PublisherEntity publisher);
    boolean existsBooksByPublisher(@Param("publisher") PublisherEntity publisher);
    List<BookEntity> findByAmountGreaterThan(int amount);
    boolean existsByNameAndPublisherAndIdNot(String name, PublisherEntity publisher, Long idToExclude);
    List<BookEntity> findByTotalRentedGreaterThan(int totalRented);
}

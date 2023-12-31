package com.wda.bookstore.api.repository;

import com.wda.bookstore.api.entity.BookEntity;
import com.wda.bookstore.api.entity.RentalEntity;
import com.wda.bookstore.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

    List<RentalEntity> findByUser(UserEntity user);

    List<RentalEntity> findByBook(BookEntity book);

    List<RentalEntity> findByBookIdAndUserIdAndStatus(Long bookId, Long userId, String status);
}

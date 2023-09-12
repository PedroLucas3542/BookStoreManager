package com.wda.bookstore.api.repository;

import com.wda.bookstore.api.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

}

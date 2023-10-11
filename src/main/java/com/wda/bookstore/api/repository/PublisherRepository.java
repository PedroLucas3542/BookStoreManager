package com.wda.bookstore.api.repository;

import com.wda.bookstore.api.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Long> {

    @Query("SELECT u FROM PublisherEntity u WHERE LOWER(u.name) = LOWER(:name)")
    Optional<PublisherEntity> findByName(String name);
}

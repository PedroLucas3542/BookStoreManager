package com.wda.bookstore.users.repository;

import com.wda.bookstore.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity, Long> {
}

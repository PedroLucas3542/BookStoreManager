package com.wda.bookstore.api.entity.rental;

import com.wda.bookstore.api.entity.book.BookEntity;
import com.wda.bookstore.api.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private UserEntity user;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private BookEntity book;

    private String rentDate;

    private String returnDate;

    private String dueDate;

}

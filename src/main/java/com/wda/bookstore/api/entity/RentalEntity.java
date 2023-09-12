package com.wda.bookstore.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate rentDate;

    private LocalDate returnDate;

    private LocalDate dueDate;
}

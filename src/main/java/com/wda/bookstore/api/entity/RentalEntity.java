package com.wda.bookstore.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @ManyToOne
    private UserEntity user;

    @JsonBackReference
    @ManyToOne
    private BookEntity book;

    private LocalDate rentDate;

    private LocalDate returnDate;

    private LocalDate previsionDate;

    private String status;
}

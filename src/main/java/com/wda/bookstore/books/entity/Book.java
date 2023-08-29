package com.wda.bookstore.books.entity;

import com.wda.bookstore.entity.Auditable;
import com.wda.bookstore.publisher.entity.Publisher;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "integer default 0", length = 4)
    private int birthYear;

    @Column(columnDefinition = "integer default 0")
    private int amount;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Publisher publisher;
}

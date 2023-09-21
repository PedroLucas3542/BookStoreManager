package com.wda.bookstore.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wda.bookstore.api.entity.PublisherEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class BookEntity {
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

    @Column(columnDefinition = "integer default 0")
    private int totalRented;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private PublisherEntity publisher;

    @JsonManagedReference
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<RentalEntity> rents;
}

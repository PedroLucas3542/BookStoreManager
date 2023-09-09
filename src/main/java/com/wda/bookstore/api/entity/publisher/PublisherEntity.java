package com.wda.bookstore.api.entity.publisher;

import com.wda.bookstore.api.entity.book.BookEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class PublisherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String cidade;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    private List<BookEntity> books;
}
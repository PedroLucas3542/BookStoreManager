package com.wda.bookstore.api.dto.book;

import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.dto.rental.RentalDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotEmpty
    private String author;

    @Column(columnDefinition = "integer default 0")
    @NotNull
    private Integer birthYear;

    @Column(columnDefinition = "integer default 0")
    @NotNull
    private int amount;

    @Column(columnDefinition = "integer default 0")
    private int totalRented;

    private PublisherDTO publisher;
}

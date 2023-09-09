package com.wda.bookstore.api.dto.book;

import com.wda.bookstore.api.dto.publisher.PublisherDTO;
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

    @Column(columnDefinition = "integer default 0", length = 4)
    @NotNull
    private int birthYear;

    @Column(columnDefinition = "integer default 0")
    @NotNull
    @Min(value = 0, message = "A quantidade deve ser igual ou maior que 0")
    private int amount;

    private PublisherDTO publisher;


}

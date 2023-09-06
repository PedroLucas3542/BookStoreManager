package com.wda.bookstore.publishers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    @Column(nullable = false)
    @NotNull
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotEmpty
    private String cidade;
}
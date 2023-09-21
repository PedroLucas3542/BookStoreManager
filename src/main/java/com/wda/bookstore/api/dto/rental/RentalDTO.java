package com.wda.bookstore.api.dto.rental;

import com.wda.bookstore.api.dto.book.BookDTO;
import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.entity.BookEntity;
import com.wda.bookstore.api.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class RentalDTO {
    private Long id;

    private UserDTO user;

    private BookDTO book;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentDate;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private String status;
}

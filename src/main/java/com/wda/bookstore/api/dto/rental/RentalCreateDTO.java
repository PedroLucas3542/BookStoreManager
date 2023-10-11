package com.wda.bookstore.api.dto.rental;

import com.wda.bookstore.api.dto.book.BookDTO;
import com.wda.bookstore.api.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
public class RentalCreateDTO {

    private Long userId;

    private Long bookId;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentDate;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate previsionDate;

    private String status;
}

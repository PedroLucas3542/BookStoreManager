package com.wda.bookstore.api.dto.rental;

import com.wda.bookstore.api.dto.book.BookDTO;
import com.wda.bookstore.api.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
public class RentalDTO {
    private Long id;

    private UserDTO user;

    private BookDTO book;

    private String rentDate;

    private String returnDate;

    private String dueDate;

}

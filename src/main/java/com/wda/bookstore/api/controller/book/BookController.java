package com.wda.bookstore.api.controller.book;

import com.wda.bookstore.api.dto.book.BookCreateDTO;
import com.wda.bookstore.api.dto.book.BookDTO;
import com.wda.bookstore.api.dto.book.BookUpdateDTO;
import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.exception.book.AmountErrorException;
import com.wda.bookstore.api.exception.book.AmountLessThanActualErrorException;
import com.wda.bookstore.api.exception.book.BookRentExists;
import com.wda.bookstore.api.exception.book.YearErrorException;
import com.wda.bookstore.api.service.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Api(tags = "Books")
public class BookController implements BookControllerDocs{

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookCreateDTO create(@RequestBody @Valid BookCreateDTO bookDTO) throws YearErrorException, AmountErrorException {
        return bookService.create(bookDTO);
    }

    @GetMapping("/rented-books")
    public List<BookDTO> getRentedBooks() {
        return bookService.getRentedBooks();
    }

    @GetMapping("/all")
    public List<BookDTO> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/available-books")
    public List<BookDTO> getAvailableBooks() { return bookService.getAvailableBooks(); }

    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws BookRentExists {
        bookService.delete(id);
    }
    @PutMapping
    public BookUpdateDTO update(@RequestBody BookUpdateDTO bookToUpdateDTO) throws YearErrorException, AmountErrorException, AmountLessThanActualErrorException {
        return bookService.update(bookToUpdateDTO);
    }
}

package com.wda.bookstore.api.controller.book;

import com.wda.bookstore.api.dto.book.BookCreateDTO;
import com.wda.bookstore.api.dto.book.BookDTO;
import com.wda.bookstore.api.dto.book.BookUpdateDTO;
import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.exception.book.AmountErrorException;
import com.wda.bookstore.api.exception.book.AmountLessThanActualErrorException;
import com.wda.bookstore.api.exception.book.BookRentExists;
import com.wda.bookstore.api.exception.book.YearErrorException;
import io.swagger.annotations.*;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api("Books Model Management")
public interface BookControllerDocs {
    @ApiOperation(value = "Book Creation Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success book creation"),
            @ApiResponse(code = 400, message = "Missing required fields or this book already exists"),
    })
    public BookCreateDTO create(@ApiParam(name = "body", value = "Representation of a new book", required = true)@RequestBody @Valid BookCreateDTO bookDTO) throws YearErrorException, AmountErrorException;

    @ApiOperation(value = "List All Book Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all registered books"),

    })
    List<BookDTO> findAll();

    @ApiOperation(value = "List All Avaible Book Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all registered books"),

    })
    List<BookDTO> getAvailableBooks();


    @ApiOperation(value = "List All Avaible Book Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all rented books"),

    })
    List<BookDTO> getRentedBooks();

    @ApiOperation(value = "Find Book By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success book found"),
            @ApiResponse(code = 404, message = "Book not found error"),
    })
    BookDTO findById(Long id);

    @ApiOperation(value = "Delete Book By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success book deleted"),
            @ApiResponse(code = 404, message = "Book not found error"),
    })
    void delete(Long id) throws BookRentExists;

    @ApiOperation(value = "Update Book By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success book updated"),
            @ApiResponse(code = 400, message = "Missing required fields or this book already exists"),
    })
    BookUpdateDTO update(@ApiParam(name = "body", value = "Representation of a edited book", required = true)BookUpdateDTO bookToUpdateDTO) throws YearErrorException, AmountErrorException, AmountLessThanActualErrorException;
}

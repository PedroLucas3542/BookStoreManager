package com.wda.bookstore.api.controller.rental;

import com.wda.bookstore.api.dto.rental.RentalCreateDTO;
import com.wda.bookstore.api.dto.rental.RentalDTO;
import com.wda.bookstore.api.dto.rental.RentalUpdateDTO;
import com.wda.bookstore.api.exception.book.UnavaiableBookException;
import com.wda.bookstore.api.exception.book.UnavaiableBookException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

public interface RentalControllerDocs {

    @ApiOperation(value = "Rental Creation Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success rental creation"),
            @ApiResponse(code = 400, message = "Missing required fields or this rental already exists"),
    })
    RentalCreateDTO create(RentalCreateDTO rentalDTO) throws UnavaiableBookException;

    @ApiOperation(value = "List All Rentals Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all registered rentals"),

    })
    List<RentalDTO> getAllRentals();

    @ApiOperation(value = "Update Rental By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Rental updated"),
            @ApiResponse(code = 400, message = "Missing required fields or this Rental already exists"),
    })
    RentalUpdateDTO update(RentalUpdateDTO rentalDTO);
}

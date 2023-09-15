package com.wda.bookstore.api.controller.rental;

import com.wda.bookstore.api.dto.rental.RentalDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.LocalDate;
import java.util.List;

public interface RentalControllerDocs {

    @ApiOperation(value = "Rental Creation Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success rental creation"),
            @ApiResponse(code = 400, message = "Missing required fields or this rental already exists"),
    })
    RentalDTO create(RentalDTO rentalDTO);

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
    RentalDTO update(RentalDTO rentalDTO);
}

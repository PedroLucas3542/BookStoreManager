package com.wda.bookstore.api.controller.rental;

import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.dto.rental.RentalDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface RentalControllerDocs {

    @ApiOperation(value = "Rental Creation Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success rental creation"),
            @ApiResponse(code = 400, message = "Missing required fields or this rental already exists"),
    })
    RentalDTO create(RentalDTO rentalDTO);
}

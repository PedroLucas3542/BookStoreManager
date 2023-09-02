package com.wda.bookstore.publisher.controller;

import com.wda.bookstore.publisher.dto.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
public interface PublisherControllerDocs {

    @ApiOperation(value = "Publisher Creation Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success publisher creation"),
            @ApiResponse(code = 400, message = "Missing required fields or this publisher already exists"),
    })
    PublisherDTO create(PublisherDTO publisherDTO);

    @ApiOperation(value = "Find Publisher By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success publisher found"),
            @ApiResponse(code = 400, message = "Publisher not found error"),
    })
    PublisherDTO findById(Long id);
}

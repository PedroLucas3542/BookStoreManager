package com.wda.bookstore.api.controller.publisher;

import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

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
            @ApiResponse(code = 404, message = "Publisher not found error"),
    })
    PublisherDTO findById(Long id);

    @ApiOperation(value = "List All Publisher Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all registered publishers"),

    })
    List<PublisherDTO> findAll();

    @ApiOperation(value = "Delete Publisher By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success publisher deleted"),
            @ApiResponse(code = 404, message = "Publisher not found error"),
    })
    void delete(Long id);

    @ApiOperation(value = "Update Publisher By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success publisher updated"),
            @ApiResponse(code = 400, message = "Missing required fields or this publisher already exists"),
    })
    PublisherDTO update(PublisherDTO publisherToUpdateDTO);

}

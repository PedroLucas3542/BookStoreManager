package com.wda.bookstore.users.controller;

import com.wda.bookstore.users.dto.UserDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

public interface UserControllerDocs {

    @ApiOperation(value = "User Creation Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success user creation"),
            @ApiResponse(code = 400, message = "Missing required fields or this user already exists"),
    })
    public UserDTO create(UserDTO userDTO);

    @ApiOperation(value = "List All User Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all registered users"),

    })
    List<UserDTO> findAll();

    @ApiOperation(value = "Find User By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success user found"),
            @ApiResponse(code = 404, message = "User not found error"),
    })
    UserDTO findById(Long id);

    @ApiOperation(value = "Update User By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success user updated"),
            @ApiResponse(code = 400, message = "Missing required fields or this user already exists"),
    })
    UserDTO update(Long id, UserDTO userToUpdateDTO);

    @ApiOperation(value = "Delete User By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success user deleted"),
            @ApiResponse(code = 404, message = "User not found error"),
    })
    void delete(Long id);
}

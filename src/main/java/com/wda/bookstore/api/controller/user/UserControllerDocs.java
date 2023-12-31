package com.wda.bookstore.api.controller.user;

import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.dto.user.UserCreateDTO;
import com.wda.bookstore.api.exception.user.AlreadyOnListException;
import com.wda.bookstore.api.exception.user.UserRentExists;
import io.swagger.annotations.*;
import org.springframework.beans.factory.parsing.Problem;

import java.util.List;

@Api(tags = "Users")
@ApiResponses({@ApiResponse(code = 500, message = "There was an internal error", response = Problem.class)})
public interface UserControllerDocs {

    @ApiOperation(value = "User Creation Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success user creation"),
            @ApiResponse(code = 400, message = "Missing required fields or this user already exists"),
    })
    UserCreateDTO create(@ApiParam(name = "body", value = "Representation of a new user", required = true) UserCreateDTO userDTO);

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
    UserDTO update(@ApiParam(name = "body", value = "Representation of a edited user", required = true)UserDTO userToUpdateDTO) throws AlreadyOnListException;

    @ApiOperation(value = "Delete User By ID Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success user deleted"),
            @ApiResponse(code = 404, message = "User not found error"),
    })
    void delete(@ApiParam(value = "User id", example = "7", required = true)
                Long id) throws UserRentExists;
}

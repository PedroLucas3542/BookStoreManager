package com.wda.bookstore.users.controller;

import com.wda.bookstore.users.service.UsersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController implements UsersControllerDocs{

    private UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }
}

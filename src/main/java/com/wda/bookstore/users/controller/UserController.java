package com.wda.bookstore.users.controller;

import com.wda.bookstore.users.dto.UserDTO;
import com.wda.bookstore.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDocs{

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody @Valid UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id,@RequestBody @Valid UserDTO userToUpdateDTO) {
        return userService.update(userToUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}

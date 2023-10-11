package com.wda.bookstore.api.controller.user;

import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.dto.user.UserCreateDTO;
import com.wda.bookstore.api.exception.user.AlreadyOnListException;
import com.wda.bookstore.api.exception.user.UserRentExists;
import com.wda.bookstore.api.service.UserService;
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
    public UserCreateDTO create(@RequestBody @Valid UserCreateDTO userDTO) {
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

    @PutMapping
    public UserDTO update(@RequestBody @Valid UserDTO userToUpdateDTO) throws AlreadyOnListException {
        return userService.update(userToUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id) throws UserRentExists {
        userService.delete(id);
    }
}

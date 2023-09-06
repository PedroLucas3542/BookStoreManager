package com.wda.bookstore.users.service;


import com.wda.bookstore.users.dto.UserDTO;
import com.wda.bookstore.users.entity.UserEntity;
import com.wda.bookstore.users.exception.UserAlreadyExistsException;
import com.wda.bookstore.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO create(UserDTO userDTO){
        verifyIfExists(userDTO.getName(), userDTO.getEmail());

        UserEntity userToCreate = modelMapper.map(userDTO, UserEntity.class);

        UserEntity createdUser = userRepository.save(userToCreate);

        return modelMapper.map(createdUser, UserDTO.class);
    }

    private void verifyIfExists(String name, String email) {
        Optional<UserEntity> duplicatedUser = userRepository
                .findByNameOrEmail(name, email);
        if (duplicatedUser.isPresent()) {
            throw new UserAlreadyExistsException(name, email);
        }
    }
}
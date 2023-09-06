package com.wda.bookstore.users.service;


import com.wda.bookstore.users.dto.UserDTO;
import com.wda.bookstore.users.entity.UserEntity;
import com.wda.bookstore.users.exception.UserAlreadyExistsException;
import com.wda.bookstore.users.exception.UserNotFoundException;
import com.wda.bookstore.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<UserDTO> findAll() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO update(UserDTO userToUpdateDTO) {
        UserEntity foundUser = verifyIfIdExists(userToUpdateDTO.getId());

        modelMapper.map(userToUpdateDTO, foundUser);

        UserEntity updatedUser = userRepository.save(foundUser);

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    private UserEntity verifyIfIdExists(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return userOptional.get();
    }

    public UserDTO findById(Long id){
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public void delete(Long id){
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }

}
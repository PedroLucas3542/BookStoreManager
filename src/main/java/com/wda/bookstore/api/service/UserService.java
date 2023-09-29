package com.wda.bookstore.api.service;


import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.entity.RentalEntity;
import com.wda.bookstore.api.entity.UserEntity;
import com.wda.bookstore.api.exception.user.UserAlreadyExistsException;
import com.wda.bookstore.api.exception.user.UserNotFoundException;
import com.wda.bookstore.api.exception.user.UserRentExists;
import com.wda.bookstore.api.repository.RentalRepository;
import com.wda.bookstore.api.repository.UserRepository;
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
    private final RentalRepository rentalRepository;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, RentalRepository rentalRepository){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.rentalRepository = rentalRepository;
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

    public void delete(Long id) throws UserRentExists {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        List<RentalEntity> activeRentals = rentalRepository.findByUser(user);
        if (!activeRentals.isEmpty()) {
            throw new UserRentExists("Não é possível excluir o usuário, pois ele possui aluguéis atrelados a ele.");
        }
        userRepository.deleteById(id);
    }


}
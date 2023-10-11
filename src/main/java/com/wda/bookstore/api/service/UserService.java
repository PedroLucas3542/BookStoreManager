package com.wda.bookstore.api.service;


import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.dto.user.UserCreateDTO;
import com.wda.bookstore.api.entity.PublisherEntity;
import com.wda.bookstore.api.entity.RentalEntity;
import com.wda.bookstore.api.entity.UserEntity;
import com.wda.bookstore.api.exception.publisher.PublisherNotFoundException;
import com.wda.bookstore.api.exception.user.AlreadyOnListException;
import com.wda.bookstore.api.exception.user.UserAlreadyExistsException;
import com.wda.bookstore.api.exception.user.UserNotFoundException;
import com.wda.bookstore.api.exception.user.UserRentExists;
import com.wda.bookstore.api.repository.RentalRepository;
import com.wda.bookstore.api.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
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

    public UserCreateDTO create(@Valid UserCreateDTO userDTO){
        verifyIfExists(userDTO.getEmail());
        UserEntity userToCreate = modelMapper.map(userDTO, UserEntity.class);
        UserEntity createdUser = userRepository.save(userToCreate);
        return modelMapper.map(createdUser, UserCreateDTO.class);
    }

    private void verifyIfExists(String email) {
        Optional<UserEntity> duplicatedUser = userRepository
                .findByEmail(email);
        if (duplicatedUser.isPresent()) {
            throw new UserAlreadyExistsException(email);
        }
    }


    public List<UserDTO> findAll() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO update(UserDTO userToUpdateDTO) throws AlreadyOnListException {
        UserEntity foundUser = verifyIfIdExists(userToUpdateDTO.getId());
        List<UserEntity> usersWithSameNameOrEmail = userRepository.findByEmailList(
                userToUpdateDTO.getEmail());
        usersWithSameNameOrEmail.removeIf(user -> user.getId().equals(foundUser.getId()));
        if (usersWithSameNameOrEmail.isEmpty()) {
            modelMapper.map(userToUpdateDTO, foundUser);
            UserEntity updatedUser = userRepository.save(foundUser);
            return modelMapper.map(updatedUser, UserDTO.class);
        } else {
            throw new AlreadyOnListException("Já existe um usuário com o mesmo email.");
        }
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

    public UserEntity verifyAndGetIfExists(Long id){
        return userRepository.findById(id)
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
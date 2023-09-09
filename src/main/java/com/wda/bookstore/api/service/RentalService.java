package com.wda.bookstore.api.service;

import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.dto.rental.RentalDTO;
import com.wda.bookstore.api.entity.publisher.PublisherEntity;
import com.wda.bookstore.api.entity.rental.RentalEntity;
import com.wda.bookstore.api.repository.RentalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RentalService(RentalRepository rentalRepository, ModelMapper modelMapper) {
        this.rentalRepository = rentalRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<RentalEntity> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public RentalDTO create(RentalDTO rentalDTO) {
        RentalEntity rentalToCreate = modelMapper.map(rentalDTO, RentalEntity.class);
        RentalEntity createdRental = rentalRepository.save(rentalToCreate);
        return modelMapper.map(createdRental, RentalDTO.class);
    }

    public List<RentalEntity> getAllRentals() {
        return rentalRepository.findAll();
    }

    public RentalEntity updateRental(Long id, RentalEntity updatedRental) {
        Optional<RentalEntity> existingRentalOptional = rentalRepository.findById(id);
        if (existingRentalOptional.isPresent()) {
            RentalEntity existingRental = existingRentalOptional.get();

            return rentalRepository.save(existingRental);
        } else {
            return null;
        }
    }

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
}


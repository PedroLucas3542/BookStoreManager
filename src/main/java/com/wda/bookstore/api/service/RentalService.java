package com.wda.bookstore.api.service;

import com.wda.bookstore.api.dto.rental.RentalDTO;
import com.wda.bookstore.api.entity.RentalEntity;
import com.wda.bookstore.api.repository.RentalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    private final ModelMapper modelMapper;

    private RentalDTO convertToDTO(RentalEntity rentalEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(rentalEntity, RentalDTO.class);
    }


    @Autowired
    public RentalService(RentalRepository rentalRepository, ModelMapper modelMapper) {
        this.rentalRepository = rentalRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<RentalEntity> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public RentalDTO create(RentalDTO rentalDTO) {
        rentalDTO.setStatus("Pendente");
        RentalEntity rentalToCreate = modelMapper.map(rentalDTO, RentalEntity.class);
        RentalEntity createdRental = rentalRepository.save(rentalToCreate);
        return modelMapper.map(createdRental, RentalDTO.class);
    }

    public List<RentalDTO> getAllRentals() {
        List<RentalEntity> rentals = rentalRepository.findAll();

        return rentals.stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());
    }

    public RentalDTO update(RentalDTO rentalDTO) {
        RentalEntity rentToUpdate = verifyIfIdExists(rentalDTO.getId());

        LocalDate returnDate = rentalDTO.getReturnDate();
        LocalDate dueDate = rentalDTO.getDueDate();

        if (returnDate == null || returnDate.isBefore(dueDate)) {
            rentToUpdate.setStatus("Atrasado");
        } else {
            rentToUpdate.setStatus("Devolvido");
        }

        rentToUpdate.setReturnDate(returnDate);

        RentalEntity updatedRental = rentalRepository.save(rentToUpdate);

        return convertToDTO(updatedRental);
    }



    private RentalEntity verifyIfIdExists(Long id) {
        Optional<RentalEntity> rentalOptional = rentalRepository.findById(id);

        if (rentalOptional.isPresent()) {
            return rentalOptional.get();
        } else {
            throw new EntityNotFoundException("Aluguel não encontrado com o ID: " + id);
        }
    }


}


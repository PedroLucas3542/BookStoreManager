package com.wda.bookstore.api.service;

import com.wda.bookstore.api.dto.book.BookDTO;
import com.wda.bookstore.api.dto.rental.RentalDTO;
import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.entity.BookEntity;
import com.wda.bookstore.api.entity.RentalEntity;
import com.wda.bookstore.api.entity.UserEntity;
import com.wda.bookstore.api.repository.BookRepository;
import com.wda.bookstore.api.repository.RentalRepository;
import com.wda.bookstore.api.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    @Autowired
    private BookRepository bookRepository;

    private final RentalRepository rentalRepository;

    private final ModelMapper modelMapper;

    private RentalDTO convertToDTO(RentalEntity rentalEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(rentalEntity, RentalDTO.class);
    }

    @Autowired
    public RentalService(BookRepository bookRepository, RentalRepository rentalRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.rentalRepository = rentalRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<RentalEntity> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public RentalDTO create(RentalDTO rentalDTO) {
        rentalDTO.setStatus("Pendente");
        Long bookId = rentalDTO.getBook().getId();
        UserDTO user = rentalDTO.getUser();

        if (bookId != null && user != null) {
            BookEntity bookEntity = bookRepository.findById(bookId).orElse(null);
            if (bookEntity != null) {
                rentalDTO.getBook().setTotalRented(bookEntity.getTotalRented() + 1);
                rentalDTO.getBook().setAmount(bookEntity.getAmount() - 1);
                bookRepository.save(bookEntity);

                RentalEntity rentalToCreate = modelMapper.map(rentalDTO, RentalEntity.class);
                RentalEntity createdRental = rentalRepository.save(rentalToCreate);
                return modelMapper.map(createdRental, RentalDTO.class);
            }
        }
        return rentalDTO;
    }

    public List<RentalDTO> getAllRentals() {
        List<RentalEntity> rentals = rentalRepository.findAll();

        return rentals.stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());
    }

    public RentalDTO update(RentalDTO rentalDTO) {
        Long bookId = rentalDTO.getBook().getId();
        RentalEntity rentToUpdate = verifyIfIdExists(rentalDTO.getId());
        LocalDate returnDate = rentalDTO.getReturnDate();
        LocalDate dueDate = rentalDTO.getDueDate();
        BookEntity bookEntity = bookRepository.findById(bookId).orElse(null);
        if (bookEntity != null) {
            if (returnDate == null || returnDate.isBefore(dueDate)) {
                rentToUpdate.setStatus("Devolvido");
            } else {
                rentToUpdate.setStatus("Atrasado");
            }
            bookEntity.setTotalRented(bookEntity.getTotalRented() - 1);
            bookEntity.setAmount(bookEntity.getAmount() + 1);
            bookRepository.save(bookEntity);

            rentToUpdate.setReturnDate(returnDate);
            RentalEntity updatedRental = rentalRepository.save(rentToUpdate);
            return convertToDTO(updatedRental);
        }
        return rentalDTO;
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


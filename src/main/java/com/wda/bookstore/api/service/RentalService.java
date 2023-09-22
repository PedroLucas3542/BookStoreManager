package com.wda.bookstore.api.service;

import com.wda.bookstore.api.dto.rental.RentalDTO;
import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.entity.BookEntity;
import com.wda.bookstore.api.entity.RentalEntity;
import com.wda.bookstore.api.exception.book.UnavaiableBookException;
import com.wda.bookstore.api.repository.BookRepository;
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

    public RentalDTO create(RentalDTO rentalDTO) throws UnavaiableBookException {
        Long bookId = rentalDTO.getBook().getId();

            rentalDTO.setStatus("Pendente");
            UserDTO user = rentalDTO.getUser();

            if (bookId != null && user != null) {
                BookEntity bookEntity = bookRepository.findById(bookId).orElse(null);
                if (bookEntity != null) {
                        if (!isBookAvailable(rentalDTO.getBook().getId())) {
                            throw new UnavaiableBookException("Este livro não está disponível para aluguel.");
                        }
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

    private boolean isBookAvailable(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElse(null);
        return bookEntity != null && bookEntity.getAmount() > bookEntity.getTotalRented();
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


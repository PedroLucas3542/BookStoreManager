package com.wda.bookstore.api.service;

import com.wda.bookstore.api.dto.book.BookDTO;
import com.wda.bookstore.api.dto.rental.RentalCreateDTO;
import com.wda.bookstore.api.dto.rental.RentalDTO;
import com.wda.bookstore.api.dto.rental.RentalUpdateDTO;
import com.wda.bookstore.api.dto.user.UserDTO;
import com.wda.bookstore.api.entity.BookEntity;
import com.wda.bookstore.api.entity.PublisherEntity;
import com.wda.bookstore.api.entity.RentalEntity;
import com.wda.bookstore.api.entity.UserEntity;
import com.wda.bookstore.api.exception.book.UnavaiableBookException;
import com.wda.bookstore.api.repository.BookRepository;
import com.wda.bookstore.api.repository.RentalRepository;
import com.wda.bookstore.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RentalService {

    private UserRepository userRepository;

    private BookService bookService;

    private UserService userService;

    private BookRepository bookRepository;

    private RentalRepository rentalRepository;

    private ModelMapper modelMapper;

    private RentalDTO convertToDTO(RentalEntity rentalEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(rentalEntity, RentalDTO.class);
    }

    public Optional<RentalEntity> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public RentalCreateDTO create(RentalCreateDTO rentalDTO) throws UnavaiableBookException {
        Long bookId = rentalDTO.getBookId();
        Long userId = rentalDTO.getUserId();
        BookEntity foundBook = bookService.verifyAndGetIfExists(bookId);
        UserEntity foundUser = userService.verifyAndGetIfExists(userId);
        if (bookId != null && userId != null) {
            BookEntity bookEntity = bookRepository.findById(bookId).orElse(null);
            if (bookEntity != null) {
                if (!isBookAvailable(bookId)) {
                    throw new UnavaiableBookException("Este livro não está disponível para aluguel.");
                }
                if (isBookRentedToUser(bookId, userId)) {
                    throw new UnavaiableBookException("Este usuário já tem um aluguel ativo com este livro");
                }

                RentalEntity rentalEntity = new RentalEntity();
                rentalEntity.setStatus("Pendente");
                rentalEntity.setBook(bookEntity);
                rentalEntity.setUser(userRepository.findById(userId).orElse(null));

                rentalEntity.setRentDate(rentalDTO.getRentDate());
                rentalEntity.setPrevisionDate(rentalDTO.getPrevisionDate());

                bookEntity.setTotalRented(bookEntity.getTotalRented() + 1);
                bookEntity.setAmount(bookEntity.getAmount() - 1);
                bookRepository.save(bookEntity);

                rentalRepository.save(rentalEntity);

                RentalCreateDTO createdRentalDTO = new RentalCreateDTO();
                createdRentalDTO.setStatus(rentalEntity.getStatus());
                createdRentalDTO.setUserId(rentalEntity.getUser().getId());
                createdRentalDTO.setBookId(rentalEntity.getBook().getId());
                createdRentalDTO.setRentDate(rentalEntity.getRentDate());
                createdRentalDTO.setPrevisionDate(rentalEntity.getPrevisionDate());

                return createdRentalDTO;
            }
        }
        return rentalDTO;
    }

    private boolean isBookRentedToUser(Long bookId, Long userId) {
        List<RentalEntity> activeRentals = rentalRepository.findByBookIdAndUserIdAndStatus(bookId, userId, "Pendente");
        return !activeRentals.isEmpty();
    }


    private boolean isBookAvailable(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElse(null);
        return bookEntity != null && bookEntity.getAmount() > 0;
    }

    public List<RentalDTO> getAllRentals() {
        List<RentalEntity> rentals = rentalRepository.findAll();

        return rentals.stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());
    }

    public RentalUpdateDTO update(RentalUpdateDTO rentalDTO) {
        RentalEntity rentToUpdate = verifyIfIdExists(rentalDTO.getId());
        Long bookId = rentToUpdate.getBook().getId();
        LocalDate returnDate = rentalDTO.getReturnDate();
        LocalDate dueDate = rentToUpdate.getPrevisionDate();
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
            rentalRepository.save(rentToUpdate);
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


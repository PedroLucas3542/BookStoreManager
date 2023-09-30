package com.wda.bookstore.api.service;

import com.wda.bookstore.api.dto.book.BookDTO;
import com.wda.bookstore.api.entity.BookEntity;
import com.wda.bookstore.api.entity.RentalEntity;
import com.wda.bookstore.api.exception.book.*;
import com.wda.bookstore.api.exception.publisher.PublisherNotFoundException;
import com.wda.bookstore.api.repository.BookRepository;
import com.wda.bookstore.api.entity.PublisherEntity;
import com.wda.bookstore.api.repository.PublisherRepository;
import com.wda.bookstore.api.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private  PublisherService publisherService;

    private final RentalRepository rentalRepository;

    private PublisherRepository publisherRepository;

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public BookService(RentalRepository rentalRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public BookDTO create(BookDTO bookDTO) throws YearErrorException, AmountErrorException {
        int currentYear = Year.now().getValue();
        if (bookDTO.getPublishingYear() > currentYear) {
            throw new YearErrorException("O ano de criação do livro deve ser do ano atual para trás.");
        }

        if (bookDTO.getAmount() <= 0){
            throw new AmountErrorException("A quantidade de livros deve ser maior que zero.");
        }

        PublisherEntity publisher = modelMapper.map(bookDTO.getPublisher(), PublisherEntity.class);
        verifyIfExists(bookDTO.getName(), publisher);

        BookEntity bookToSave = modelMapper.map(bookDTO, BookEntity.class);
        BookEntity createdBook = bookRepository.save(bookToSave);
        return modelMapper.map(createdBook, BookDTO.class);
    }

    public BookDTO update(BookDTO bookToUpdateDTO) throws YearErrorException, AmountErrorException {
        int currentYear = Year.now().getValue();
        if (bookToUpdateDTO.getPublishingYear() > currentYear) {
            throw new YearErrorException("O ano de nascimento do livro deve ser do ano atual para trás.");
        }
        if (bookToUpdateDTO.getAmount() <= 0){
            throw new AmountErrorException("A quantidade de livros deve ser maior que zero.");
        }
        BookEntity foundBook = verifyIfIdBookExists(bookToUpdateDTO.getId());
        PublisherEntity publisherEntity = modelMapper.map(bookToUpdateDTO.getPublisher(), PublisherEntity.class);
        if (publisherEntity != null) {
            foundBook.setPublisher(publisherEntity);
        } else {
            throw new PublisherNotFoundException(bookToUpdateDTO.getPublisher().getId());
        }
        modelMapper.map(bookToUpdateDTO, foundBook);
        BookEntity updatedBook = bookRepository.save(foundBook);
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    private BookEntity verifyIfIdBookExists(Long id) {
        Optional<BookEntity> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException(id);
        }
        return bookOptional.get();
    }

    public List<BookDTO> findAll() {
        List<BookEntity> books = bookRepository.findAll();

        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .sorted(Comparator.comparingInt(BookDTO::getTotalRented).reversed())
                .collect(Collectors.toList());
    }

    public List<BookDTO> getAvailableBooks() {
        List<BookEntity> availableBooks = bookRepository.findByAmountGreaterThan(0);

        return availableBooks.stream()
                .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class))
                .collect(Collectors.toList());
    }

    public BookDTO findById(Long id){
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void delete(Long id) throws BookRentExists {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        List<RentalEntity> activeRentals = rentalRepository.findByBook(book);
        if (!activeRentals.isEmpty()) {
            throw new BookRentExists("Não é possível excluir o livro, pois ele possui aluguéis atrelados a ele.");
        }
        bookRepository.deleteById(id);
    }

    private void verifyIfExists(String bookName, PublisherEntity publisher) {
        if (bookRepository.existsByNameAndPublisher(bookName, publisher)) {
            throw new BookAlreadyExistsException("Um livro com o mesmo nome já está associado a esta editora.");
        }
    }

}

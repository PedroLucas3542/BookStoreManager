package com.wda.bookstore.api.service;

import com.wda.bookstore.api.dto.book.BookDTO;
import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.entity.book.BookEntity;
import com.wda.bookstore.api.exception.book.BookAlreadyExistsException;
import com.wda.bookstore.api.exception.book.BookNotFoundException;
import com.wda.bookstore.api.exception.publisher.PublisherNotFoundException;
import com.wda.bookstore.api.repository.BookRepository;
import com.wda.bookstore.api.entity.publisher.PublisherEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private PublisherService publisherService;

    private BookRepository bookRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public BookDTO create(BookDTO bookDTO) {
        PublisherEntity publisher = modelMapper.map(bookDTO.getPublisher(), PublisherEntity.class);
        verifyIfExists(bookDTO.getName(), publisher);


        BookEntity bookToSave = modelMapper.map(bookDTO, BookEntity.class);
        BookEntity createdBook = bookRepository.save(bookToSave);
        return modelMapper.map(createdBook, BookDTO.class);
    }

    public BookDTO update(BookDTO bookToUpdateDTO) {
        BookEntity foundBook = verifyIfIdExists(bookToUpdateDTO.getId());

        modelMapper.map(bookToUpdateDTO, foundBook);

        BookEntity updatedBook = bookRepository.save(foundBook);

        return modelMapper.map(updatedBook, BookDTO.class);
    }

    private BookEntity verifyIfIdExists(Long id) {
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
                .collect(Collectors.toList());
    }

    public BookDTO findById(Long id){
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void delete(Long id){
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.deleteById(id);
    }

    private void verifyIfExists(String bookName, PublisherEntity publisher) {
        if (bookRepository.existsByNameAndPublisher(bookName, publisher)) {
            throw new BookAlreadyExistsException("Um livro com o mesmo nome já está associado a esta editora.");
        }
    }

}

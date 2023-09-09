package com.wda.bookstore.api.service;


import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.entity.publisher.PublisherEntity;
import com.wda.bookstore.api.exception.publisher.PublisherAlreadyExistsException;
import com.wda.bookstore.api.exception.publisher.PublisherHasBooksException;
import com.wda.bookstore.api.exception.publisher.PublisherNotFoundException;
import com.wda.bookstore.api.repository.BookRepository;
import com.wda.bookstore.api.repository.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PublisherService(BookRepository bookRepository, PublisherRepository publisherRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.modelMapper = modelMapper;
    }

    public PublisherDTO create(PublisherDTO publisherDTO){
        verifyIfExists(publisherDTO.getName());

        PublisherEntity publisherToCreate = modelMapper.map(publisherDTO, PublisherEntity.class);

        PublisherEntity createdPublisher = publisherRepository.save(publisherToCreate);

        return modelMapper.map(createdPublisher, PublisherDTO.class);
    }

    public PublisherDTO findById(Long id){
        return publisherRepository.findById(id)
                .map(publisher -> modelMapper.map(publisher, PublisherDTO.class))
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public PublisherDTO update(PublisherDTO publisherToUpdateDTO) {
        PublisherEntity foundPublisher = verifyIfIdExists(publisherToUpdateDTO.getId());

        // Verifique se o nome foi alterado na atualização
        if (!foundPublisher.getName().equals(publisherToUpdateDTO.getName())) {
            // O nome foi alterado, aplique a validação de nome duplicado
            verifyIfExists(publisherToUpdateDTO.getName());
        }

        foundPublisher.setName(publisherToUpdateDTO.getName());
        foundPublisher.setCidade(publisherToUpdateDTO.getCidade());

        PublisherEntity updatedPublisher = publisherRepository.save(foundPublisher);

        return modelMapper.map(updatedPublisher, PublisherDTO.class);
    }


    private PublisherEntity verifyIfIdExists(Long id) {
        Optional<PublisherEntity> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isEmpty()) {
            throw new PublisherNotFoundException(id);
        }
        return publisherOptional.get();
    }

    public List<PublisherDTO> findAll() {
        List<PublisherEntity> publishers = publisherRepository.findAll();

        return publishers.stream()
                .map(publisher -> modelMapper.map(publisher, PublisherDTO.class))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        PublisherEntity publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
        if (bookRepository.existsBooksByPublisher(publisher)) {
            throw new PublisherHasBooksException("A editora possui livros associados e não pode ser excluída.");
        }
        publisherRepository.deleteById(id);
    }

    public PublisherEntity verifyIfAndGetExists(Long id){
       return publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    private void verifyIfExists(String name) {
        Optional<PublisherEntity> duplicatedPublisher = publisherRepository
                .findByName(name);
        if (duplicatedPublisher.isPresent()){
            throw new PublisherAlreadyExistsException(name);
        }
    }
}
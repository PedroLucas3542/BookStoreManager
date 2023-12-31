package com.wda.bookstore.api.service;

import com.wda.bookstore.api.dto.publisher.PublisherCreateDTO;
import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.entity.PublisherEntity;
import com.wda.bookstore.api.exception.publisher.PublisherAlreadyExistsException;
import com.wda.bookstore.api.exception.publisher.PublisherHasBooksException;
import com.wda.bookstore.api.exception.publisher.PublisherNotFoundException;
import com.wda.bookstore.api.repository.BookRepository;
import com.wda.bookstore.api.repository.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public PublisherCreateDTO create(PublisherCreateDTO publisherDTO){
        verifyIfExists(publisherDTO.getName());
        PublisherEntity createdPublisher = publisherRepository.save(modelMapper.map(publisherDTO, PublisherEntity.class));
        return modelMapper.map(createdPublisher, PublisherCreateDTO.class);
    }

    public PublisherDTO findById(Long id){
        return publisherRepository.findById(id)
                .map(publisher -> modelMapper.map(publisher, PublisherDTO.class))
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public PublisherDTO update(PublisherDTO publisherToUpdateDTO) {
        PublisherEntity foundPublisher = verifyIfIdExists(publisherToUpdateDTO.getId());
        if (!foundPublisher.getName().equals(publisherToUpdateDTO.getName())) {
            verifyIfExists(publisherToUpdateDTO.getName());
        }
        foundPublisher.setName(publisherToUpdateDTO.getName());
        foundPublisher.setCity(publisherToUpdateDTO.getCity());
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

    public PublisherEntity verifyAndGetIfExists(Long id){
        return publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
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

    private void verifyIfExists(String name) {
        if (publisherRepository.findByName(name).isPresent()){
            throw new PublisherAlreadyExistsException(name);
        }
    }

    public PublisherEntity findPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Editora não encontrada com o ID: " + id));
    }
}

package com.wda.bookstore.publishers.service;


import com.wda.bookstore.publishers.dto.PublisherDTO;
import com.wda.bookstore.publishers.entity.PublisherEntity;
import com.wda.bookstore.publishers.exception.PublisherAlreadyExistsException;
import com.wda.bookstore.publishers.exception.PublisherNotFoundException;
import com.wda.bookstore.publishers.repository.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, ModelMapper modelMapper) {
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

        modelMapper.map(publisherToUpdateDTO, foundPublisher);

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


    public void delete(Long id){
        publisherRepository.findById(id)
                        .orElseThrow(() -> new PublisherNotFoundException(id));
        publisherRepository.deleteById(id);
    }

    private void verifyIfExists(String name) {
        Optional<PublisherEntity> duplicatedPublisher = publisherRepository
                .findByName(name);
        if (duplicatedPublisher.isPresent()){
            throw new PublisherAlreadyExistsException(name);
        }
    }
}
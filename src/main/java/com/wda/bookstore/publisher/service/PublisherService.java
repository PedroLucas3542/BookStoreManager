package com.wda.bookstore.publisher.service;


import com.wda.bookstore.publisher.dto.PublisherDTO;
import com.wda.bookstore.publisher.entity.PublisherEntity;
import com.wda.bookstore.publisher.exception.PublisherAlreadyExistsException;
import com.wda.bookstore.publisher.mapper.PublisherMapper;
import com.wda.bookstore.publisher.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository){
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO){
        verifyIfExists(publisherDTO.getName());

        PublisherEntity publisherToCreate = publisherMapper.toModel(publisherDTO);
        PublisherEntity createdPubliser = publisherRepository.save(publisherToCreate);
        return  publisherMapper.toDTO(createdPubliser);
    }

    private void verifyIfExists(String name) {
        Optional<PublisherEntity> duplicatedPublisher = publisherRepository
                .findByName(name);
        if (duplicatedPublisher.isPresent()){
            throw new PublisherAlreadyExistsException(name);
        }
    }
}

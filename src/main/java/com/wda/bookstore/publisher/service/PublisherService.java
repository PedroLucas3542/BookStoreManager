package com.wda.bookstore.publisher.service;


import com.wda.bookstore.publisher.dto.PublisherDTO;
import com.wda.bookstore.publisher.entity.PublisherEntity;
import com.wda.bookstore.publisher.exception.PublisherAlreadyExistsException;
import com.wda.bookstore.publisher.exception.PublisherNotFoundException;
import com.wda.bookstore.publisher.mapper.PublisherMapper;
import com.wda.bookstore.publisher.repository.PublisherRepository;
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

        // Use o ModelMapper para mapear os campos do DTO para a entidade
        modelMapper.map(publisherToUpdateDTO, foundPublisher);

        PublisherEntity updatedPublisher = publisherRepository.save(foundPublisher);

        // Use o ModelMapper para mapear a entidade atualizada de volta para DTO
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

        // Use o ModelMapper para mapear a lista de entidades para uma lista de DTOs
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

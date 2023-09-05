package com.wda.bookstore.publisher.service;


import com.wda.bookstore.publisher.dto.PublisherDTO;
import com.wda.bookstore.publisher.entity.PublisherEntity;
import com.wda.bookstore.publisher.exception.PublisherAlreadyExistsException;
import com.wda.bookstore.publisher.exception.PublisherNotFoundException;
import com.wda.bookstore.publisher.mapper.PublisherMapper;
import com.wda.bookstore.publisher.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        PublisherEntity createdPublisher = publisherRepository.save(publisherToCreate);
        return  publisherMapper.toDTO(createdPublisher);
    }

    public PublisherDTO findById(Long id){
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public PublisherDTO update(PublisherDTO publisherToUpdateDTO) {
        PublisherEntity foundPublisher = verifyIfIdExists(publisherToUpdateDTO.getId());

        foundPublisher.setName(publisherToUpdateDTO.getName());
        foundPublisher.setCidade(publisherToUpdateDTO.getCidade());

        PublisherEntity updatedPublisher = publisherRepository.save(foundPublisher);

        return publisherMapper.toDTO(updatedPublisher);
    }

    private PublisherEntity verifyIfIdExists(Long id) {
        Optional<PublisherEntity> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isEmpty()) {
            throw new PublisherNotFoundException(id);
        }
        return publisherOptional.get();
    }


    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::toDTO)
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

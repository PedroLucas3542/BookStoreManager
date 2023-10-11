package com.wda.bookstore.api.controller.publisher;

import com.wda.bookstore.api.dto.publisher.PublisherCreateDTO;
import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.service.PublisherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@Api(tags = "Publishers")
public class PublisherController implements PublisherControllerDocs {

    private PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherCreateDTO create(@RequestBody @Valid PublisherCreateDTO publisherDTO) {
        return publisherService.create(publisherDTO);
    }

    @PutMapping
    public PublisherDTO update(@RequestBody @Valid PublisherDTO publisherToUpdateDTO) {
        return publisherService.update(publisherToUpdateDTO);
    }

    @GetMapping("/{id}")
    public PublisherDTO findById(@PathVariable Long id) {
        return publisherService.findById(id);
    }

    @GetMapping
    public List<PublisherDTO> findAll() {
        return publisherService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        publisherService.delete(id);
    }
}
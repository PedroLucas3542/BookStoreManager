package com.wda.bookstore.api.controller.rental;

import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.dto.rental.RentalDTO;
import com.wda.bookstore.api.entity.RentalEntity;
import com.wda.bookstore.api.service.RentalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Rentals")
@RestController
@RequestMapping("/api/v1/rentals")
public class RentalController implements RentalControllerDocs{

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentalDTO create(@RequestBody @Valid RentalDTO rentalDTO) {
        return rentalService.create(rentalDTO);
    }

    @GetMapping
    public List<RentalDTO> getAllRentals() {
        return rentalService.getAllRentals();
    }
}

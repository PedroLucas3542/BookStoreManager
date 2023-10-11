package com.wda.bookstore.api.controller.rental;

import com.wda.bookstore.api.dto.rental.RentalCreateDTO;
import com.wda.bookstore.api.dto.rental.RentalDTO;
import com.wda.bookstore.api.dto.rental.RentalUpdateDTO;
import com.wda.bookstore.api.exception.book.UnavaiableBookException;
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
    @Override
    public RentalCreateDTO create(@RequestBody @Valid RentalCreateDTO rentalDTO) throws UnavaiableBookException {
        return rentalService.create(rentalDTO);
    }

    @GetMapping
    public List<RentalDTO> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @PutMapping
    public RentalUpdateDTO update(@RequestBody RentalUpdateDTO rentalDTO) {
        return rentalService.update(rentalDTO);
    }
}

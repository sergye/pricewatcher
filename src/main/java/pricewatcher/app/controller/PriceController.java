package pricewatcher.app.controller;

import java.util.List;

import pricewatcher.app.dto.price.PriceCreateDTO;
import pricewatcher.app.dto.price.PriceDTO;
import pricewatcher.app.dto.price.PriceFilterDTO;
import pricewatcher.app.dto.price.PriceUpdateDTO;
import pricewatcher.app.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping
    ResponseEntity<List<PriceDTO>> index(PriceFilterDTO filter) {
        List<PriceDTO> prices = priceService.getAll(filter);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(prices.size()))
                .body(prices);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceDTO show(@PathVariable Long id) {
        return priceService.findById(id);

    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PriceDTO create(@Valid @RequestBody PriceCreateDTO priceCreateDTO) {
        return priceService.create(priceCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceDTO update(@RequestBody @Valid PriceUpdateDTO priceUpdateDTO, @PathVariable Long id) {
        return priceService.update(priceUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        priceService.delete(id);
    }
}

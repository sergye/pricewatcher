package pricewatcher.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import pricewatcher.app.dto.pricedate.PriceDateCreateDTO;
import pricewatcher.app.dto.pricedate.PriceDateDTO;
import pricewatcher.app.dto.pricedate.PriceDateUpdateDTO;
import pricewatcher.app.service.PriceDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/dates")
public class PriceDateController {
    @Autowired
    private PriceDateService priceDateService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<PriceDateDTO>> index() {
        List<PriceDateDTO> priceDates = priceDateService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(priceDates.size()))
                .body(priceDates);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceDateDTO show(@PathVariable Long id) {
        return priceDateService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PriceDateDTO create(@Valid @RequestBody PriceDateCreateDTO priceDateCreateDTO) {
        return priceDateService.create(priceDateCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceDateDTO update(@RequestBody @Valid PriceDateUpdateDTO priceDateUpdateDTO, @PathVariable Long id) {
        return priceDateService.update(priceDateUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        priceDateService.delete(id);
    }
}

package pricewatcher.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import pricewatcher.app.dto.pricelist.PriceListCreateDTO;
import pricewatcher.app.dto.pricelist.PriceListDTO;
import pricewatcher.app.dto.pricelist.PriceListUpdateDTO;
import pricewatcher.app.service.PriceListService;
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
@RequestMapping("/api/pricelists")
public class PriceListController {
    @Autowired
    private PriceListService priceListService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<PriceListDTO>> index() {
        List<PriceListDTO> priceLists = priceListService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(priceLists.size()))
                .body(priceLists);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceListDTO show(@PathVariable Long id) {
        return priceListService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PriceListDTO create(@Valid @RequestBody PriceListCreateDTO priceListCreateDTO) {
        return priceListService.create(priceListCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceListDTO update(@RequestBody @Valid PriceListUpdateDTO priceListUpdateDTO, @PathVariable Long id) {
        return priceListService.update(priceListUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        priceListService.delete(id);
    }
}

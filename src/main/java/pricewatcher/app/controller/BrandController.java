package pricewatcher.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import pricewatcher.app.dto.brand.BrandCreateDTO;
import pricewatcher.app.dto.brand.BrandDTO;
import pricewatcher.app.dto.brand.BrandUpdateDTO;
import pricewatcher.app.service.BrandService;
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
@RequestMapping("/api/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<BrandDTO>> index() {
        List<BrandDTO> brands = brandService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(brands.size()))
                .body(brands);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BrandDTO show(@PathVariable Long id) {
        return brandService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BrandDTO create(@Valid @RequestBody BrandCreateDTO brandCreateDTO) {
        return brandService.create(brandCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BrandDTO update(@RequestBody @Valid BrandUpdateDTO brandUpdateDTO, @PathVariable Long id) {
        return brandService.update(brandUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        brandService.delete(id);
    }
}

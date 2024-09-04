package pricewatcher.app.controller;

import java.util.List;

import pricewatcher.app.dto.BrandCreateDTO;
import pricewatcher.app.dto.BrandDTO;
import pricewatcher.app.dto.BrandUpdateDTO;
import pricewatcher.app.mapper.BrandMapper;
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

import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.repository.BrandRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<BrandDTO> index() {
        var brands = brandRepository.findAll();

        return brands.stream()
                .map(brandMapper::map)
                .toList();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BrandDTO create(@Valid @RequestBody BrandCreateDTO brandData) {
        var brand = brandMapper.map(brandData);
        brandRepository.save(brand);
        return brandMapper.map(brand);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BrandDTO show(@PathVariable Long id) {
        var brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        return brandMapper.map(brand);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BrandDTO update(@RequestBody @Valid BrandUpdateDTO brandData, @PathVariable Long id) {
        var brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));

        brandMapper.update(brandData, brand);
        brandRepository.save(brand);

        return brandMapper.map(brand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        brandRepository.deleteById(id);
    }
}

package pricewatcher.app.service;

import pricewatcher.app.dto.BrandCreateDTO;
import pricewatcher.app.dto.BrandDTO;
import pricewatcher.app.dto.BrandUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.mapper.BrandMapper;
import pricewatcher.app.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository repository;

    @Autowired
    private BrandMapper brandMapper;

    public List<BrandDTO> getAll() {
        var brands = repository.findAll();
        return brands.stream()
                .map(brandMapper::map)
                .toList();
    }

    public BrandDTO create(BrandCreateDTO brandCreateDTO) {
        var brand = brandMapper.map(brandCreateDTO);
        repository.save(brand);
        return brandMapper.map(brand);
    }

    public BrandDTO findById(Long id) {
        var brand = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not Found: " + id));
        return brandMapper.map(brand);
    }

    public BrandDTO update(BrandUpdateDTO brandUpdateDTO, Long id) {
        var brand = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not Found: " + id));
        brandMapper.update(brandUpdateDTO, brand);
        repository.save(brand);
        return brandMapper.map(brand);
    }


    public void delete(Long id) {
        repository.deleteById(id);
    }
}

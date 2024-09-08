package pricewatcher.app.service;

import pricewatcher.app.dto.pricedate.PriceDateCreateDTO;
import pricewatcher.app.dto.pricedate.PriceDateDTO;
import pricewatcher.app.dto.pricedate.PriceDateUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.mapper.PriceDateMapper;
import pricewatcher.app.repository.PriceDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceDateService {
    @Autowired
    private PriceDateRepository repository;

    @Autowired
    private PriceDateMapper priceDateMapper;

    public List<PriceDateDTO> getAll() {
        var priceDates = repository.findAll();
        return priceDates.stream()
                .map(priceDateMapper::map)
                .toList();
    }

    public PriceDateDTO create(PriceDateCreateDTO priceDateCreateDTO) {
        var priceDate = priceDateMapper.map(priceDateCreateDTO);
        repository.save(priceDate);
        return priceDateMapper.map(priceDate);
    }

    public PriceDateDTO findById(Long id) {
        var priceDate = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PriceDate not found: " + id));
        return priceDateMapper.map(priceDate);
    }

    public PriceDateDTO update(PriceDateUpdateDTO priceDateUpdateDTO, Long id) {
        var priceDate = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PriceDate not found: " + id));
        priceDateMapper.update(priceDateUpdateDTO, priceDate);
        repository.save(priceDate);
        return priceDateMapper.map(priceDate);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

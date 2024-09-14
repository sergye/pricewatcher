package pricewatcher.app.service;

import pricewatcher.app.dto.pricelist.PriceListCreateDTO;
import pricewatcher.app.dto.pricelist.PriceListDTO;
import pricewatcher.app.dto.pricelist.PriceListUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.mapper.PriceListMapper;
import pricewatcher.app.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListService {
    @Autowired
    private PriceListRepository repository;

    @Autowired
    private PriceListMapper priceListMapper;

    public List<PriceListDTO> getAll() {
        var priceLists = repository.findAll();
        return priceLists.stream()
                .map(priceListMapper::map)
                .toList();
    }

    public PriceListDTO create(PriceListCreateDTO priceListCreateDTO) {
        var priceList = priceListMapper.map(priceListCreateDTO);
        repository.save(priceList);
        return priceListMapper.map(priceList);
    }

    public PriceListDTO findById(Long id) {
        var priceList = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PriceList not found: " + id));
        return priceListMapper.map(priceList);
    }

    public PriceListDTO update(PriceListUpdateDTO priceListUpdateDTO, Long id) {
        var priceList = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PriceList not found: " + id));
        priceListMapper.update(priceListUpdateDTO, priceList);
        repository.save(priceList);
        return priceListMapper.map(priceList);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

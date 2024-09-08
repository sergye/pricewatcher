package pricewatcher.app.service;

import pricewatcher.app.dto.pricedate.PriceDateCreateDTO;
import pricewatcher.app.dto.pricedate.PriceDateFormatDTO;
import pricewatcher.app.dto.pricedate.PriceDateDTO;
import pricewatcher.app.dto.pricedate.PriceDateUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.mapper.PriceDateMapper;
import pricewatcher.app.repository.PriceDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PriceDateService {
    @Autowired
    private PriceDateRepository repository;

    @Autowired
    private PriceDateMapper priceDateMapper;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<PriceDateDTO> getAll() {
        var priceDates = repository.findAll();
        return priceDates.stream()
                .map(priceDateMapper::map)
                .toList();
    }

    public PriceDateDTO create(PriceDateCreateDTO priceDateCreateDTO) {
        var formatedDate = LocalDate.parse(priceDateCreateDTO.getPriceDate(), FORMATTER);
        PriceDateFormatDTO priceDateFormatDTO = new PriceDateFormatDTO();
        priceDateFormatDTO.setPriceDate(formatedDate);
        var priceDate = priceDateMapper.map(priceDateFormatDTO);
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
        var formatedDate = LocalDate.parse(priceDateUpdateDTO.getPriceDate().get(), FORMATTER);
        PriceDateFormatDTO priceDateFormatDTO = new PriceDateFormatDTO();
        priceDateFormatDTO.setPriceDate(formatedDate);
        priceDateMapper.update(priceDateFormatDTO, priceDate);
        repository.save(priceDate);
        return priceDateMapper.map(priceDate);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

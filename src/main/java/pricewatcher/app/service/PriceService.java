package pricewatcher.app.service;

import pricewatcher.app.component.PriceSpecification;
import pricewatcher.app.dto.price.PriceCreateDTO;
import pricewatcher.app.dto.price.PriceDTO;
import pricewatcher.app.dto.price.PriceFilterDTO;
import pricewatcher.app.dto.price.PriceUpdateDTO;
import pricewatcher.app.exception.ResourceAlreadyExistsException;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.mapper.PriceMapper;
import pricewatcher.app.model.Product;
import pricewatcher.app.model.Price;
import pricewatcher.app.model.PriceList;
import pricewatcher.app.model.Brand;
import pricewatcher.app.repository.PriceListRepository;
import pricewatcher.app.repository.ProductRepository;
import pricewatcher.app.repository.PriceRepository;
import pricewatcher.app.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PriceMapper priceMapper;

    @Autowired
    private PriceSpecification priceSpecification;

    public List<PriceDTO> getAll(PriceFilterDTO filter) {
        Specification<Price> spec = priceSpecification.build(filter);

        return priceRepository.findAll(spec).stream()
                .map(priceMapper::map)
                .toList();
    }

    public PriceDTO create(PriceCreateDTO priceCreateDTO) {
        if (priceRepository.findByPriceList(priceMapper.map(priceCreateDTO)
                .getPriceList())
                .isPresent()) {
            throw new ResourceAlreadyExistsException("Price already exists");
        }

        var price = priceMapper.map(priceCreateDTO);
        setPriceData(price, priceCreateDTO);
        priceRepository.save(price);
        return priceMapper.map(price);
    }

    public PriceDTO findById(Long id) {
        var price = priceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Price not Found: " + id));
        return priceMapper.map(price);
    }

    public PriceDTO update(PriceUpdateDTO priceUpdateDTO, Long id) {
        var price = priceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Price not Found: " + id));

        setPriceData(price, priceUpdateDTO);
        priceMapper.update(priceUpdateDTO, price);
        priceRepository.save(price);
        return priceMapper.map(price);
    }

    public void delete(Long id) {
        priceRepository.deleteById(id);
    }


    private void setPriceData(Price price, PriceUpdateDTO priceUpdateDTO) {
        PriceCreateDTO priceCreateDTO = new PriceCreateDTO();
        priceCreateDTO.setProduct(priceUpdateDTO.getProduct().get());
        priceCreateDTO.setBrand(priceUpdateDTO.getBrand().get());
        priceCreateDTO.setPriceList(priceUpdateDTO.getPriceList().get());
        setPriceData(price, priceCreateDTO);
    }

    private void setPriceData(Price price, PriceCreateDTO priceCreateDTO) {

        if (priceCreateDTO.getProduct() != null) {
            Product newProduct = null;
            if (price.getProduct() != null) {
                newProduct = productRepository.findByName(priceCreateDTO.getProduct())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            }
            price.setProduct(newProduct);
        }

        if (priceCreateDTO.getBrand() != null) {
            Brand newBrand = null;
            if (price.getBrand() != null) {
                newBrand = brandRepository.findByName(priceCreateDTO.getBrand())
                        .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
            }
            price.setBrand(newBrand);
        }

        if (priceCreateDTO.getPriceList() != null) {
            PriceList newPriceList = null;
            if (price.getPriceList() != null) {
                newPriceList = priceListRepository.findByName(priceCreateDTO.getPriceList())
                        .orElseThrow(() -> new ResourceNotFoundException("PriceList not found"));
            }
            price.setPriceList(newPriceList);
        }
    }
}

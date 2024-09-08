package pricewatcher.app.service;

import pricewatcher.app.dto.product.ProductCreateDTO;
import pricewatcher.app.dto.product.ProductDTO;
import pricewatcher.app.dto.product.ProductUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.mapper.ProductMapper;
import pricewatcher.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> getAll() {
        var products = repository.findAll();
        return products.stream()
                .map(productMapper::map)
                .toList();
    }

    public ProductDTO create(ProductCreateDTO productCreateDTO) {
        var product = productMapper.map(productCreateDTO);
        repository.save(product);
        return productMapper.map(product);
    }

    public ProductDTO findById(Long id) {
        var product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return productMapper.map(product);
    }

    public ProductDTO update(ProductUpdateDTO productUpdateDTO, Long id) {
        var product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        productMapper.update(productUpdateDTO, product);
        repository.save(product);
        return productMapper.map(product);
    }


    public void delete(Long id) {
        repository.deleteById(id);
    }
}

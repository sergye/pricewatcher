package pricewatcher.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import pricewatcher.app.dto.product.ProductCreateDTO;
import pricewatcher.app.dto.product.ProductDTO;
import pricewatcher.app.dto.product.ProductUpdateDTO;
import pricewatcher.app.service.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<ProductDTO>> index() {
        List<ProductDTO> products = productService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(products.size()))
                .body(products);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO show(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        return productService.create(productCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO update(@RequestBody @Valid ProductUpdateDTO productUpdateDTO, @PathVariable Long id) {
        return productService.update(productUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}

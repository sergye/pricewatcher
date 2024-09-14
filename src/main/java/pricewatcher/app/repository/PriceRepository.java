package pricewatcher.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pricewatcher.app.model.Brand;
import pricewatcher.app.model.Price;
import pricewatcher.app.model.PriceList;
import pricewatcher.app.model.Product;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findByBrand(Brand brand);
    Optional<Price> findByProduct(Product product);
    Optional<Price> findByPriceList(PriceList priceList);
}

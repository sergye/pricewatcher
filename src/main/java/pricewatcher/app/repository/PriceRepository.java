package pricewatcher.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import pricewatcher.app.model.Price;
import pricewatcher.app.model.PriceList;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long>, JpaSpecificationExecutor<Price> {
    Optional<Price> findByPriceList(PriceList priceList);
}

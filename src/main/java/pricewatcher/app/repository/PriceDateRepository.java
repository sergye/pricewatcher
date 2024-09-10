package pricewatcher.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pricewatcher.app.model.PriceDate;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceDateRepository extends JpaRepository<PriceDate, Long> {
    Optional<PriceDate> findByPriceDate(LocalDateTime priceDate);
}

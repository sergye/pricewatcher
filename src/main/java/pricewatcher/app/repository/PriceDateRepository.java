package pricewatcher.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pricewatcher.app.model.PriceDate;

@Repository
public interface PriceDateRepository extends JpaRepository<PriceDate, Long> {
}

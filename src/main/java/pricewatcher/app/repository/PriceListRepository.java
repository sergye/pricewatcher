package pricewatcher.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pricewatcher.app.model.PriceList;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    Optional<PriceList> findByName(String name);
}

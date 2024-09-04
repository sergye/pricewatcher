package pricewatcher.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pricewatcher.app.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	Optional<Brand> findByEmail(String email);
}

package pricewatcher.app.component;

import org.springframework.data.jpa.domain.Specification;
import pricewatcher.app.dto.price.PriceFilterDTO;
import pricewatcher.app.model.Price;

import java.time.LocalDateTime;

public class PriceSpecification {
    public Specification<Price> build(PriceFilterDTO params) {
        return withProduct(params.getProduct())
                .and(withBrand(params.getBrand()))
                .and(withPriceList(params.getPriceList()))
                .and(withPriceDate(params.getPriceDate()));

    }

    private Specification<Price> withProduct(String product) {
        return (root, query, cb) -> product == null
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("product")), "%" + product + "%");

    }

    private Specification<Price> withBrand(String brand) {
        return (root, query, cb) -> brand == null
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("brand")), "%" + brand + "%");
    }

    private Specification<Price> withPriceList(String priceList) {
        return (root, query, cb) -> priceList == null
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("priceList")), "%" + priceList + "%");
    }

    private Specification<Price> withPriceDate(LocalDateTime priceDate) {
        return (root, query, cb) -> priceDate == null
                ? cb.conjunction()
                : cb.between(cb.literal(priceDate), root.get("startDate"), root.get("endDate"));
    }
}

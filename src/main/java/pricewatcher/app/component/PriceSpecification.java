package pricewatcher.app.component;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pricewatcher.app.dto.price.PriceFilterDTO;
import pricewatcher.app.model.Price;

import java.time.LocalDateTime;

import static pricewatcher.app.service.PriceDateService.FORMATTER;

@Component
public class PriceSpecification {
    private LocalDateTime filterDate;

    public Specification<Price> build(PriceFilterDTO params) {
        return withProduct(params.getProduct())
                .and(withBrand(params.getBrand()))
                .and(withPriceList(params.getPriceList()))
                .and(withPriceDate(params.getPriceDate()));

    }

    private Specification<Price> withProduct(String product) {
        return (root, query, cb) -> product == null
                ? cb.conjunction()
                : cb.equal(root.get("product").get("name"), product);

    }

    private Specification<Price> withBrand(String brand) {
        return (root, query, cb) -> brand == null
                ? cb.conjunction()
                : cb.equal(root.get("brand").get("name"), brand);
    }

    private Specification<Price> withPriceList(String priceList) {
        return (root, query, cb) -> priceList == null
                ? cb.conjunction()
                : cb.equal(root.get("priceList").get("name"), priceList);
    }

    private Specification<Price> withPriceDate(String priceDate) {
        filterDate = priceDate == null ? null : LocalDateTime.parse(priceDate, FORMATTER);
        return (root, query, cb) -> filterDate == null
                ? cb.conjunction()
                : cb.between(cb.literal(filterDate), root.get("startDate"), root.get("endDate"));
    }
}

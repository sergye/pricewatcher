package pricewatcher.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import static jakarta.persistence.GenerationType.IDENTITY;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;


@Entity
@Table(name = "prices")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "brand_id")
    @ManyToOne
    private Brand brand;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @JoinColumn(name = "price_list_id")
    @ManyToOne
    private PriceList priceList;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    private int priority;

    private BigDecimal price;

    private Currency curr;

    @CreatedDate
    private LocalDateTime createdAt;
}

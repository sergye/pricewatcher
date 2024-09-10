package pricewatcher.app.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "price_dates")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PriceDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @PastOrPresent
    private LocalDateTime priceDate;

    @CreatedDate
    private LocalDateTime createdAt;
}

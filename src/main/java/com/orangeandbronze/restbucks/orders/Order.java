package com.orangeandbronze.restbucks.orders;

import com.orangeandbronze.restbucks.drinks.Drink;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "DRINK_ORDER")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private Status status;
    @ManyToOne
    private Drink drink;
    private Integer quantity;
    private BigDecimal total;
    private LocalDateTime orderedDate;

    public Order(Status status, Drink drink, Integer quantity, BigDecimal total, LocalDateTime orderedDate) {
        this.status = status;
        this.drink = drink;
        this.quantity = quantity;
        this.total = total;
        this.orderedDate = orderedDate;
    }
}

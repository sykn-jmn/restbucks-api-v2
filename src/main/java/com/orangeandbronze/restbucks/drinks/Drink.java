package com.orangeandbronze.restbucks.drinks;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Drink {
    private @Id
    @GeneratedValue Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    public Drink(String title, String description, BigDecimal price, String image) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}

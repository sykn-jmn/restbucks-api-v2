package com.orangeandbronze.restbucks.favorite;

import com.orangeandbronze.restbucks.drinks.Drink;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Favorite {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String drink;
    private Long drinkId;

    Favorite () {}

    public Favorite(Drink drink) {
        this.drink = drink.getTitle();
        this.drinkId = drink.getId();
    }

    public Long getId() {
        return id;
    }
    public String getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink.getTitle();
    }

    public Long getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(Long drinkId) {
        this.drinkId = drinkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(id, favorite.id) && Objects.equals(drink, favorite.drink) && Objects.equals(drinkId, favorite.drinkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drink, drinkId);
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", drink=" + drink +
                ", drinkId=" + drinkId +
                '}';
    }
}

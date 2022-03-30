package com.orangeandbronze.restbucks.favorite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.orangeandbronze.restbucks.drinks.Drink;
import com.orangeandbronze.restbucks.profile.Profile;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Favorite {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private String drink;
    private Long drinkId;

    Favorite () {}

    public Favorite(Profile profile, Drink drink) {
        this.profile = profile;
        this.drink = drink.getTitle();
        this.drinkId = drink.getId();
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Profile getProfile(){
        return profile;
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

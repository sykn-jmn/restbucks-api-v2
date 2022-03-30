package com.orangeandbronze.restbucks.favorite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.orangeandbronze.restbucks.drinks.Drink;
import com.orangeandbronze.restbucks.profile.Profile;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Favorite {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private String drink;
    private Long drinkId;

    public Favorite(Profile profile, Drink drink) {
        this.profile = profile;
        this.drink = drink.getTitle();
        this.drinkId = drink.getId();
    }
}

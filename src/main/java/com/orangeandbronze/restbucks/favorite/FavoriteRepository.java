package com.orangeandbronze.restbucks.favorite;

import com.orangeandbronze.restbucks.drinks.Drink;
import com.orangeandbronze.restbucks.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    public List<Favorite> findByDrinkId(Long id);
    public List<Favorite> findByProfileAndDrink(Profile profile, Drink drink);
    public void deleteByDrinkAndProfile(Drink drink,Profile profile);
}
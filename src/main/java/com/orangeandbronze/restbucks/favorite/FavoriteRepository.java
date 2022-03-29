package com.orangeandbronze.restbucks.favorite;

import com.orangeandbronze.restbucks.drinks.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
package com.orangeandbronze.restbucks.profile;

import com.orangeandbronze.restbucks.favorite.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Optional<Profile> findByUsername(String username);
    List<Favorite> findFavoritesById(Long id);
}

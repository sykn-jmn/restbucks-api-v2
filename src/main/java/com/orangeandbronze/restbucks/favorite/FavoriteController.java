package com.orangeandbronze.restbucks.favorite;

import com.orangeandbronze.restbucks.drinks.Drink;
import com.orangeandbronze.restbucks.drinks.DrinkRepository;
import com.orangeandbronze.restbucks.profile.Profile;
import com.orangeandbronze.restbucks.profile.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/profiles/favorites")
public class FavoriteController {

    private final ProfileRepository profileRepository;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteModelAssembler assembler;
    private final DrinkRepository drinkRepository;

    @GetMapping
    public CollectionModel<EntityModel<Favorite>> all(@AuthenticationPrincipal Profile profile) {
        List<Favorite> favorites = favoriteRepository.findByProfile(profile);
        List<EntityModel<Favorite>> favoritesModel = favorites.stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(favoritesModel,
                linkTo(methodOn(FavoriteController.class).all(profile)).withSelfRel());
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> post(@PathVariable long id, @AuthenticationPrincipal Profile profile){
        Favorite favorite = new Favorite(profile, drinkRepository.getById(id));
        if(!favoriteRepository.findByProfileAndDrink(profile, drinkRepository.getById(id)).isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        favoriteRepository.save(favorite);
        EntityModel<Favorite> entityModel = assembler.toModel(favorite);
        return ResponseEntity.created(linkTo(methodOn(FavoriteController.class).get(id,profile)).toUri()).body(entityModel);
    }

    @GetMapping("/id")
    public ResponseEntity<Favorite> get(@PathVariable long id, @AuthenticationPrincipal Profile profile){
        Favorite favorite = favoriteRepository.getById(id);
        return new ResponseEntity<Favorite>(favorite, HttpStatus.OK);
    }


    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Profile profile){
        Drink drink = drinkRepository.findById(id).get();
        favoriteRepository.deleteByDrinkAndProfile(drink, profile);
        return ResponseEntity.ok().build();
    }


}



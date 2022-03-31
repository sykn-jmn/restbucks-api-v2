package com.orangeandbronze.restbucks.favorite;

import com.orangeandbronze.restbucks.drinks.Drink;
import com.orangeandbronze.restbucks.drinks.DrinkRepository;
import com.orangeandbronze.restbucks.profile.Profile;
import com.orangeandbronze.restbucks.profile.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
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
        final List<EntityModel<Favorite>> collection = getFavoritesForPerson(profile.getId());

        return CollectionModel.of(collection,
                linkTo(methodOn(FavoriteController.class).all(profile)).withSelfRel());
    }

    private List<EntityModel<Favorite>> getFavoritesForPerson(long profileId) {
        List<EntityModel<Favorite>> favorites = profileRepository.findById(profileId)
                .map(profile -> profile.getFavorites()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList()))
                .orElseThrow(RuntimeException::new);
        return favorites;
    }

    @GetMapping("/{id}")
    public EntityModel<Favorite> one(@PathVariable long profileId, @PathVariable Long id) {
        List<Favorite> favorites = new ArrayList<>();
        profileRepository.findById(profileId)
                .map(profile -> profile
                        .getFavorites()
                        .stream()
                        .filter(fave -> fave.getId()
                                .equals(id)).findAny().map(favorites::add));
        if (!favorites.isEmpty()) {
            Favorite favorite = favorites.get(0);
            return assembler.toModel(favorite);
        }

        return null;
    }

    //    @PostMapping
//    public ResponseEntity<?> post(@PathVariable long profileId, @RequestBody Drink drink){
//        List<Favorite> favorites = new ArrayList<>();
//        System.out.println(drink);
//        Favorite favorite = null;
//        profileRepository.findById(profileId)
//                .map(profile -> profile
//                        .getFavorites()
//                        .stream()
//                        .filter(fave -> fave.getDrinkId()
//                                .equals(drink.getId())).findAny().map(favorites::add));
//        if (favorites.isEmpty()) {
//            favorite = new Favorite(profileRepository.getById(profileId), drink);
//            favoriteRepository.save(favorite);
//        }
//
//    }
    @PostMapping("/{id}")
    public ResponseEntity<?> post(@PathVariable long id, @AuthenticationPrincipal Profile profile){
        Favorite favorite = new Favorite(profile, drinkRepository.getById(id));
        if(favoriteRepository.findByProfileAndDrink(profile, drinkRepository.getById(id)).isEmpty()){
            favoriteRepository.save(favorite);
            EntityModel<Favorite> entityModel = assembler.toModel(favorite);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        }

        return ResponseEntity.internalServerError().build();
    }


    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Profile profile){
        if(profile != null){
            Drink drink = drinkRepository.findById(id).get();
            favoriteRepository.deleteByDrinkAndProfile(drink, profile);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }


}
//    @PostMapping
//    public ResponseEntity<EntityModel<Favorite>> post(@PathVariable long profileId, @RequestBody Drink drink){
//         ResponseEntity<EntityModel<Favorite>> favorite = profileRepository
//                                                .findById(profileId)
//                                                .map(profile -> profile.getFavorites().stream().filter().)
//
////        Favorite newFavorite = new Favorite(drink);
////        favoriteRepository.save(newFavorite);
////        return ResponseEntity
////                .created(linkTo(methodOn(FavoriteController.class).one(newFavorite.getId())).toUri())
////                .body(assembler.toModel(newFavorite));
//    }



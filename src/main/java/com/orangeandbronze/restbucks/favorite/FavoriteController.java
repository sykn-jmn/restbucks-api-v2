package com.orangeandbronze.restbucks.favorite;

import com.orangeandbronze.restbucks.drinks.Drink;
import com.orangeandbronze.restbucks.profile.ProfileRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/profiles/{profileId}/favorites")
public class FavoriteController {

    private final ProfileRepository profileRepository;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteModelAssembler assembler;

    public FavoriteController(ProfileRepository profileRepository, FavoriteRepository favoriteRepository, FavoriteModelAssembler assembler) {
        this.profileRepository = profileRepository;
        this.favoriteRepository = favoriteRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Favorite>> all(@PathVariable Long profileId) {
        final List<EntityModel<Favorite>> collection = getFavoritesForPerson(profileId);

        return CollectionModel.of(collection,
                linkTo(methodOn(FavoriteController.class).all(profileId)).withSelfRel());
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
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Drink drink, @PathVariable long profileId){

        Favorite favorite = new Favorite(profileRepository.getById(profileId), drink);
        if(favoriteRepository.findByDrinkId(drink.getId()).isEmpty()){
            favoriteRepository.save(favorite);
            EntityModel<Favorite> entityModel = assembler.toModel(favorite);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        }

        return ResponseEntity.internalServerError().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        favoriteRepository.deleteByDrinkId(id);
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





package com.orangeandbronze.restbucks.drinks;

import com.orangeandbronze.restbucks.RestbucksController;
import com.orangeandbronze.restbucks.favorite.Favorite;
import com.orangeandbronze.restbucks.favorite.FavoriteController;
import com.orangeandbronze.restbucks.favorite.FavoriteRepository;
import com.orangeandbronze.restbucks.orders.OrderController;
import com.orangeandbronze.restbucks.profile.Profile;
import com.orangeandbronze.restbucks.profile.ProfileController;
import com.orangeandbronze.restbucks.profile.ProfileRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DrinkController {

    private final DrinkRepository drinkRepository;
    private final DrinkModelAssembler assembler;
    private final ProfileRepository profileRepository;
    private final FavoriteRepository favoriteRepository;

    public DrinkController(DrinkRepository drinkRepository, DrinkModelAssembler assembler, ProfileRepository profileRepository, FavoriteRepository favoriteRepository){
        this.drinkRepository = drinkRepository;
        this.profileRepository = profileRepository;
        this.favoriteRepository = favoriteRepository;
        this.assembler = assembler;
    }


    @GetMapping("/drinks")
    public CollectionModel<EntityModel<Drink>> all(@AuthenticationPrincipal Profile profile){
        List<EntityModel<Drink>> drinks = drinkRepository.
                findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());



        if(profile != null){
//            List<EntityModel<Favorite>> favorites = getFavoritesForPerson

            drinks.forEach(drinkEntityModel -> {
                drinkEntityModel.add(linkTo(methodOn(OrderController.class).newOrder(null)).withRel("restbucks:newOrder"));
                if(favoriteRepository.findByProfileAndDrink(profile, drinkEntityModel.getContent()).isEmpty()){
                    drinkEntityModel.add(linkTo(methodOn(FavoriteController.class).post(drinkEntityModel.getContent().getId(),profile)).withRel("restbucks:addToFavorites"));
                }else {
                    drinkEntityModel.add(linkTo(methodOn(FavoriteController.class).delete(drinkEntityModel.getContent().getId(), profile)).withRel("restbucks:removeFavorites"));
                }

            });
        }
        CollectionModel<EntityModel<Drink>> collectionModel =  CollectionModel.of(drinks,
                linkTo(methodOn(DrinkController.class).all(null)).withSelfRel());
        if(profile == null){
            collectionModel.add(linkTo(methodOn(RestbucksController.class).login()).withRel("restbucks:login"));
        }else{
            collectionModel.add(linkTo(methodOn(ProfileController.class).one(profile)).withRel("restbucks:profile"));
        }
        return collectionModel;
    }


//    private List<EntityModel<Favorite>> getFavoritesForPerson(long profileId) {
//        List<EntityModel<Favorite>> favorites = profileRepository.findById(profileId)
//                .map(profile -> profile.getFavorites()
//                        .stream()
//                        .map(assembler::toModel)
//                        .collect(Collectors.toList()))
//                .orElseThrow(RuntimeException::new);
//        return favorites;
//    }

    @PostMapping("/drinks")
    public ResponseEntity<?> newDrink(@RequestBody Drink newDrink){
        EntityModel<Drink> entityModel = assembler.toModel(drinkRepository.save(newDrink));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    @GetMapping("/drinks/{id}")
    public EntityModel<Drink> one(@PathVariable Long id){
        Drink drink =  drinkRepository.findById(id).orElseThrow(() -> new DrinkNotFoundException(id));

        return assembler.toModel(drink);

    }
    @PutMapping("/drinks/{id}")
    public ResponseEntity<?> replaceDrink(@RequestBody Drink newDrink, @PathVariable Long id){
        Drink updatedDrink = drinkRepository.findById(id).map(drink -> {
            drink.setTitle(newDrink.getTitle());
            drink.setDescription(newDrink.getDescription());
            drink.setPrice(newDrink.getPrice());
            drink.setImage(newDrink.getImage());
            return drinkRepository.save(drink);
        }).orElseGet(() -> {
            newDrink.setId(id);
            return drinkRepository.save(newDrink);
        });
        EntityModel<Drink> entityModel = assembler.toModel(updatedDrink);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    @DeleteMapping("/drinks/{id}")
    public ResponseEntity<?> deleteDrink(@PathVariable Long id){
        drinkRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

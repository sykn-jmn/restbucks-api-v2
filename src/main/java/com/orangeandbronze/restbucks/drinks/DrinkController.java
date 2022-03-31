package com.orangeandbronze.restbucks.drinks;

import com.orangeandbronze.restbucks.RestbucksController;
import com.orangeandbronze.restbucks.favorite.Favorite;
import com.orangeandbronze.restbucks.favorite.FavoriteController;
import com.orangeandbronze.restbucks.favorite.FavoriteRepository;
import com.orangeandbronze.restbucks.orders.OrderController;
import com.orangeandbronze.restbucks.profile.Profile;
import com.orangeandbronze.restbucks.profile.ProfileController;
import com.orangeandbronze.restbucks.profile.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
public class DrinkController {

    @Autowired
    private final DrinkRepository drinkRepository;
    @Autowired
    private final DrinkModelAssembler assembler;
    @Autowired
    private final FavoriteRepository favoriteRepository;

    @GetMapping("/drinks")
    public CollectionModel<EntityModel<Drink>> all(@AuthenticationPrincipal Profile profile){
        List<EntityModel<Drink>> drinks = drinkRepository.
                findAll()
                .stream()
                .map(drink->assembler.toModel(drink,profile))
                .collect(Collectors.toList());
        return assembler.collectionToModel(drinks, profile);
    }

    @PostMapping("/drinks")
    public ResponseEntity<?> newDrink(@RequestBody Drink newDrink){
        EntityModel<Drink> entityModel = assembler.toModel(drinkRepository.save(newDrink));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/drinks/{id}")
    public EntityModel<Drink> one(@PathVariable Long id, @AuthenticationPrincipal Profile profile){
        Drink drink =  drinkRepository.findById(id).orElseThrow(() -> new DrinkNotFoundException(id));
        EntityModel<Drink> model = assembler.toModel(drink,profile);
        return model;
    }

    @PutMapping("/drinks/{id}")
    public ResponseEntity<?> replaceDrink(@RequestBody Drink newDrink, @PathVariable Long id){
        newDrink.setId(id);
        Drink updatedDrink = drinkRepository.save(newDrink);
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

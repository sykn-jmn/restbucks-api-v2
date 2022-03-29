package com.orangeandbronze.restbucks.favorite;

import com.orangeandbronze.restbucks.drinks.Drink;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteModelAssembler assembler;

    public FavoriteController(FavoriteRepository favoriteRepository, FavoriteModelAssembler assembler) {
        this.favoriteRepository = favoriteRepository;
        this.assembler = assembler;
    }
    @GetMapping("/favorites")
    CollectionModel<EntityModel<Favorite>> all(){
        List<EntityModel<Favorite>> faves = favoriteRepository
                .findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(faves,
                linkTo(methodOn(FavoriteController.class).all()).withSelfRel());
    }
//    @GetMapping("/favorites")
//    List<Favorite> all(){
//        return  favoriteRepository.findAll();
//    }
    @GetMapping("/favorites/{id}")
    EntityModel<Favorite> one(@PathVariable Long id) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return assembler.toModel(favorite);
    }

    @PostMapping("/favorites")
    ResponseEntity<EntityModel<Favorite>> newFavorite(@RequestBody Drink drink){
        Favorite newFavorite = new Favorite(drink);
        favoriteRepository.save(newFavorite);
        return ResponseEntity
                .created(linkTo(methodOn(FavoriteController.class).one(newFavorite.getId())).toUri())
                .body(assembler.toModel(newFavorite));
    }


}

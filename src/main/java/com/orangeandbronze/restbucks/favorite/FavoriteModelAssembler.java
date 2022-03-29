package com.orangeandbronze.restbucks.favorite;

import com.orangeandbronze.restbucks.drinks.DrinkController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class FavoriteModelAssembler implements RepresentationModelAssembler<Favorite, EntityModel<Favorite>> {
    @Override
    public EntityModel<Favorite> toModel(Favorite favorite){
        EntityModel<Favorite> fave =  EntityModel.of(favorite,
                linkTo(methodOn(FavoriteController.class).one(favorite.getId())).withSelfRel(),
                linkTo(methodOn(FavoriteController.class).all()).withRel("favorites"));

        fave.add(linkTo(methodOn(DrinkController.class).one(favorite.getDrinkId())).withRel("restbucks:drink_id"));
        return fave;
    }

}

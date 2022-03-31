package com.orangeandbronze.restbucks.favorite;

import com.orangeandbronze.restbucks.drinks.DrinkController;
import com.orangeandbronze.restbucks.profile.ProfileController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class FavoriteModelAssembler implements RepresentationModelAssembler<Favorite, EntityModel<Favorite>> {

    @Override
    public EntityModel<Favorite> toModel(Favorite favorite){
        final long profileId = favorite.getProfile().getId();

        EntityModel<Favorite> fave =  EntityModel.of(favorite,
                linkTo(methodOn(FavoriteController.class).one(profileId, favorite.getId())).withSelfRel().withType("GET"),
                linkTo(methodOn(FavoriteController.class).all(null)).withRel("restbucks:favorites").withType("GET"),
                linkTo(methodOn(DrinkController.class).one(favorite.getDrink().getId())).withRel("restbucks:drink_id").withType("GET"),
                linkTo(methodOn(ProfileController.class).one(null)).withRel("profile").withType("GET"));

        return fave;
    }

}

package com.orangeandbronze.restbucks.drinks;

import com.orangeandbronze.restbucks.favorite.FavoriteController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class DrinkModelAssembler implements RepresentationModelAssembler<Drink, EntityModel<Drink>> {
    @Override
    public EntityModel<Drink> toModel(Drink drink){
        EntityModel<Drink> drinkModel = EntityModel.of(drink,
                linkTo(methodOn(DrinkController.class).one(drink.getId(), null)).withSelfRel().withType("GET"),
                linkTo(methodOn(DrinkController.class).all(null)).withRel("restbucks:drinks").withType("GET"));

//        if(!drink.getFavoriteFlag()){
//            drinkModel.add(linkTo(methodOn(FavoriteController.class).post(drink)).withRel("restbucks:add_favorite").withType("PUT"));
//        } else {
//            drinkModel.add(linkTo(methodOn(FavoriteController.class).delete(drink.getId())).withRel("restbucks:remove_favorite").withType("DELETE"));
//        }
        return drinkModel;
    }

}

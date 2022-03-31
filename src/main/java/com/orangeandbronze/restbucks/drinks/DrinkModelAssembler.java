package com.orangeandbronze.restbucks.drinks;

import com.orangeandbronze.restbucks.RestbucksController;
import com.orangeandbronze.restbucks.favorite.FavoriteController;
import com.orangeandbronze.restbucks.favorite.FavoriteRepository;
import com.orangeandbronze.restbucks.orders.OrderController;
import com.orangeandbronze.restbucks.profile.Profile;
import com.orangeandbronze.restbucks.profile.ProfileController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
class DrinkModelAssembler implements RepresentationModelAssembler<Drink, EntityModel<Drink>> {
    @Autowired
    FavoriteRepository favoriteRepository;

    @Override
    public EntityModel<Drink> toModel(Drink drink){
        EntityModel<Drink> drinkModel = EntityModel.of(drink,
            linkTo(methodOn(DrinkController.class).one(drink.getId(), null)).withSelfRel().withType("GET"),
            linkTo(methodOn(DrinkController.class).all(null)).withRel("restbucks:drinks").withType("GET"));
        return drinkModel;
    }

    public EntityModel<Drink> toModel(Drink drink, Profile profile){
        EntityModel<Drink> drinkModel = toModel(drink);
        if(profile!=null){
            drinkModel.add(linkTo(methodOn(OrderController.class).newOrder(null,null)).withRel("restbucks:newOrder"));
            if(favoriteRepository.findByProfileAndDrink(profile,drink).isEmpty()){
                drinkModel.add(linkTo(methodOn(FavoriteController.class).post(drink.getId(),profile)).withRel("restbucks:addToFavorites"));
            }else {
                drinkModel.add(linkTo(methodOn(FavoriteController.class).delete(drink.getId(),profile)).withRel("restbucks:removeFavorites"));
            }
        }
        return drinkModel;
    }

    public CollectionModel<EntityModel<Drink>> collectionToModel(List<EntityModel<Drink>> drinksModel, Profile profile){
        CollectionModel<EntityModel<Drink>> collectionModel =  CollectionModel.of(drinksModel,
                linkTo(methodOn(DrinkController.class).all(null)).withSelfRel());
        if(profile == null){
            collectionModel.add(linkTo(methodOn(RestbucksController.class).login()).withRel("restbucks:login"));
        }else{
            collectionModel.add(linkTo(methodOn(ProfileController.class).one(profile)).withRel("restbucks:profile"));
        }
        return collectionModel;
    }

}

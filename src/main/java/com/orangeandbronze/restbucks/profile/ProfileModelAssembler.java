package com.orangeandbronze.restbucks.profile;

import com.orangeandbronze.restbucks.favorite.Favorite;
import com.orangeandbronze.restbucks.favorite.FavoriteController;
import com.orangeandbronze.restbucks.orders.OrderController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class ProfileModelAssembler implements RepresentationModelAssembler<Profile, EntityModel<Profile>> {
    @Override
    public EntityModel<Profile> toModel(Profile profile){
        EntityModel<Profile> profileModel = EntityModel.of(profile,
                linkTo(methodOn(ProfileController.class).one(profile)).withSelfRel().withType("GET"));
//                linkTo(methodOn(ProfileController.class).all()).withRel("restbucks:profiles").withType("GET"));
//        if(!profile.getFavorites().isEmpty()){
//
//        }
       return profileModel;
    }

}

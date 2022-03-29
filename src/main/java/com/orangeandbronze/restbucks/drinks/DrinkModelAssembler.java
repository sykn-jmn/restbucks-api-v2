package com.orangeandbronze.restbucks.drinks;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class DrinkModelAssembler implements RepresentationModelAssembler<Drink, EntityModel<Drink>> {
    @Override
    public EntityModel<Drink> toModel(Drink drink){
        return EntityModel.of(drink,
                linkTo(methodOn(DrinkController.class).one(drink.getId())).withSelfRel(),
                linkTo(methodOn(DrinkController.class).all()).withRel("drinks"));
    }

}

package com.orangeandbronze.restbucks;

import com.orangeandbronze.restbucks.drinks.DrinkController;
import com.orangeandbronze.restbucks.favorite.FavoriteController;
import com.orangeandbronze.restbucks.orders.OrderController;
import com.orangeandbronze.restbucks.profile.Profile;
import com.orangeandbronze.restbucks.profile.ProfileController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class RestbucksController {

    @GetMapping
    public RepresentationModel root(@AuthenticationPrincipal Profile profile){
        RepresentationModel model = new RepresentationModel();
        model.add(linkTo(methodOn(DrinkController.class).all(null)).withRel("restbucks:drinks"),
                linkTo(methodOn(OrderController.class).all()).withRel("restbucks:orders"),
                linkTo(methodOn(RestbucksController.class).login()).withRel("restbucks:login"));
        if(profile != null){
            model.add(linkTo(methodOn(ProfileController.class).one(profile)).withRel("restbucks:profile"));
        }
        return model;
    }

    @GetMapping("login")
    public ResponseEntity<Void> login(){
        return ResponseEntity.status(200).build();
    }

}

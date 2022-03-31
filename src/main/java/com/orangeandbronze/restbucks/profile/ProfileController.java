package com.orangeandbronze.restbucks.profile;

import com.orangeandbronze.restbucks.favorite.FavoriteController;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/profiles")
@AllArgsConstructor
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final ProfileModelAssembler assembler;

    @GetMapping("")
    public EntityModel<Profile> one(@AuthenticationPrincipal Profile profile){
        EntityModel<Profile> model = assembler.toModel(profile);
        if(!profileRepository.findById(profile.getId()).get().getFavorites().isEmpty()){
            model.add(linkTo(methodOn(FavoriteController.class).all(profile)).withRel("restbucks:favorites").withType("GET"));
        }
        return model;
    }
}
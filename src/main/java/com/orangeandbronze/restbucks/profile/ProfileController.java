package com.orangeandbronze.restbucks.profile;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final ProfileModelAssembler assembler;
    public ProfileController(ProfileRepository profileRepository, ProfileModelAssembler assembler){
        this.profileRepository = profileRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Profile>> all(){
        List<EntityModel<Profile>> profiles = profileRepository.
                findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(profiles,
                linkTo(methodOn(ProfileController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Profile> one(@PathVariable Long id){
        Profile profile = profileRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException());

        return assembler.toModel(profile);

    }

}

/*

    @PostMapping("/drinks")
    public ResponseEntity<?> newDrink(@RequestBody Drink newDrink){
        EntityModel<Drink> entityModel = assembler.toModel(drinkRepository.save(newDrink));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    @GetMapping("/drinks/{id}")
    public EntityModel<Drink> one(@PathVariable Long id){
        Drink drink =  drinkRepository.findById(id).orElseThrow(() -> new DrinkNotFoundException(id));

        return assembler.toModel(drink);

    }
    @PutMapping("/drinks/{id}")
    public ResponseEntity<?> replaceDrink(@RequestBody Drink newDrink, @PathVariable Long id){
        Drink updatedDrink = drinkRepository.findById(id).map(drink -> {
            drink.setTitle(newDrink.getTitle());
            drink.setDescription(newDrink.getDescription());
            drink.setPrice(newDrink.getPrice());
            drink.setImage(newDrink.getImage());
            return drinkRepository.save(drink);
        }).orElseGet(() -> {
            newDrink.setId(id);
            return drinkRepository.save(newDrink);
        });
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

 */
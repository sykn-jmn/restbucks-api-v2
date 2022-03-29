package com.orangeandbronze.restbucks.drinks;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class DrinkController {
    private final DrinkRepository drinkRepository;
    private final DrinkModelAssembler assembler;
    DrinkController(DrinkRepository drinkRepository, DrinkModelAssembler assembler){
        this.drinkRepository = drinkRepository;
        this.assembler = assembler;
    }

    @GetMapping("/drinks")
    CollectionModel<EntityModel<Drink>> all(){
        List<EntityModel<Drink>> drinks = drinkRepository.
                findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(drinks,
                linkTo(methodOn(DrinkController.class).all()).withSelfRel());
    }
    @PostMapping("/drinks")
    ResponseEntity<?> newDrink(@RequestBody Drink newDrink){
        EntityModel<Drink> entityModel = assembler.toModel(drinkRepository.save(newDrink));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    @GetMapping("/drinks/{id}")
    EntityModel<Drink> one(@PathVariable Long id){
        Drink drink =  drinkRepository.findById(id).orElseThrow(() -> new DrinkNotFoundException(id));

        return assembler.toModel(drink);

    }
    @PutMapping("/drinks/{id}")
    ResponseEntity<?> replaceDrink(@RequestBody Drink newDrink, @PathVariable Long id){
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
    ResponseEntity<?> deleteDrink(@PathVariable Long id){
        drinkRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

package com.orangeandbronze.restbucks;

import com.orangeandbronze.restbucks.drinks.DrinkController;
import com.orangeandbronze.restbucks.orders.Order;
import com.orangeandbronze.restbucks.orders.OrderController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RestbucksController {

    RestbucksController () {

    }
    @GetMapping("/")
    public EntityModel<Restbucks> all(){
        return EntityModel.of(new Restbucks(),
                linkTo(methodOn(DrinkController.class).all()).withRel("drinks"),
                linkTo(methodOn(OrderController.class).all()).withRel("orders"));
    }

}

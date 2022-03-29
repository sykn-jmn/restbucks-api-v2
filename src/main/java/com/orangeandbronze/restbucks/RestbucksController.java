package com.orangeandbronze.restbucks;

import com.orangeandbronze.restbucks.drinks.DrinkController;
import com.orangeandbronze.restbucks.orders.OrderController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class RestbucksController {

    RestbucksController () { }
    @GetMapping
    public RepresentationModel root(){
        RepresentationModel model = new RepresentationModel();
        return model.add(linkTo(methodOn(DrinkController.class).all()).withRel("drinks"),
                linkTo(methodOn(OrderController.class).all()).withRel("orders"));
    }

}

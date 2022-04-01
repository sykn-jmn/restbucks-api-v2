package com.orangeandbronze.restbucks.orders;

import com.orangeandbronze.restbucks.drinks.Drink;
import com.orangeandbronze.restbucks.profile.Profile;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderModelAssembler assembler;

    public OrderController(OrderRepository orderRepository, OrderModelAssembler assember) {
        this.orderRepository = orderRepository;
        this.assembler = assember;
    }
    @GetMapping("")
    public CollectionModel<EntityModel<Order>> all(){
        List<EntityModel<Order>> orders = orderRepository
                .findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    EntityModel<Order> one(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return assembler.toModel(order);
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Drink drink, @AuthenticationPrincipal Profile profile){
        Order order = new Order(Status.PENDING,drink,1,drink.getPrice(), LocalDateTime.now().plusHours(8), profile);
        order = orderRepository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).one(order.getId())).toUri())
                .body(assembler.toModel(order));
    }
}

package com.orangeandbronze.restbucks.orders;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderModelAssembler assembler;

    public OrderController(OrderRepository orderRepository, OrderModelAssembler assember) {
        this.orderRepository = orderRepository;
        this.assembler = assember;
    }
    @GetMapping("/orders")
    CollectionModel<EntityModel<Order>> all(){
        List<EntityModel<Order>> orders = orderRepository
                .findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }
    @GetMapping("/orders/{id}")
    EntityModel<Order> one(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return assembler.toModel(order);
    }

    @PostMapping("/orders")
    ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order){
        order.setStatus(Status.PENDING);
        Order newOrder = orderRepository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
                .body(assembler.toModel(newOrder));
    }

    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus().equals(Status.PENDING)){
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body("You can't cancel an order that is in the " + order.getStatus() + " status");
//                 .body(Problem.create()
//                        .withTitle("Method not allowed")
//                        .withDetail("You can't canel an order that is in the " + order.getStatus() + " status");

    }
    @DeleteMapping("/orders/{id}/delete")
    ResponseEntity<?> complete(@PathVariable Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus().equals(Status.PENDING)){
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body("You can't complete an order that is in the " + order.getStatus() + " status");
//                .body(Problem.create()
//                        .withTitle("Method not allowed")
//                        .withDetail("You can't canel an order that is in the " + order.getStatus() + " status");

    }
}

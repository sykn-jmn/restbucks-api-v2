package com.orangeandbronze.restbucks.orders;
    import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

    import org.springframework.hateoas.EntityModel;
    import org.springframework.hateoas.server.RepresentationModelAssembler;
    import org.springframework.stereotype.Component;

@Component
class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Order> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel().withType("GET"),
                linkTo(methodOn(OrderController.class).all()).withRel("orders").withType("GET"));

        // Conditional links based on state of the order

        if (order.getStatus() == Status.PENDING) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel").withType("DELETE"));
            orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete").withType("DELETE"));
        }

        return orderModel;
    }
}
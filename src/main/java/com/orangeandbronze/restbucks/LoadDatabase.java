package com.orangeandbronze.restbucks;

import com.orangeandbronze.restbucks.drinks.Drink;
import com.orangeandbronze.restbucks.drinks.DrinkRepository;
import com.orangeandbronze.restbucks.orders.Order;
import com.orangeandbronze.restbucks.orders.OrderRepository;
import com.orangeandbronze.restbucks.orders.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DrinkRepository drinkRepository, OrderRepository orderRepository) {
        return args -> {
            drinkRepository.save(new Drink("Americano", "Starbucks Reserve® espresso with hot water", "P250.00", "https://d1ralsognjng37.cloudfront.net/6c716fb9-2137-4a1d-af41-4a2647a8c9f6.jpeg"));
            drinkRepository.save(new Drink("Cappuccino", "Starbucks Reserve® espresso with steamed milk, topped with a layer of foam", "P350.00", "https://d1ralsognjng37.cloudfront.net/0d2e6fa2-418c-4f6e-bb69-819468967aa5.jpeg"));
            drinkRepository.save(new Drink("Caramel Infused Latte", "Starbucks Reserve® espresso with whole milk infused with buttery caramel sauce", "P250.00", "https://d1ralsognjng37.cloudfront.net/4aad366a-8e04-4d00-be46-18090320b246.jpeg"));
            drinkRepository.save(new Drink("Matcha Green Tea Latte", "Pure matcha tea, steamed milk and demerara syrup", "P250.00", "https://d1ralsognjng37.cloudfront.net/ab529c7d-1481-4e6b-b9bb-990db4aa59d3.jpeg"));
            drinkRepository.save(new Drink("Chai Tea Latte", "A concentrated blend of full-bodied black teas, black pepper and spicy ginger with notes of cardamom and vanilla, lightly sweetened. Topped with hot water, steamed milk and foam.", "2350.00", "https://d1ralsognjng37.cloudfront.net/c49d796e-07ea-46bd-8bd8-b496a8851e38.jpeg"));

            orderRepository.save(new Order(Status.PENDING,"1", "1", "1", new Date().toString()));
            orderRepository.save(new Order(Status.COMPLETED,"1", "1", "1", new Date().toString()));

        };
    }
}
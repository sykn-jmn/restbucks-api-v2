package com.orangeandbronze.restbucks;

import com.orangeandbronze.restbucks.drinks.Drink;
import com.orangeandbronze.restbucks.drinks.DrinkRepository;
import com.orangeandbronze.restbucks.favorite.Favorite;
import com.orangeandbronze.restbucks.favorite.FavoriteRepository;
import com.orangeandbronze.restbucks.orders.Order;
import com.orangeandbronze.restbucks.orders.OrderRepository;
import com.orangeandbronze.restbucks.orders.Status;
import com.orangeandbronze.restbucks.profile.Profile;
import com.orangeandbronze.restbucks.profile.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DrinkRepository drinkRepository, OrderRepository orderRepository, FavoriteRepository favoriteRepository, ProfileRepository profileRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            Drink drink1 = new Drink("Americano", "Starbucks Reserve® espresso with hot water", new BigDecimal("250.00"), "https://d1ralsognjng37.cloudfront.net/6c716fb9-2137-4a1d-af41-4a2647a8c9f6.jpeg");
            Drink drink2 = new Drink("Cappuccino", "Starbucks Reserve® espresso with steamed milk, topped with a layer of foam", new BigDecimal("350.00"), "https://d1ralsognjng37.cloudfront.net/0d2e6fa2-418c-4f6e-bb69-819468967aa5.jpeg");
            Drink drink5 = new Drink("Chai Tea Latte", "A concentrated blend of full-bodied black teas, black pepper and spicy ginger with notes of cardamom and vanilla, lightly sweetened. Topped with hot water, steamed milk and foam.", new BigDecimal("235.00"), "https://d1ralsognjng37.cloudfront.net/c49d796e-07ea-46bd-8bd8-b496a8851e38.jpeg");

            drink1 = drinkRepository.save(drink1);
            drink2 = drinkRepository.save(drink2);
            drinkRepository.save(new Drink("Caramel Infused Latte", "Starbucks Reserve® espresso with whole milk infused with buttery caramel sauce", new BigDecimal("250.00"), "https://d1ralsognjng37.cloudfront.net/4aad366a-8e04-4d00-be46-18090320b246.jpeg"));
            drinkRepository.save(new Drink("Matcha Green Tea Latte", "Pure matcha tea, steamed milk and demerara syrup", new BigDecimal("250.00"), "https://d1ralsognjng37.cloudfront.net/ab529c7d-1481-4e6b-b9bb-990db4aa59d3.jpeg"));
            drink5 = drinkRepository.save(drink5);

            Profile eurese = new Profile("eurese", passwordEncoder.encode("strongpassword123"));
            Profile jeman = new Profile("jeman", passwordEncoder.encode("gwapito"));
            eurese = profileRepository.save(eurese);
            jeman = profileRepository.save(jeman);
            profileRepository.save(new Profile("viver", passwordEncoder.encode("conyorichkid123")));

            orderRepository.save(new Order(Status.PENDING,drink1, 1, new BigDecimal("1"), LocalDateTime.now(),eurese));
            orderRepository.save(new Order(Status.COMPLETED,drink2, 1, new BigDecimal("1"), LocalDateTime.now(),eurese));
            // eurese favorites
            favoriteRepository.save(new Favorite(eurese, drink1));
            favoriteRepository.save(new Favorite(eurese, drink5));
            favoriteRepository.save(new Favorite(eurese, drink2));

            //jeman favorites
            favoriteRepository.save(new Favorite(jeman, drink1));

//            System.out.println(drink);
        };
    }
}
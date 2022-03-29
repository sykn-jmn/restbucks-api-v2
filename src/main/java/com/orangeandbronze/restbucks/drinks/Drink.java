package com.orangeandbronze.restbucks.drinks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Drink {
    private @Id
    @GeneratedValue Long id;
    private String title;
    private String description;
    private String price;
    private String image;

    public Drink() { }
    public Drink(String title, String description, String price, String image) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return Objects.equals(id, drink.id) && Objects.equals(title, drink.title) && Objects.equals(description, drink.description) && Objects.equals(price, drink.price) && Objects.equals(image, drink.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, image);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

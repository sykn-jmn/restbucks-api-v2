package com.orangeandbronze.restbucks.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orangeandbronze.restbucks.favorite.Favorite;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Profile {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String userName;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "profile")
    private List<Favorite> favorites;

    public Profile () {}

    public Profile(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.favorites = new ArrayList<>();
    }

    public Profile(String userName, String password, List<Favorite> favorites) {
        this.userName = userName;
        this.password = password;
        this.favorites = favorites;
    }

    public Long getId() {
        return id;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) && Objects.equals(userName, profile.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }
}

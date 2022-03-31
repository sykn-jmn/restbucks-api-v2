package com.orangeandbronze.restbucks.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orangeandbronze.restbucks.favorite.Favorite;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements UserDetails {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String username;
    @JsonIgnore
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "profile")
    private List<Favorite> favorites;

    public Profile(String userName, String password) {
        this.username = userName;
        this.password = password;
        this.favorites = new ArrayList<>();
    }

    public Profile(String userName, String password, List<Favorite> favorites) {
        this.username = userName;
        this.password = password;
        this.favorites = favorites;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

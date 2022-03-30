package com.orangeandbronze.restbucks.profile;

import com.orangeandbronze.restbucks.favorite.Favorite;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileService implements UserDetailsService {
    @Autowired
    private final ProfileRepository profileRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Profile> profileOptional = profileRepository.findByUsername(username);
        if(profileOptional.isPresent()){
            return profileOptional.get();
        }
        throw new UsernameNotFoundException(username);
    }

    @Transactional
    public List<Favorite> getFavorites(Profile profile){
        return profile.getFavorites();
    }
}

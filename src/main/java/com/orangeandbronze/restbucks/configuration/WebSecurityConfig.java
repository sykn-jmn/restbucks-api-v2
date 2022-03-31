package com.orangeandbronze.restbucks.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests().mvcMatchers("/favorites").hasRole("USER")
                .mvcMatchers("/login").hasRole("USER")
                .mvcMatchers("/profile").hasRole("USER")
                .and()
                .authorizeRequests().anyRequest().permitAll()
                .and().httpBasic().authenticationEntryPoint(new NoPopUpBasicAuthenticationEntryPoint())
                .and()
                .csrf().disable();
    }
}

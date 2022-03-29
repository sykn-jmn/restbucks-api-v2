package com.orangeandbronze.restbucks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Restbucks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Restbucks () {}
}

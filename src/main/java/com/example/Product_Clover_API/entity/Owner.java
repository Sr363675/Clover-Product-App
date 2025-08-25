package com.example.Product_Clover_API.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "owner_details")
public class Owner {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="ownername", unique = true, nullable = false)
    private String ownername;

    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(name="password", unique = true, nullable = false)
    private String password;

    @Column(name="role", nullable = false)
    private String role;
}

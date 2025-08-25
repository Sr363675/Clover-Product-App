package com.example.Product_Clover_API.repository;

import com.example.Product_Clover_API.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner,Long> {

    Optional<Owner> findByOwnername(String ownername);

    Optional<Owner> findByEmail(String email);

}

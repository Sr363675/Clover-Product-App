package com.example.Product_Clover_API.repository;

import com.example.Product_Clover_API.entity.Owner;
import com.example.Product_Clover_API.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
}


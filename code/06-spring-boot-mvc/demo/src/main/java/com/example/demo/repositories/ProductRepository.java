package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop3ByOrderByNameAsc();
}

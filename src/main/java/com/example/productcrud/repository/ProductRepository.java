package com.example.productcrud.repository;

import com.example.productcrud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (:category IS NULL OR p.category = :category) " +
            "AND (:price IS NULL OR p.price = :price) " +
            "AND (:name IS NULL OR p.name LIKE %:name%)")
    List<Product> filterProducts(@Param("category") String category,
                                 @Param("price") Double price,
                                 @Param("name") String name);
}
package com.products.MSproducts.repository;

import com.products.MSproducts.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

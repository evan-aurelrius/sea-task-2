package com.evanaurelrius.seacademy.repository;

import com.evanaurelrius.seacademy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(int id);

    Iterable<Product> findAllByOrderByNameAsc();

    Iterable<Product> findAllByOrderByNameDesc();

    Iterable<Product> findAllByOrderByTimestampAsc();

    Iterable<Product> findAllByOrderByTimestampDesc();

}

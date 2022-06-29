package com.evanaurelrius.seacademy.repository;

import com.evanaurelrius.seacademy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(int id);

    @Query(value = "SELECT p FROM Product p WHERE p.forsale='1' ORDER BY p.name asc")
    Iterable<Product> findAllByOrderByNameAsc();

    @Query(value = "SELECT p FROM Product p WHERE p.forsale='1' ORDER BY p.name desc")
    Iterable<Product> findAllByOrderByNameDesc();

    @Query(value = "SELECT p FROM Product p WHERE p.forsale='1' ORDER BY p.timestamp asc")
    Iterable<Product> findAllByOrderByTimestampAsc();

    @Query(value = "SELECT p FROM Product p WHERE p.forsale='1' ORDER BY p.timestamp desc")
    Iterable<Product> findAllByOrderByTimestampDesc();

    @Query(value = "SELECT p FROM Product p WHERE p.forsale='0' AND ownerid=?1 ORDER BY p.name asc")
    Iterable<Product> findAllMyProductByOrderByNameAsc(long ownerid);

    @Query(value = "SELECT p FROM Product p WHERE p.forsale='0' AND ownerid=?1 ORDER BY p.name desc")
    Iterable<Product> findAllMyProductByOrderByNameDesc(long ownerid);

    @Query(value = "SELECT p FROM Product p WHERE p.forsale='0' AND ownerid=?1 ORDER BY p.timestamp asc")
    Iterable<Product> findAllMyProductByOrderByTimestampAsc(long ownerid);

    @Query(value = "SELECT p FROM Product p WHERE p.forsale='0' AND ownerid=?1 ORDER BY p.timestamp desc")
    Iterable<Product> findAllMyProductByOrderByTimestampDesc(long ownerid);

}

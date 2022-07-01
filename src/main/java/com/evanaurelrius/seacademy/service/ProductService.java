package com.evanaurelrius.seacademy.service;

import com.evanaurelrius.seacademy.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {

    Boolean saveProduct(String name, MultipartFile image, String desc, String price);

    List<Product> getAllProducts();

    List<Product> getAllProductsByNameAsc();

    List<Product> getAllProductsByNameDesc();

    List<Product> getAllProductsByTimestampAsc();

    List<Product> getAllProductsByTimestampDesc();

    List<Product> getAllPurchasedProductByNameAsc(long ownerId);

    List<Product> getAllPurchasedProductByNameDesc(long ownerId);

    List<Product> getAllPurchasedProductByTimeAsc(long ownerId);

    List<Product> getAllPurchasedProductByTimeDesc(long ownerId);

    Product buyProduct(String productId, long ownerId);

    void setDefaultProductIfUnset();

}

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

}

package com.evanaurelrius.seacademy.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@NoArgsConstructor
public class ProductDTO {

    private String name;

    private MultipartFile imageSrc;

    private String description;

    private String price;

    private Date date;

    public ProductDTO(String name, MultipartFile imageSrc, String description, String price, Date date) {
        this.name = name;
        this.imageSrc = imageSrc;
        this.description = description;
        this.price = price;
        this.date = date;
    }
}

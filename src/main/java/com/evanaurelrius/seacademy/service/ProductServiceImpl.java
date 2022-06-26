package com.evanaurelrius.seacademy.service;

import com.evanaurelrius.seacademy.model.Product;
import com.evanaurelrius.seacademy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    public static final String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/imagedata";

    @Override
    public Boolean saveProduct(String name, MultipartFile image, String desc, String price) {
        if(Boolean.FALSE.equals(isValidNumber(price))) return false;

        Product newProduct = new Product();
        java.util.Date dNow = new java.util.Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        StringBuilder fileNames = new StringBuilder();
        String fileName = name+datetime+image.getOriginalFilename().substring(image.getOriginalFilename().length()-4);
        Path fileNameAndPath = Paths.get(uploadDir, fileName);

        try{
            Files.write(fileNameAndPath, image.getBytes());
        } catch (Exception e) {
            return false;
        }

        newProduct.setImageSrc(fileName);
        newProduct.setName(name);
        newProduct.setDescription(desc);
        newProduct.setPrice(convertToProper(price));
        newProduct.setDate(new Date(System.currentTimeMillis()));
        productRepository.save(newProduct);
        return true;
    }

    public Boolean isValidNumber(String price) {
        try {
            BigInteger bn = new BigInteger(price);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String convertToProper(String text) {
        text = new StringBuilder(text).reverse().toString();
        Pattern p = Pattern.compile("(.{" + 3 + "})", Pattern.DOTALL);
        Matcher m = p.matcher(text);
        String result = new StringBuilder(m.replaceAll("$1" + ".")).reverse().toString();
        if(result.substring(0,1).equals(".")) {
            result = result.substring(1);
        }
        result = "Rp "+result;
        return result;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

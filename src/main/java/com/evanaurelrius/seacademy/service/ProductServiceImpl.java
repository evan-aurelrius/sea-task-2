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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BalanceService balanceService;

    public static final String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/imagedata";

    @Override
    public Boolean saveProduct(String name, MultipartFile image, String desc, String price) {
        if(Boolean.FALSE.equals(isValidNumber(price))) return false;

        Product newProduct = new Product();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(new Timestamp(System.currentTimeMillis()));
        StringBuilder fileNames = new StringBuilder();
        String fileName = name.replace(" ","")+datetime+image.getOriginalFilename().substring(image.getOriginalFilename().length()-4);
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
        newProduct.setTimestamp(new Timestamp(System.currentTimeMillis()));
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
        result = "Rp"+result;
        return result;
    }

    @Override
    public Product buyProduct(String productId, long ownerId) {
        Product bought = productRepository.findById(Long.parseLong(productId)).orElse(null);
        if(bought != null) {
            String rawPrice = bought.getPrice().substring(2).replace(".","");
            String currentBalance = balanceService.getBalance().getCurrentBalance();
            if(currentBalance.equals(balanceService.addBalance(rawPrice))) {
                return null;
            }
            changeProductStatus(bought, ownerId);
        }
        return bought;
    }

    private void changeProductStatus(Product product, long ownerId) {
        product.setForsale(false);
        product.setOwnerid(ownerId);
        product.setTimestamp(new Timestamp(System.currentTimeMillis()));
        productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByNameAsc() { return (List<Product>) productRepository.findAllByOrderByNameAsc(); }

    @Override
    public List<Product> getAllProductsByNameDesc() { return (List<Product>) productRepository.findAllByOrderByNameDesc(); }

    @Override
    public List<Product> getAllProductsByTimestampAsc() { return (List<Product>) productRepository.findAllByOrderByTimestampAsc(); }

    @Override
    public List<Product> getAllProductsByTimestampDesc() { return (List<Product>) productRepository.findAllByOrderByTimestampDesc(); }

    @Override
    public List<Product> getAllPurchasedProductByNameAsc(long ownerId) { return (List<Product>) productRepository.findAllMyProductByOrderByNameAsc(ownerId); }

    @Override
    public List<Product> getAllPurchasedProductByNameDesc(long ownerId) { return (List<Product>) productRepository.findAllMyProductByOrderByNameDesc(ownerId); }

    @Override
    public List<Product> getAllPurchasedProductByTimeAsc(long ownerId) { return (List<Product>) productRepository.findAllMyProductByOrderByTimestampAsc(ownerId); }

    @Override
    public List<Product> getAllPurchasedProductByTimeDesc(long ownerId) { return (List<Product>) productRepository.findAllMyProductByOrderByTimestampDesc(ownerId); }

}

package com.evanaurelrius.seacademy.controller;

import com.evanaurelrius.seacademy.model.Account;
import com.evanaurelrius.seacademy.model.Product;
import com.evanaurelrius.seacademy.model.ProductDTO;
import com.evanaurelrius.seacademy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    private String error = "", success = "";

    public static final String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/imagedata";

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String homepage(Model model, HttpSession session) {
        String fullName = (String) session.getAttribute("fullName");
        if(fullName!=null){
            model.addAttribute("name", fullName);
        }
        if(Boolean.FALSE.equals(success.isEmpty())) {
            model.addAttribute("success", success);
            success = "";
        }
        List<Product> products = productService.getAllProductsByTimestampDesc();
        model.addAttribute("sortingMechanism", "Latest first");
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping(path = "/sort/{sortingMechanism}")
    public String productSort(@PathVariable("sortingMechanism") String sortBy, Model model, HttpSession session) {
        String fullName = (String) session.getAttribute("fullName");
        if(fullName!=null){
            model.addAttribute("name", fullName);
        }
        List<Product> products = new ArrayList<>();
        String mechanism = "";
        switch (sortBy) {
            case "nameAsc":
                products = productService.getAllProductsByNameAsc();
                mechanism = "A to Z";
                break;
            case "nameDesc":
                products = productService.getAllProductsByNameDesc();
                mechanism = "Z to A";
                break;
            case "timeAsc":
                products = productService.getAllProductsByTimestampAsc();
                mechanism = "Oldest first";
                break;
            case "timeDesc":
                products = productService.getAllProductsByTimestampDesc();
                mechanism = "Latest first";
                break;
        }
        model.addAttribute("sortingMechanism", mechanism);
        model.addAttribute("products", products);
        return "home";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/createProduct")
    public String createProduct(Model model, HttpSession session) {
        String fullName = (String) session.getAttribute("fullName");
        if(fullName!=null){
            ProductDTO productDto = new ProductDTO();
            model.addAttribute("name", fullName);
            model.addAttribute("productDto", productDto);
        } else {
            return "redirect:/login";
        }
        if(Boolean.FALSE.equals(error.isEmpty())) {
            model.addAttribute("error", error);
            error = "";
        }
        return "createProduct";
    }

    @PostMapping("/createProduct")
    public String createProductAndUpload(@ModelAttribute ProductDTO productDto, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        if(currentUser==null) return "redirect:/login";

        boolean savedSuccessfully = productService.saveProduct(productDto.getName(),productDto.getImageSrc(),productDto.getDescription(),productDto.getPrice());
        if(Boolean.TRUE.equals(savedSuccessfully)) {
            System.out.println("aman");
            return "redirect:/";
        }
        System.out.println("Error");
        error = "Price is invalid! Only numbers are allowed.";
        return "redirect:/createProduct";
    }

    @GetMapping(path = "/buy/{productId}")
    public String buyProduct(@PathVariable("productId") String productId, Model model, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        String fullName = (String) session.getAttribute("fullName");
        if(fullName==null){
            return "redirect:/login";
        }
        if(productService.buyProduct(productId, currentUser.getId()) != null) {
            success = "Thank you for purchasing our products! May the SEA be with you.";
        }
        model.addAttribute("name", fullName);
        return "redirect:/";
    }

}

package com.evanaurelrius.seacademy.controller;

import com.evanaurelrius.seacademy.model.Account;
import com.evanaurelrius.seacademy.model.Product;
import com.evanaurelrius.seacademy.model.ProductDTO;
import com.evanaurelrius.seacademy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    private String error = "";

    public static final String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/imagedata";

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String homepage(Model model, HttpSession session) {
        String fullName = (String) session.getAttribute("fullName");
        if(fullName!=null){
            model.addAttribute("name", fullName);
        }
        List<Product> products = productService.getAllProducts();
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


}

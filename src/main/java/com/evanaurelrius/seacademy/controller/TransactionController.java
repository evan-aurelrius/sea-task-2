package com.evanaurelrius.seacademy.controller;

import com.evanaurelrius.seacademy.model.Account;
import com.evanaurelrius.seacademy.model.BalanceDTO;
import com.evanaurelrius.seacademy.model.Product;
import com.evanaurelrius.seacademy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET, path = "/transaction")
    public String balanceBox(Model model, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        String fullName = (String) session.getAttribute("fullName");
        if(currentUser!=null){
            BalanceDTO balanceDto = new BalanceDTO();
            model.addAttribute("name", fullName);
        } else {
            return "redirect:/login";
        }
        List<Product> products = productService.getAllPurchasedProductByTimeDesc(currentUser.getId());
        model.addAttribute("products", products);
        model.addAttribute("sortingMechanism", "Latest first");

        return "transaction";
    }

    @GetMapping(path = "/sortTransaction/{sortingMechanism}")
    public String productSort(@PathVariable("sortingMechanism") String sortBy, Model model, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        String fullName = (String) session.getAttribute("fullName");
        if(currentUser==null){
            return "redirect:/login";
        }
        List<Product> products = new ArrayList<>();
        String mechanism = "";
        switch (sortBy) {
            case "nameAsc":
                products = productService.getAllPurchasedProductByNameAsc(currentUser.getId());
                mechanism = "A to Z";
                break;
            case "nameDesc":
                products = productService.getAllPurchasedProductByNameDesc(currentUser.getId());
                mechanism = "Z to A";
                break;
            case "timeAsc":
                products = productService.getAllPurchasedProductByTimeAsc(currentUser.getId());
                mechanism = "Oldest first";
                break;
            case "timeDesc":
                products = productService.getAllPurchasedProductByTimeDesc(currentUser.getId());
                mechanism = "Latest first";
                break;
        }
        model.addAttribute("name", fullName);
        model.addAttribute("sortingMechanism", mechanism);
        model.addAttribute("products", products);
        return "transaction";
    }

}

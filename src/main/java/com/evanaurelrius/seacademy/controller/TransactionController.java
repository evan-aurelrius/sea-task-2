package com.evanaurelrius.seacademy.controller;

import com.evanaurelrius.seacademy.model.Account;
import com.evanaurelrius.seacademy.model.BalanceDTO;
import com.evanaurelrius.seacademy.model.Product;
import com.evanaurelrius.seacademy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET, path = "/transaction")
    public String balanceBox(Model model, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        String fullName = (String) session.getAttribute("fullName");
        if(fullName!=null){
            BalanceDTO balanceDto = new BalanceDTO();
            model.addAttribute("name", fullName);
        } else {
            return "redirect:/login";
        }
        List<Product> products = productService.getAllPurchasedProduct(currentUser.getId());
        model.addAttribute("products", products);
        return "transaction";
    }

}

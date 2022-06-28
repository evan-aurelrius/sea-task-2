package com.evanaurelrius.seacademy.controller;

import com.evanaurelrius.seacademy.model.BalanceDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class TransactionController {

    @RequestMapping(method = RequestMethod.GET, path = "/transaction")
    public String balanceBox(Model model, HttpSession session) {
        String fullName = (String) session.getAttribute("fullName");
        if(fullName!=null){
            BalanceDTO balanceDto = new BalanceDTO();
            model.addAttribute("name", fullName);
        } else {
            return "redirect:/login";
        }
        return "transaction";
    }

}

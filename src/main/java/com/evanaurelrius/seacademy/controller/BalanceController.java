package com.evanaurelrius.seacademy.controller;

import com.evanaurelrius.seacademy.model.Account;
import com.evanaurelrius.seacademy.model.BalanceDTO;
import com.evanaurelrius.seacademy.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class BalanceController {

    private String error="";

    @Autowired
    BalanceService balanceService;

    @RequestMapping(method = RequestMethod.GET, path = "/balanceBox")
    public String balanceBox(Model model, HttpSession session) {
        String fullName = (String) session.getAttribute("fullName");
        if(fullName!=null){
            BalanceDTO balanceDto = new BalanceDTO();
            model.addAttribute("name", fullName);
            model.addAttribute("balanceDto", balanceDto);
            model.addAttribute("balance",balanceService.getStringBalance());
            if(Boolean.FALSE.equals(error.isEmpty())) {
                model.addAttribute("error", error);
                error = "";
            }
        } else {
            return "redirect:/login";
        }
        return "balance";
    }

    @PostMapping("/topup")
    public String topup(@ModelAttribute BalanceDTO balanceDto, Model model, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        if(currentUser==null) return "redirect:/login";

        String balance = balanceService.getStringBalance();
        String newBalance = balanceService.addBalance(balanceDto.getAmount());
        if(newBalance.equals(balance)) {
            error = "Invalid amount to top up!";
        } else{
            model.addAttribute("balance",newBalance);
        }        return "redirect:/balanceBox";
    }

    @PostMapping("/withdraw")
    public String withdraw(@ModelAttribute BalanceDTO balanceDto, Model model, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        if(currentUser==null) return "redirect:/login";


        String balance = balanceService.getStringBalance();
        String newBalance = balanceService.subtractBalance(balanceDto.getAmount());
        if(newBalance.equals(balance)) {
            error = "Invalid amount to withdraw!";
        } else{
            model.addAttribute("balance",newBalance);
        }
        return "redirect:/balanceBox";
    }
}

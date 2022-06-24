package com.evanaurelrius.seacademy.controller;

import com.evanaurelrius.seacademy.model.Account;
import com.evanaurelrius.seacademy.model.AccountDTO;
import com.evanaurelrius.seacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private String error = "";
    private Account sessionAccount;
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String homepage(Model model, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        if(currentUser!=null){
            model.addAttribute("name", currentUser.getFirst_name()
                    + " " + currentUser.getLast_name());
        }
        return "home";
    }

    @GetMapping("/register")
    public String registerPage(Model model, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        if(currentUser!=null) return "redirect:/";

        AccountDTO accountDto = new AccountDTO();
        model.addAttribute("accountDto", accountDto);
        if(Boolean.FALSE.equals(error.isEmpty())) {
            model.addAttribute("error", error);
            error = "";
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@ModelAttribute AccountDTO accountDto, HttpServletRequest request) {
        if(Boolean.TRUE.equals(userService.accountIsExist(accountDto.getSid()))) {
            error = "Account is exist, please log in!";
            return "redirect:/register";
        }
        Account user = userService.registerUser(accountDto.getFirst_name(), accountDto.getLast_name(), accountDto.getSid(), accountDto.getPassword());
        if(user==null) {
            error = "Invalid Student's ID!";
            return "redirect:/register";
        } else {
            request.getSession().setAttribute("currentUser",user);
            return "redirect:/";
        }
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        Account currentUser = (Account) session.getAttribute("currentUser");
        if(currentUser!=null) return "redirect:/";

        AccountDTO accountDto = new AccountDTO();
        model.addAttribute("accountDto", accountDto);
        if(Boolean.FALSE.equals(error.isEmpty())) {
            model.addAttribute("error", error);
            error = "";
        }
        return "login";
    }

    @PostMapping("/login")
    public String authenticateAccount(@ModelAttribute AccountDTO accountDto, HttpServletRequest request) {
        Account user = userService.authenticate(accountDto.getSid(), accountDto.getPassword());
        if(user == null) {
            error = "Incorrect student's id or password!";
            return "redirect:/login";
        }
        request.getSession().setAttribute("currentUser",user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

}
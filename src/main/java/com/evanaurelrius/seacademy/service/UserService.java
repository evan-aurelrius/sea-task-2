package com.evanaurelrius.seacademy.service;


import com.evanaurelrius.seacademy.model.Account;

public interface UserService {

    Account registerUser(String fname, String lname, String sid, String password);

    Account authenticate(String sid, String password);

    Boolean accountIsExist(String sid);
}

package com.evanaurelrius.seacademy.service;

import com.evanaurelrius.seacademy.model.Balance;

public interface BalanceService {

    Balance getBalance();

    String getStringBalance();

    String addBalance(String other);

    String subtractBalance(String other);

    void setDefaultBalanceIfUnset();

}

package com.evanaurelrius.seacademy.service;

import com.evanaurelrius.seacademy.model.Balance;

import java.math.BigInteger;

public interface BalanceService {

    Balance getBalance();

    String getStringBalance();

    String addBalance(String other);

    String subtractBalance(String other);

    void setDefaultBalanceIfUnset();

}

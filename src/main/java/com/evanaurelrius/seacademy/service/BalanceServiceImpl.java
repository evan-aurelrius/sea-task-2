package com.evanaurelrius.seacademy.service;

import com.evanaurelrius.seacademy.model.Balance;
import com.evanaurelrius.seacademy.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BalanceServiceImpl implements BalanceService{

    @Autowired
    BalanceRepository balanceRepository;

    @Override
    public Balance getBalance() {
        return balanceRepository.findById(1).orElse(null);
    }

    @Override
    public String getStringBalance() {
        return getBalance().getCurrentBalance();
    }

    @Override
    public String addBalance(String other) {
        Balance balance = getBalance();
        BigInteger currentBalance = new BigInteger(balance.getCurrentBalance().substring(2).replace(".",""));
        //        check amount to subtract is a valid number
        try{
            currentBalance = currentBalance.add(new BigInteger(other));
        } catch (Exception e) {
            return balance.getCurrentBalance();
        }

        String strCurrentBalance = convertToProper(currentBalance.toString());
        balance.setCurrentBalance(strCurrentBalance);
        balanceRepository.save(balance);
        return strCurrentBalance;
    }

    @Override
    public String subtractBalance(String other) {
        Balance balance = getBalance();
        BigInteger currentBalance = new BigInteger(balance.getCurrentBalance().substring(2).replace(".",""));

        //        check amount to subtract is a valid number
        try{
            currentBalance = currentBalance.subtract(new BigInteger(other));
        } catch (Exception e) {
            return getBalance().getCurrentBalance();
        }

        //        check balance is sufficient
        if(currentBalance.compareTo(new BigInteger("0")) == -1) {
            return getBalance().getCurrentBalance();
        }

        String strCurrentBalance = convertToProper(currentBalance.toString());

        balance.setCurrentBalance(strCurrentBalance);
        balanceRepository.save(balance);
        return strCurrentBalance;
    }

    public static String convertToProper(String text) {
        text = new StringBuilder(text).reverse().toString();
        Pattern p = Pattern.compile("(.{" + 3 + "})", Pattern.DOTALL);
        Matcher m = p.matcher(text);
        String result = new StringBuilder(m.replaceAll("$1" + ".")).reverse().toString();
        if(result.substring(0,1).equals(".")) {
            result = result.substring(1);
        }
        result = "Rp"+result;
        return result;
    }

}

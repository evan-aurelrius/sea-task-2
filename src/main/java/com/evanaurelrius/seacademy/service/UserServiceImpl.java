package com.evanaurelrius.seacademy.service;

import com.evanaurelrius.seacademy.model.Account;
import com.evanaurelrius.seacademy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    public Account registerUser(String fname, String lname, String sid, String password) {
        if(fname.isEmpty() || sid.isEmpty()) return null;
        if(Boolean.FALSE.equals(checkSid(sid))) return null;

        fname = fname.length() > 1 ? fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase() : fname.toUpperCase();
        if(lname.length() > 0)
            lname = lname.length() > 1 ? lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase() : lname.toUpperCase();

        try{
            return userRepository.save(new Account(fname, lname, sid, password));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Account authenticate(String sid, String password) {
        return userRepository.findBySidAndPassword(sid, password).orElse(null);
    }

    @Override
    public Boolean accountIsExist(String sid) {
        if(userRepository.findBySid(sid).isPresent()) return true;
        return false;
    }

    private boolean checkSid(String sid) {
        if(sid.length()==5 && isNumeric(sid)) {
            char[] charOfSid = sid.toCharArray();
            int[] intOfSid = new int[5];
            for (int i = 0; i < 3; i++) {
                if (Character.isDigit(charOfSid[i])) {
                    int a = Integer.parseInt(String.valueOf(charOfSid[i]));
                    intOfSid[i] = a;
                }
            }
            if(intOfSid[0]+intOfSid[1]+intOfSid[2]==Integer.parseInt(""+charOfSid[3]+charOfSid[4]) ) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}

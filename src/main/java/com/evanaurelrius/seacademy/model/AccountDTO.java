package com.evanaurelrius.seacademy.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AccountDTO {
    private String first_name;
    private String last_name;
    private String sid;
    private String password;

    public AccountDTO() {}

    public AccountDTO(String first_name, String last_name, String sid, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.sid = sid;
        this.password = password;
    }
}

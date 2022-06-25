package com.evanaurelrius.seacademy.model;

import javax.persistence.*;

@Entity
@Table(name = "balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private int id;

    @Column(name = "current")
    private String currentBalance;

    public Balance() {
    }

    public Balance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getCurrentBalance(){
        return currentBalance;
    }

    public void setCurrentBalance(String newBalance) {
        currentBalance = newBalance;
    }
}

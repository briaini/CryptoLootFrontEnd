package com.universityoflimerick.cryptolootfrontend.adam.User;

import com.universityoflimerick.cryptolootfrontend.dillon.Coin;

import java.math.BigDecimal;


public interface User {
    void addCoin(Coin coin);
    void pay(String address, BigDecimal amount, String coin);
    void request(String address, BigDecimal amount, String coin);
}

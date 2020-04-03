package com.universityoflimerick.cryptolootfrontend.Model.User;

import com.universityoflimerick.cryptolootfrontend.Model.Coin.Coin;

import java.math.BigDecimal;


public interface User {
    void addCoin(Coin coin);
    void pay(String address, BigDecimal amount, Coin coin);
    void request(String address, BigDecimal amount, Coin coin);
    String getType();
    Coin matchCoin(String coinName);
    void sendMessage(String message);
    String getUsername();
}

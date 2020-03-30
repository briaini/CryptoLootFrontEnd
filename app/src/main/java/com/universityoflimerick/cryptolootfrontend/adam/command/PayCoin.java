package com.universityoflimerick.cryptolootfrontend.adam.command;

import com.universityoflimerick.cryptolootfrontend.adam.User.User;
import com.universityoflimerick.cryptolootfrontend.dillon.Coin;

import java.math.BigDecimal;

public class PayCoin implements CoinAction {
    private User user;
    private String address;
    private BigDecimal amount;
    private Coin coin;

    public PayCoin(User user, String address, BigDecimal amount, Coin coin){
        this.user = user;
        this.address = address;
        this.amount = amount;
        this.coin = coin;
    }

    public void execute(){
        user.pay(this.address, this.amount, this.coin);
    }
}

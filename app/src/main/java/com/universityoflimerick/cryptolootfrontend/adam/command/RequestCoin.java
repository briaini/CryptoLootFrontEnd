package com.universityoflimerick.cryptolootfrontend.adam.command;

import com.universityoflimerick.cryptolootfrontend.adam.User.User;

import java.math.BigDecimal;

public class RequestCoin implements CoinAction {
    private User user;
    private String address;
    private BigDecimal amount;
    private String coin;

    public RequestCoin(User user, String address, BigDecimal amount, String coin){
        this.user = user;
        this.address = address;
        this.amount = amount;
        this.coin = coin;
    }

    public void execute(){
        user.request(this.address, this.amount, this.coin);
    }
}

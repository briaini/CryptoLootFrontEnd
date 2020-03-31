package com.universityoflimerick.cryptolootfrontend.Model.Coin;

import com.universityoflimerick.cryptolootfrontend.Model.User.User;
import com.universityoflimerick.cryptolootfrontend.Utils.command.CoinAction;

import java.math.BigDecimal;

public class RequestCoin implements CoinAction {
    private User user;
    private String address;
    private BigDecimal amount;
    private Coin coin;

    public RequestCoin(User user, String address, BigDecimal amount, Coin coin){
        this.user = user;
        this.address = address;
        this.amount = amount;
        this.coin = coin;
    }

    public void execute(){
        user.request(this.address, this.amount, this.coin);
    }
}

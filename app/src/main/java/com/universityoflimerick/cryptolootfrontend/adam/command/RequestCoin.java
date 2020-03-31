package com.universityoflimerick.cryptolootfrontend.adam.command;

import com.universityoflimerick.cryptolootfrontend.adam.User.User;
import com.universityoflimerick.cryptolootfrontend.dillon.Coin;

import java.math.BigDecimal;

public class RequestCoin implements CoinAction {
    private User user;
    private String address;
    private BigDecimal amount;
    private Coin coin;

    /**
     * RequestCoin object so users have the ability to request a payment from another account.
     * @param user
     * @param address
     * @param amount
     * @param coin
     */
    public RequestCoin(User user, String address, BigDecimal amount, Coin coin){
        this.user = user;
        this.address = address;
        this.amount = amount;
        this.coin = coin;
    }

    /**
     * Calls the request method in the user class
     */
    public void execute(){

        user.request(this.address, this.amount, this.coin);
    }
}

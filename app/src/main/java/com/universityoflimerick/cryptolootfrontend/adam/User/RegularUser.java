package com.universityoflimerick.cryptolootfrontend.adam.User;

import com.universityoflimerick.cryptolootfrontend.dillon.Coin;

import java.math.BigDecimal;
import java.util.ArrayList;

public class RegularUser implements User {

    private String username;
    private String email;
    private ArrayList<Coin> coins;

    public RegularUser(){
        coins = new ArrayList<Coin>();
    }

    public RegularUser(String name, String email){
        this.username = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Coin> getCoin(){
        return coins;
    }

    public void addCoin(Coin coin){
        coins.add(coin);
    }

    public void pay(String address, BigDecimal amount, String coin){
        for(int i = 0; i < coins.size(); i++){
            if (coin.equals(coins.get(i).getName())) {
                    coins.get(i).subtract(amount);
                    i = coins.size();
            }
        }
    }
    public void request(String address, BigDecimal amount, String coin){}
}

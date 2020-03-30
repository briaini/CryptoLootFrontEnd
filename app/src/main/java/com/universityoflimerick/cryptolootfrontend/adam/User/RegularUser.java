package com.universityoflimerick.cryptolootfrontend.adam.User;

import com.universityoflimerick.cryptolootfrontend.dillon.Coin;

import java.math.BigDecimal;
import java.util.ArrayList;

public class RegularUser implements User {

    private String username;
    private String email;
    private ArrayList<Coin> coins;
    private String type;

    public RegularUser(){
        this.type = "regular";
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

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public void pay(String address, BigDecimal amount, Coin coin){
        coin.subtract(amount);
    }
    public void request(String address, BigDecimal amount, Coin coin){}

    public Coin matchCoin(String coinName){
        for(int i = 0; i < coins.size(); i++){
            if (coinName.equals(coins.get(i).getName())) {
                return coins.get(i);
            }
        }
        return null;
    }
}

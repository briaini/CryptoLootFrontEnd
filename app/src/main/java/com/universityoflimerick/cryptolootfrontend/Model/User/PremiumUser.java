package com.universityoflimerick.cryptolootfrontend.Model.User;

import com.universityoflimerick.cryptolootfrontend.Activities.ChatActivity;
import com.universityoflimerick.cryptolootfrontend.Model.Coin.Coin;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PremiumUser implements User {
    private String username;
    private String email;
    private ArrayList<Coin> coins;
    private String type;

    /**
     * User with a premium type.
     */
    public PremiumUser(){
        this.type = "premium";
        coins = new ArrayList<Coin>();
    }

    public PremiumUser(String name, String email){
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

    /**
     * Pay method which used to send the payment and subtract amount from user
     * @param address The address of receiver
     * @param amount The amount of the payment
     * @param coin The coin being used
     */
    public void pay(String address, BigDecimal amount, Coin coin){
        this.sendPayment(address, amount);
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

    @Override
    public void sendMessage(String message) {
        ChatActivity.showMessage(this,message);
    }


    public void sendPayment(String address, BigDecimal amount){}
}

package com.universityoflimerick.cryptolootfrontend.Model.Coin;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Coin{
    private String name;
    private String address;
    private Crypto baseCrypto;
    private Crypto purseCrypto;
    private BigDecimal amountInBaseCrypto;
    private BigDecimal amountInPurseCrypto;
    private BigDecimal exchangeRate;

    //base will nearly always be bitcoin so we can pass the same bitcoin object when passing this method
    public Coin(String name, Crypto base, Crypto purse, String amountPurse){
        this.name = name;
        this.baseCrypto = base;
        this.purseCrypto= purse;
        this.amountInPurseCrypto= new BigDecimal(amountPurse);
        this.exchangeRate       = new BigDecimal(purseCrypto.getExchangeRate().toString());
        this.amountInPurseCrypto.setScale(8, RoundingMode.HALF_EVEN);
        this.exchangeRate.setScale(8, RoundingMode.HALF_EVEN);
        this.amountInBaseCrypto = divide(exchangeRate);
        this.address = "myAddress";
    }

    public String getAddress() {
        return address;
    }

    public String getName(){
        return this.name;
    }
    public void add(BigDecimal temp){
        amountInPurseCrypto = amountInPurseCrypto.add(temp);
        refresh();
    }

    public void subtract(BigDecimal temp){
        amountInPurseCrypto = amountInPurseCrypto.subtract(temp);
        refresh();
    }

    public BigDecimal multiply(BigDecimal temp){
        return this.amountInPurseCrypto.multiply(temp);
    }

    public BigDecimal divide(BigDecimal temp){
        return this.amountInPurseCrypto.divide(temp, 8, RoundingMode.HALF_EVEN);
    }

    public String toString(){
        String info = purseCrypto.getName() + "\nAmount:\t\t" + amountInPurseCrypto.toString() + "\nAmount in Base Crypto:\t" + amountInBaseCrypto.toString() + "\n";
        return info;
    }
    public void refresh(){
        //calculate new balance in base coin
        this.amountInBaseCrypto = divide(exchangeRate);
    }
    /*public int compareWithBaseCurrency(BigDecimal baseCryptoAmount){
        int result = this.amountInBaseCrypto.compareTo(baseCryptoAmount);
        return result;
    }
    public int compareWithPurseCurrency(BigDecimal purseCryptoAmount){
        int result = this.amountInPurseCrypto.compareTo(purseCryptoAmount);
        return result;
    }*/
    public boolean transfer(Coin receiving, BigDecimal amount){
        if(this.getBalanceInPurseCoin().compareTo(amount)==0 || this.getBalanceInPurseCoin().compareTo(amount)==1 ){
            this.subtract(amount);
            if(this.getName().equals(receiving.getName())){
                //do nothing if trying to convert the the same crypto
            }
            //if converting BTC -> Other, multiply amount by receiving coin exchange rate
            else if(this.getName().equals("Bitcoin")){
                System.out.println("BTC " + amount.toString() + " BEING CONVERTED TO");
                amount = amount.multiply(receiving.getExchangeRate());
                System.out.println("Amount after conversion to " + receiving.getName() + " is " + amount.toString());
            }
            //if converting non BTC -> BTC, divide sending coin amount by exchange rate to get amount in BTC
            else if(receiving.getName().equals("Bitcoin")){
                System.out.println(this.getName() +" amount " + amount.toString() + " BEING CONVERTED TO");
                amount = amount.divide(this.getExchangeRate(), 10, RoundingMode.HALF_EVEN);
                System.out.println(receiving.getName() + " amount " + amount.toString());
            }
            //converting non BTC to non BTC
            //Must convert from sending -> BTC, then BTC -> receiving
            else {
                System.out.println(this.getName() + " Amount being converted is " + amount.toString());
                amount = amount.divide(this.getExchangeRate(), 10, RoundingMode.HALF_EVEN);
                System.out.println("After convert to BTC is " + amount.toString());
                amount = amount.multiply(receiving.getExchangeRate());
                System.out.println("After convert to Target is " + amount.toString());
            }
            receiving.add(amount);
            return true;
        }
        else{
            System.out.println(this.getName() + " : NOT ENOUGH TO CONVERT " + amount.toString() + " TO " + receiving.getName());
            return false;
        }
    }

    public BigDecimal getBalanceInPurseCoin(){
        return this.amountInPurseCrypto;
    }
    public BigDecimal getBalanceInBaseCoin(){
        return this.amountInBaseCrypto;
    }
    public BigDecimal getExchangeRate(){
        return purseCrypto.getExchangeRate();
    }

}

package com.universityoflimerick.cryptolootfrontend.dillon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Coin{
    private String name;
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
        //this.exchangeRate = purseCrypto.getExchangeRate();
    }
    public String getName(){
        return this.name;
    }
    public void add(BigDecimal temp){
        System.out.println("ADD BEFORE IS " + amountInPurseCrypto.toString());
        amountInPurseCrypto = amountInPurseCrypto.add(temp);
        System.out.println("ADD AFTER IS " + amountInPurseCrypto.toString());
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
        //need to override operators
        this.amountInBaseCrypto = divide(exchangeRate);
    }
    public int compareWithBaseCurrency(BigDecimal baseCryptoAmount){
        int result = this.amountInBaseCrypto.compareTo(baseCryptoAmount);
        return result;
    }
    public int compareWithPurseCurrency(BigDecimal purseCryptoAmount){
        int result = this.amountInPurseCrypto.compareTo(purseCryptoAmount);
        return result;
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

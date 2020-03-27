package com.universityoflimerick.cryptolootfrontend.dillon;

import java.math.BigDecimal;

public class Crypto{
    private String name;
    private BigDecimal exchangeRateToBase;

    public Crypto(String cryptoName){
        this.name = cryptoName;
        if(this.name=="Bitcoin"){
            exchangeRateToBase = new BigDecimal("1.0");
        }
        else{
            exchangeRateToBase = new BigDecimal("1.2");
            //return exchangeRateToBase;
        }
        exchangeRateToBase.setScale(8);
    }
    public String getName(){
        return this.name;
    }
    public BigDecimal getExchangeRate(){
        return exchangeRateToBase;
    }
    public void setExchangeRate(String rate){
        exchangeRateToBase = new BigDecimal(rate);
    }
}

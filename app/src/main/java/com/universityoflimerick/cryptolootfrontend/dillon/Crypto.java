package com.universityoflimerick.cryptolootfrontend.dillon;

import java.math.BigDecimal;

public class Crypto{
    private String name;
    private BigDecimal exchangeRateToBase;

    public Crypto(String cryptoName){
        this.name = cryptoName;
        //sets a default exchange rate in case it isn't set using setExchangeRate, to avoid errors
        if(this.name=="Bitcoin"){
            exchangeRateToBase = new BigDecimal("1.0");
        }
        else{
            exchangeRateToBase = new BigDecimal("1.2");
        }
        exchangeRateToBase.setScale(8);
    }
    public String getName(){
        return this.name;
    }
    public BigDecimal getExchangeRate(){
        return exchangeRateToBase;
    }
    public void setExchangeRate(){
        //exchangeRateToBase = new BigDecimal(rate);
    }
    public void setExchangeRate(String rate){
        exchangeRateToBase = new BigDecimal(rate);
    }
}

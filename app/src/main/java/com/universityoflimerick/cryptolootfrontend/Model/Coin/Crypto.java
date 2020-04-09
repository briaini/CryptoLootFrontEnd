package com.universityoflimerick.cryptolootfrontend.Model.Coin;

import java.math.BigDecimal;

public class Crypto{
    /**The Crypto object is used to represent a Cryptocurrency
     * @param name is used to hold the name of the Crypto, such as "Bitcoin"
     * @param exchangeRateToBase holds the cryptos conversion rate against the base currency */
    private String name;
    private BigDecimal exchangeRateToBase;

    /**Constructor for Crypto object, all we need is a name, exchange rate can be set properly later
     * Gives a default exchange rate to avoid errors.*/
    public Crypto(String cryptoName){
        this.name = cryptoName;
        //sets a default exchange rate in case it isn't set using setExchangeRate, to avoid errors
        if(this.name=="Bitcoin") exchangeRateToBase = new BigDecimal("1.0");
        else exchangeRateToBase = new BigDecimal("1.2");

        exchangeRateToBase.setScale(10);
    }
    /**getter methods for name and exchange rate */
    public String getName(){
        return this.name;
    }
    public BigDecimal getExchangeRate(){
        return exchangeRateToBase;
    }

    /**setter methods for exchange rate, which will constantly be changing if an API that checks coin prices gets implemented */
    public void setExchangeRate(String rate){
        exchangeRateToBase = new BigDecimal(rate);
    }
}

package com.universityoflimerick.cryptolootfrontend.Model.Coin;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Coin{
    /** Each Coin container object must have a name e.g. bitcoinPurse, an address for performing transactions
     * @param name holds name of the Coin type of the object e.g Bitcoin
     * @param address holds the wallet address of the Coin object
     * @param baseCrypto holds the base currency as a Crypto object.
     * @param purseCrypto holds the type of Crypto object being stored in the Coin object
     * @param amountInBaseCrypto holds the amount of Coin in the object in base currency (BTC)
     * @param amountInPurseCrypto holds the amount of Coin in the object in Coin currency
     * @param exchangeRate holds the exchange rate of the Crypto against the base currency (BTC)
     * */
    private String name;
    private String address;
    private Crypto baseCrypto;
    private Crypto purseCrypto;
    private BigDecimal amountInBaseCrypto;
    private BigDecimal amountInPurseCrypto;
    private BigDecimal exchangeRate;

    /** Constructor for Coin object, sets variables and calculates amounts of coin in Bitcoin
     * */
    public Coin(String name, Crypto base, Crypto purse, String amountPurse){
        this.name = name;
        this.baseCrypto = base;
        this.purseCrypto= purse;
        this.amountInPurseCrypto= new BigDecimal(amountPurse);
        this.exchangeRate       = new BigDecimal(purseCrypto.getExchangeRate().toString());
        this.amountInBaseCrypto = divide(exchangeRate);
        this.address = "myAddress";
    }

    /**
     * getter() methods for Address, name, amountInPurseCoin, amountInBaseCoin, and exchange rate
     */
    public String getAddress() {
        return address;
    }

    public String getName(){
        return this.name;
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

    /**
     * Mathematical operations to be performed on the Coin objects values
     * add(BigDecimal) adds the value of the parameter to the Coins amount to the balance
     * subtract(BigDecimal) subtracts the value of the parameter from the Coins balance
     * multiply(BigDecimal) multiples the value of the parameter against the Coins balance and returns the new value
     * divide(BigDecimal) divides the balance of the coin by the value of the parameter and returns the new value
     */
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
        return this.amountInPurseCrypto.divide(temp, 10, RoundingMode.HALF_EVEN);
    }
    /**method that returns a description of the Coin object, useful for displaying information to user interface* */
    @NotNull
    public String toString(){
        String info = purseCrypto.getName() + "\nAmount:\t\t" + amountInPurseCrypto.toString() + "\nAmount in Base Crypto:\t" + amountInBaseCrypto.toString() + "\n";
        return info;
    }
    /**method that calculates the new balance converted to base currency* */
    private void refresh(){
        //calculate new balance in base coin
        this.amountInBaseCrypto = divide(exchangeRate);
    }
    /**transfer method is where most of the coin conversion logic takes place
     * @param receiving is the Coin object we are converting to
     * @param amount is the value of Coin that will be converted to our receiving coin
     *
     * different calculations are performed depending on the sending and receiving coin
     * Nothing will be executed if there isn't enough coins to make the transfer, it will return an error to the user
     * First we subtract the amount from the sending currency, before we calculate it's value
     * if the sending and receiving Coins are the same we don't need to perform any conversions
     * else if the sending coin bitcoin, and converting to a different coin, we multiply the amount by the receiving coins exchange rate
     * else if receiving coin is bitcoin, we convert the amount of different coin to bitcoin by dividing by the sending coins exchange rate
     * else if converting non btc -> non btc, its a bit more difficult. Have to convert the sending coin to BTC, then convert that BTC to the receiving currency
     *
     * After our new amount is calculated, it is added to the receiving coin's balance
     * */
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


}

package com.universityoflimerick.cryptolootfrontend.Model.Coin;

import com.universityoflimerick.cryptolootfrontend.Model.Coin.Coin;

public class CoinMemento {
    /**this class is a part of the Memento pattern, used to restore the state of object(s).
     * a instance of coinMemento is used to store a state of an object, which is Coin in our case.
     * @param BTC contains the amount of Bitcoin in the User's wallet
     * @param ETH contains the amount of Ethereum in the User's wallet
     * @param XRP contains the amount of Ripple in the User's wallet
     * @param LTC contains the amount of Litecoin in the User's wallet
     * @param state is an array that holds all our Coin values, so the state can be returned as a single object
     *  */
    private Coin BTC;
    private Coin ETH;
    private Coin XRP;
    private Coin LTC;
    private Coin [] state;

    /** Constructor for the memento objects, all we need is the four Coin objects so we know their values in that state*/
    public CoinMemento(Coin btc, Coin eth, Coin xrp, Coin ltc){
        state = new Coin [4];
        this.BTC = btc;
        this.ETH = eth;
        this.XRP = xrp;
        this.LTC = ltc;
    }

    /**returns the state of all coins in the form of a Coin Array */
    public Coin[] getState(){
        this.state[0]=this.BTC;
        this.state[1]=this.ETH;
        this.state[2]=this.XRP;
        this.state[3]=this.LTC;

        return state;
    }
}

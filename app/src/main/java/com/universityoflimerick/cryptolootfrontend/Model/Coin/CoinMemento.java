package com.universityoflimerick.cryptolootfrontend.Model.Coin;

import com.universityoflimerick.cryptolootfrontend.Model.Coin.Coin;

public class CoinMemento {
    private Coin BTC;
    private Coin ETH;
    private Coin XRP;
    private Coin LTC;
    private Coin [] state;

    public CoinMemento(Coin btc, Coin eth, Coin xrp, Coin ltc){
        state = new Coin [4];
        this.BTC = btc;
        this.ETH = eth;
        this.XRP = xrp;
        this.LTC = ltc;
    }

    public Coin[] getState(){
        this.state[0]=this.BTC;
        this.state[1]=this.ETH;
        this.state[2]=this.XRP;
        this.state[3]=this.LTC;

        return state;
    }
}

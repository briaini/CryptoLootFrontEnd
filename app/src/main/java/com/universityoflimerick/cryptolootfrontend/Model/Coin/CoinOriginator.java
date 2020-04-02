package com.universityoflimerick.cryptolootfrontend.Model.Coin;

public class CoinOriginator {
    private Coin[] state;

    public void setState(Coin btc, Coin eth, Coin xrp, Coin ltc){
        state = new Coin[4];
        this.state[0]=btc;
        this.state[1]=eth;
        this.state[2]=xrp;
        this.state[3]=ltc;
    }

    public Coin[] getState(){
        return state;
    }

    public CoinMemento saveStateToMemento(){
        return new CoinMemento(state[0], state[1], state[2], state[3]);
    }

    public void getStateFromMemento(CoinMemento memento){
        state = memento.getState();
    }
}

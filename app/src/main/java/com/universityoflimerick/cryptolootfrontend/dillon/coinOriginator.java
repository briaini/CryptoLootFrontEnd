package com.universityoflimerick.cryptolootfrontend.dillon;

public class coinOriginator {
    //state is actually an array of coins now
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

    public coinMemento saveStateToMemento(){
        return new coinMemento(state[0], state[1], state[2], state[3]);
    }

    public void getStateFromMemento(coinMemento memento){
        state = memento.getState();
    }
}

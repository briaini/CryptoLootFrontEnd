package com.universityoflimerick.cryptolootfrontend.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.universityoflimerick.cryptolootfrontend.R;
import com.universityoflimerick.cryptolootfrontend.Model.Coin.Coin;
import com.universityoflimerick.cryptolootfrontend.Model.Coin.Crypto;
import com.universityoflimerick.cryptolootfrontend.Model.Coin.CoinCareTaker;
import com.universityoflimerick.cryptolootfrontend.Model.Coin.CoinOriginator;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TradeActivity extends AppCompatActivity {
    /**TradeActivity allows users to convert their cryptocurrencies against each other
     * Contains different Coin objects, nicknamed Purses (e.g ripplePurse is used to hold users Ripple)
     * Crypto objects are passed to Coin constructors
     * Other various UI elements
     * @param myCoins arraylist holds all Coin objects
     * @param originator is used to create mementos which it uses in the future to undo operations and return previous states
     * @param careTaker is used to keep track of all statesm decides when to roll back data
     * @param tradeCounter increments/decrements each time a new state is created/restored
     * */
    Coin bitcoinPurse, ethereumPurse, ripplePurse, litecoinPurse;
    Coin[] temp;
    Crypto BTC, ETH, XRP, LTC;
    TextView disp;
    EditText amountET;
    Button trade, revert;
    ArrayList<Coin> myCoins;
    Spinner sendSpinner, receiveSpinner;
    CoinOriginator originator;
    CoinCareTaker careTaker;
    int tradeCounter=0;

    /**the onCreate() Initialises UI elements and creates variables
     * Alongside controlling the execution of the entire activity */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        sendSpinner     = findViewById(R.id.sendSpinner);
        receiveSpinner  = findViewById(R.id.receiveSpinner);
        amountET = findViewById(R.id.amountET);
    
        BTC = new Crypto("Bitcoin");
        ETH = new Crypto("Ethereum");
        XRP = new Crypto("Ripple");
        LTC = new Crypto("Litecoin");

        BTC.setExchangeRate("1");
        ETH.setExchangeRate("48.433494");
        XRP.setExchangeRate("36270.725139");
        LTC.setExchangeRate("166.525399");

        /**Creates coin objects with their amounts after their exchange rates have been set
         * Calls initializeMemento in order to save our first state */
        bitcoinPurse    = new Coin(BTC.getName(), BTC, BTC, "5.1234");
        ethereumPurse   = new Coin(ETH.getName(), BTC, ETH, "24.2");
        ripplePurse     = new Coin(XRP.getName(), BTC, XRP, "37588.67124");
        litecoinPurse   = new Coin(LTC.getName(), BTC, LTC, "50");

        initializeMemento();

        myCoins = new ArrayList<>();
        myCoins.add(bitcoinPurse);
        myCoins.add(ethereumPurse);
        myCoins.add(ripplePurse);
        myCoins.add(litecoinPurse);

        String [] names = new String[myCoins.size()];
        for(int i=0; i<myCoins.size(); i++){
            names[i] = myCoins.get(i).getName();
        }

        /**Initialise new ArrayAdapter for all available crypto types, so user can select what currencies they wish to convert
         * Sets the adapter to both the send and receive Spinner objects */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sendSpinner.setAdapter(adapter);
        receiveSpinner.setAdapter(adapter);

        disp = findViewById(R.id.display);
        refresh();

        /**initialise buttons and create their onClickListeners
         * If trading, state of coins will be created and stored using originator and caretaker objects before trade is made
         * If reverting, last state of coins will be retrieved using the originator and applied to our coins */
        trade = findViewById(R.id.trade);
        trade.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                originator.setState(new Coin("Bitcoin", BTC, BTC, bitcoinPurse.getBalanceInPurseCoin().toString()), new Coin("Ethereum", BTC, ETH, ethereumPurse.getBalanceInPurseCoin().toString()), new Coin("Ripple", BTC, XRP, ripplePurse.getBalanceInPurseCoin().toString()), new Coin("Litecoin", BTC, LTC, litecoinPurse.getBalanceInPurseCoin().toString()));
                careTaker.add(originator.saveStateToMemento());
                checkInput(sendSpinner.getSelectedItem().toString(), receiveSpinner.getSelectedItem().toString(), amountET.getText().toString() );
                tradeCounter++;
            }});

        revert = findViewById(R.id.revert);
        revert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(careTaker.getListSize() > 1){
                    originator.getStateFromMemento(careTaker.get(tradeCounter));
                    temp            = originator.getState();
                    bitcoinPurse    = temp[0];
                    ethereumPurse   = temp[1];
                    ripplePurse     = temp[2];
                    litecoinPurse   = temp[3];
                    tradeCounter--;

                    refresh();
                    Toast.makeText(TradeActivity.this, "TRANSACTION REVERSED", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TradeActivity.this, "NO TRADE TO REVERSE", Toast.LENGTH_SHORT).show();
                }

            }});
    }
    /**refresh method is used to update the UI elements with new values, and update our coins in our arraylist */
    public void refresh(){
        String temp = bitcoinPurse.toString();
        temp += ethereumPurse.toString();
        temp += ripplePurse.toString();
        temp += litecoinPurse.toString();
        disp.setText(temp);
        myCoins.set(0, bitcoinPurse);
        myCoins.set(1, ethereumPurse);
        myCoins.set(2, ripplePurse);
        myCoins.set(3, litecoinPurse);
    }
    /**The checkInput method is where the actual Trade takes place
     * We flick through our arraylist and if we can find the coins we wish to trade in our arrayList of coins,
     * we perform the conversion using the coins transfer() method, if unsuccessful an error message will be sent to the user*/
    public void checkInput(String send, String receive, String amount){
        boolean result=false;
        for (int i = 0; i < myCoins.size(); i++) {
            if (send.equals(myCoins.get(i).getName())) {
                for (int j = 0; j < myCoins.size(); j++) {
                    if (receive.equals(myCoins.get(j).getName())) {
                        result = myCoins.get(i).transfer(myCoins.get(j), new BigDecimal(amount));
                        refresh();
                        if(result){
                            //Not necessary to let them know when they've made a trade, they'll see the display update its info
                            //Toast.makeText(this, "SUCCESSFULLY TRADED " + myCoins.get(i).getName(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "NOT ENOUGH " + myCoins.get(i).getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }
    /**
     * Initialises the different objects required by the Memento pattern to save states and revert to previous states */
    private void initializeMemento(){
        temp = new Coin[4];
        originator = new CoinOriginator();
        careTaker = new CoinCareTaker();

        originator.setState(bitcoinPurse, ethereumPurse, ripplePurse, litecoinPurse);
        careTaker.add(originator.saveStateToMemento());
        System.out.println("First saved State: " + originator.getState());
    }
}

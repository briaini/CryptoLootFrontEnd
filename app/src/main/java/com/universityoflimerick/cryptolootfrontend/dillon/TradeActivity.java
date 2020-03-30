package com.universityoflimerick.cryptolootfrontend.dillon;

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

import java.math.BigDecimal;
import java.util.ArrayList;

public class TradeActivity extends AppCompatActivity {
    Coin bitcoinPurse, ethereumPurse, ripplePurse, litecoinPurse;
    Crypto BTC, ETH, XRP, LTC;
    TextView disp;
    EditText amountET;
    Button trade, revert;
    ArrayList<Coin> myCoins;
    Spinner sendSpinner, receiveSpinner;
    coinOriginator originator;
    coinCareTaker careTaker;
    Coin[] temp;

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

        bitcoinPurse    = new Coin(BTC.getName(), BTC, BTC, "5.1234");
        ethereumPurse   = new Coin(ETH.getName(), BTC, ETH, "2.2");
        ripplePurse     = new Coin(XRP.getName(), BTC, XRP, "37588.671246");
        litecoinPurse   = new Coin(LTC.getName(), BTC, LTC, "50");

        System.out.println("CALLING MEMENTO");
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sendSpinner.setAdapter(adapter);
        receiveSpinner.setAdapter(adapter);

        disp = findViewById(R.id.display);
        refresh();

        trade = findViewById(R.id.trade);
        trade.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //originator.setState(bitcoinPurse, ethereumPurse, ripplePurse, litecoinPurse);
                originator.setState(new Coin("Bitcoin", BTC, BTC, bitcoinPurse.getBalanceInPurseCoin().toString()), new Coin("Ethereum", BTC, ETH, ethereumPurse.getBalanceInPurseCoin().toString()), new Coin("Ripple", BTC, XRP, ripplePurse.getBalanceInPurseCoin().toString()), new Coin("Litecoin", BTC, LTC, litecoinPurse.getBalanceInPurseCoin().toString()));
                careTaker.add(originator.saveStateToMemento());
                checkInput(sendSpinner.getSelectedItem().toString(), receiveSpinner.getSelectedItem().toString(), amountET.getText().toString() );
            }});

        revert = findViewById(R.id.revert);
        revert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                originator.getStateFromMemento(careTaker.getLast());
                temp = originator.getState();
                bitcoinPurse = temp[0];
                ethereumPurse = temp[1];
                ripplePurse = temp[2];
                litecoinPurse = temp[3];
                refresh();
                System.out.println("RESTORED TO PREVIOUS STATE" + temp[0].toString());
                //checkInput(sendSpinner.getSelectedItem().toString(), receiveSpinner.getSelectedItem().toString(), amountET.getText().toString() );
            }});
    }

    public void refresh(){
        String temp = bitcoinPurse.toString();
        temp += ethereumPurse.toString();
        temp += ripplePurse.toString();
        temp += litecoinPurse.toString();
        disp.setText(temp);
        //System.out.println("REFRESHED");
    }
    public void checkInput(String send, String receive, String amount){
        boolean result=false;
        for (int i = 0; i < myCoins.size(); i++) {
            if (send.equals(myCoins.get(i).getName())) {
                for (int j = 0; j < myCoins.size(); j++) {
                    if (receive.equals(myCoins.get(j).getName())) {
                        //transfer(myCoins.get(i), myCoins.get(j), new BigDecimal(amount));
                        result = myCoins.get(i).transfer(myCoins.get(j), new BigDecimal(amount));
                        refresh();
                        if(result){
                            //Toast.makeText(this, "SUCCESSFULLY TRADED " + myCoins.get(i).getName(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "NOT ENOUGH " + myCoins.get(i).getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }
    private void initializeMemento(){
        temp = new Coin[4];
        originator = new coinOriginator();
        careTaker = new coinCareTaker();

        originator.setState(bitcoinPurse, ethereumPurse, ripplePurse, litecoinPurse);
        //originator.setState("State #2");
        careTaker.add(originator.saveStateToMemento());
        System.out.println("First saved State: " + originator.getState());
    }

}

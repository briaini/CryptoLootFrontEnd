package com.universityoflimerick.cryptolootfrontend.dillon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.universityoflimerick.cryptolootfrontend.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CoinTestActivity extends AppCompatActivity {
    Coin bitcoinPurse, ethereumPurse, ripplePurse, litecoinPurse;
    Crypto BTC, ETH, XRP, LTC;
    TextView disp;
    EditText sendingET, receivingET, amountET;
    Button ref, trade;
    ArrayList<Coin> myCoins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_test);

        sendingET = findViewById(R.id.sendingET);
        receivingET = findViewById(R.id.receivingET);
        amountET = findViewById(R.id.amountET);
    
        BTC = new Crypto("Bitcoin");
        ETH = new Crypto("Ethereum");
        XRP = new Crypto("Ripple");
        LTC = new Crypto("Litecoin");

        BTC.setExchangeRate("1");
        ETH.setExchangeRate("48.723470");
        XRP.setExchangeRate("37588.671246");
        LTC.setExchangeRate("166.917094");

        bitcoinPurse = new Coin(BTC.getName(), BTC, BTC, "5.1234");
        ethereumPurse = new Coin(ETH.getName(), BTC, ETH, "2.2");
        ripplePurse = new Coin(XRP.getName(), BTC, XRP, "37588.671246");
        litecoinPurse = new Coin(LTC.getName(), BTC, LTC, "50");

        myCoins = new ArrayList<>();
        myCoins.add(bitcoinPurse);
        myCoins.add(ethereumPurse);
        myCoins.add(ripplePurse);
        myCoins.add(litecoinPurse);


        disp = findViewById(R.id.display);
        refresh();

        trade = findViewById(R.id.trade);
        trade.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkInput(sendingET.getText().toString(), receivingET.getText().toString(), amountET.getText().toString());
            }});

        ref = findViewById(R.id.ref);
        ref.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                refresh();
            }
        });

    }
    public void transfer(Coin sending, Coin receiving, BigDecimal amount){
        if(sending.getBalanceInPurseCoin().compareTo(amount)==0 || sending.getBalanceInPurseCoin().compareTo(amount)==1 ){
            sending.subtract(amount);
            if(sending.getName().equals(receiving.getName())){
                //do nothing if trying to convert the the same crypto
                //amount = amount;
            }
            else if(sending.getName().equals("Bitcoin")){
                amount = amount.multiply(receiving.getExchangeRate());
            }
            else if(receiving.getName().equals("Bitcoin")){
                amount = amount.divide(sending.getExchangeRate(), 8, RoundingMode.HALF_EVEN);
            }

            System.out.println("NEW AMOUNT IS " + amount.toString());
            receiving.add(amount);
            System.out.println("BALANCE AFTER ADD IS " + receiving.getBalanceInPurseCoin());
            refresh();
        } else{
            Toast.makeText(this, "NOT ENOUGH COIN", Toast.LENGTH_SHORT).show();
        }

    }
    public void refresh(){
        String temp = bitcoinPurse.toString();
        temp += ethereumPurse.toString();
        temp += ripplePurse.toString();
        temp += litecoinPurse.toString();
        disp.setText(temp);
        System.out.println("REFRESHED");
    }
    public void checkInput(String send, String receive, String amount){
        for (int i = 0; i < myCoins.size(); i++) {
            if (send.equals(myCoins.get(i).getName())) {
                System.out.println("FOUND SENDING COIN!!");
                for (int j = 0; j < myCoins.size(); j++) {
                    if (receive.equals(myCoins.get(j).getName())) {
                        System.out.println("FOUND RECEIVING COIN!!");
                        transfer(myCoins.get(i), myCoins.get(j), new BigDecimal(amount));
                    }
                }
            }
        }
    }
}

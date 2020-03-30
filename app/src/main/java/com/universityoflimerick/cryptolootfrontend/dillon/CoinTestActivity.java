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
import java.math.RoundingMode;
import java.util.ArrayList;

public class CoinTestActivity extends AppCompatActivity {
    Coin bitcoinPurse, ethereumPurse, ripplePurse, litecoinPurse;
    Crypto BTC, ETH, XRP, LTC;
    TextView disp;
    EditText amountET;
    Button trade;
    ArrayList<Coin> myCoins;
    Spinner sendSpinner, receiveSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_test);

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
                checkInput(sendSpinner.getSelectedItem().toString(), receiveSpinner.getSelectedItem().toString(), amountET.getText().toString() );
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
    /*public void transfer(Coin sending, Coin receiving, BigDecimal amount){
        if(sending.getBalanceInPurseCoin().compareTo(amount)==0 || sending.getBalanceInPurseCoin().compareTo(amount)==1 ){
            sending.subtract(amount);
            if(sending.getName().equals(receiving.getName())){
                //do nothing if trying to convert the the same crypto
            }
            //if converting BTC -> Other, multiply amount by receiving coin exchange rate
            else if(sending.getName().equals("Bitcoin")){
                System.out.println("BTC " + amount.toString() + " BEING CONVERTED TO");
                amount = amount.multiply(receiving.getExchangeRate());
                System.out.println("Amount after conversion to " + receiving.getName() + " is " + amount.toString());
            }
            //if converting non BTC -> BTC, divide sending coin amount by exchange rate to get amount in BTC
            else if(receiving.getName().equals("Bitcoin")){
                System.out.println(sending.getName() +" amount " + amount.toString() + " BEING CONVERTED TO");
                amount = amount.divide(sending.getExchangeRate(), 10, RoundingMode.HALF_EVEN);
                System.out.println(receiving.getName() + " amount " + amount.toString());
            }
            //converting non BTC to non BTC
            //Must convert from sending -> BTC, then BTC -> receiving
            else {
                System.out.println(sending.getName() + " Amount being converted is " + amount.toString());
                amount = amount.divide(sending.getExchangeRate(), 10, RoundingMode.HALF_EVEN);
                System.out.println("After convert to BTC is " + amount.toString());
                amount = amount.multiply(receiving.getExchangeRate());
                System.out.println("After convert to Target is " + amount.toString());
            }
            receiving.add(amount);
            refresh();
        } else{
            Toast.makeText(this, "NOT ENOUGH " + sending.getName(), Toast.LENGTH_SHORT).show();
        }

    }*/
}

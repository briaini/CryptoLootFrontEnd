package com.universityoflimerick.cryptolootfrontend.adam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.universityoflimerick.cryptolootfrontend.R;
import com.universityoflimerick.cryptolootfrontend.adam.User.User;
import com.universityoflimerick.cryptolootfrontend.adam.User.UserFactory;
import com.universityoflimerick.cryptolootfrontend.adam.command.ActionInvoker;
import com.universityoflimerick.cryptolootfrontend.adam.command.PayCoin;
import com.universityoflimerick.cryptolootfrontend.adam.command.RequestCoin;
import com.universityoflimerick.cryptolootfrontend.brian.MainActivity;
import com.universityoflimerick.cryptolootfrontend.dillon.Coin;
import com.universityoflimerick.cryptolootfrontend.dillon.Crypto;

import java.math.BigDecimal;

public class PaymentActivity extends AppCompatActivity {

    TextView textView1, textViewAmount, textViewAddress;
    Spinner spinner;
    EditText amountInput, addressInput;
    Button payButton, requestButton, executeButton, cancelButton;
    ActionInvoker actionInvoker;

    /**
     * UI Payment screen activity.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        textView1 = findViewById(R.id.payment_textview);
        textViewAmount = findViewById(R.id.payment_textview_amount);
        textViewAddress = findViewById(R.id.payment_textview_address);
        spinner = findViewById(R.id.payment_spinner);
        amountInput = findViewById(R.id.payment_amount_input);
        addressInput = findViewById(R.id.payment_address_input);
        payButton = findViewById(R.id.payment_pay_button);
        cancelButton = findViewById(R.id.payment_cancel_button);
        requestButton = findViewById(R.id.request_button);
        executeButton = findViewById(R.id.execute_button);
        actionInvoker = new ActionInvoker();

        payButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pay();
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                request();
            }
        });

        executeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actionInvoker.executeAction();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancel();
            }
        });

    }

    /**
     * Adds a payment command to the actionInvoker.
     */
    public void pay(){
        String amount = amountInput.getText().toString();
        String address = addressInput.getText().toString();
        String selectedCoin = spinner.getSelectedItem().toString();
        User user = createUser();
        Coin userCoin = user.matchCoin(selectedCoin);
        BigDecimal decimalAmount = new BigDecimal(amount.replaceAll(",", ""));
        PayCoin payCoin = new PayCoin(user, address, decimalAmount, userCoin);
        actionInvoker.addAction(payCoin);
    }

    /**
     * Adds a request command to the actionInvoker.
     */
    public void request(){
        String amount = amountInput.getText().toString();
        String address = addressInput.getText().toString();
        String selectedCoin = spinner.getSelectedItem().toString();
        User user = createUser();
        Coin userCoin = user.matchCoin(selectedCoin);
        BigDecimal decimalAmount = new BigDecimal(amount.replaceAll(",", ""));
        RequestCoin reqCoin = new RequestCoin(user, address, decimalAmount, userCoin);
        actionInvoker.addAction(reqCoin);
    }

    /**
     * Object instantiation
     * @return
     */
    public User createUser(){
        UserFactory uf = new UserFactory();
        User user = uf.getUser("regular");
        Crypto BTC = new Crypto("Bitcoin");
        BTC.setExchangeRate("1");
        Coin bitcoinPurse = new Coin(BTC.getName(), BTC, BTC, "5.1234");
        user.addCoin(bitcoinPurse);
        return user;
    }

    /**
     * return to previous activity.
     */
    public void cancel(){
        showNextActivity();
    }

    private void showNextActivity() {
        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

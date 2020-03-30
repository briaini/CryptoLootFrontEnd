package com.universityoflimerick.cryptolootfrontend.adam.command;

import android.net.TrafficStats;

import com.universityoflimerick.cryptolootfrontend.adam.User.User;
import com.universityoflimerick.cryptolootfrontend.ben.visitor.BtcTransaction;
import com.universityoflimerick.cryptolootfrontend.ben.visitor.EthTransaction;
import com.universityoflimerick.cryptolootfrontend.ben.visitor.LtcTransaction;
import com.universityoflimerick.cryptolootfrontend.ben.visitor.RipTransaction;
import com.universityoflimerick.cryptolootfrontend.ben.visitor.TransactionCostVisitor;
import com.universityoflimerick.cryptolootfrontend.ben.visitor.TransactionCostVisitorImpl;
import com.universityoflimerick.cryptolootfrontend.ben.visitor.TransactionElement;

import java.math.BigDecimal;

public class PayCoin implements CoinAction {
    private User user;
    private String address;
    private BigDecimal amount;
    private String coin;

    //Hard coded values for transaction type
    private String message = "Message being sent with transaction";
    private boolean fastTransaction = true;
    private boolean safeTransaction = true;

    public PayCoin(User user, String address, BigDecimal amount, String coin){
        this.user = user;
        this.address = address;
        this.amount = amount;
        this.coin = coin;
    }

    public void execute(){
        Double transactionFee = getTransactionFees();
        user.pay(this.address, this.amount, this.coin);
    }

    private Double getTransactionFees() {
        TransactionCostVisitor visitor = new TransactionCostVisitorImpl(user);
        double fee = 0.0;
        double amountSending = amount.doubleValue();
        TransactionElement element;

        if(coin.matches("Bitcoin")){
            element = new BtcTransaction("", address, amountSending, fastTransaction);
            fee = element.accept(visitor);
        }
        else if(coin.matches("Ethereum")){
            element = new EthTransaction("",address, amountSending, message);
            fee = element.accept(visitor);
        }
        else if(coin.matches("Litecoin")){
            element = new LtcTransaction("", address, amountSending);
            fee = element.accept(visitor);
        }
        else if(coin.matches("Ripple")){
            element = new RipTransaction("", address, amountSending, safeTransaction);
            fee = element.accept(visitor);
        }

        return fee;
    }

}

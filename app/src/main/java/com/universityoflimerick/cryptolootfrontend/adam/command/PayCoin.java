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
import com.universityoflimerick.cryptolootfrontend.dillon.Coin;

import java.math.BigDecimal;

public class PayCoin implements CoinAction {
    private User user;
    private String address;
    private BigDecimal amount;
    private Coin coin;

    //Hard coded values for transaction type
    private String message = "Message being sent with transaction";
    private boolean fastTransaction = true;
    private boolean safeTransaction = true;

    public PayCoin(User user, String address, BigDecimal amount, Coin coin){
        this.user = user;
        this.address = address;
        this.amount = amount;
        this.coin = coin;
    }

    public void execute(){
        BigDecimal finalAmount = getTransactionFees();
        user.pay(this.address, finalAmount, this.coin);
    }

    private BigDecimal getTransactionFees() {
        TransactionCostVisitor visitor = new TransactionCostVisitorImpl(user);
        double fee = 0.0;
        double amountSending = amount.doubleValue();
        TransactionElement element;

        if((coin.getName()).matches("Bitcoin")){
            element = new BtcTransaction(coin.getAddress(), address, amountSending, fastTransaction);
            fee = element.accept(visitor);
        }
        else if(coin.getName().matches("Ethereum")){
            element = new EthTransaction(coin.getAddress(),address, amountSending, message);
            fee = element.accept(visitor);
        }
        else if(coin.getName().matches("Litecoin")){
            element = new LtcTransaction(coin.getAddress(), address, amountSending);
            fee = element.accept(visitor);
        }
        else if(coin.getName().matches("Ripple")){
            element = new RipTransaction(coin.getAddress(), address, amountSending, safeTransaction);
            fee = element.accept(visitor);
        }

        BigDecimal transaction = new BigDecimal(fee);
        BigDecimal newAmount = (this.amount).subtract(transaction);
        return newAmount;
    }

}

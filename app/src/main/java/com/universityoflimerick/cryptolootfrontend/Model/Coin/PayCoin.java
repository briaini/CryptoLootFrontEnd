package com.universityoflimerick.cryptolootfrontend.Model.Coin;

import com.universityoflimerick.cryptolootfrontend.Model.User.User;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.BtcTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.EthTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.LtcTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.RipTransaction;
import com.universityoflimerick.cryptolootfrontend.Utils.command.CoinAction;
import com.universityoflimerick.cryptolootfrontend.Utils.visitor.TransactionCostVisitor;
import com.universityoflimerick.cryptolootfrontend.Utils.visitor.TransactionCostVisitorImpl;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.TransactionElement;

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

    /**
     * PayCoin object allowing users to make payment with their coins.
     * @param user
     * @param address
     * @param amount
     * @param coin
     */
    public PayCoin(User user, String address, BigDecimal amount, Coin coin){
        this.user = user;
        this.address = address;
        this.amount = amount;
        this.coin = coin;
    }

    /**
     * Call the users pay method to make the payment
     */
    public void execute(){
        BigDecimal finalAmount = getTransactionFees();
        user.pay(this.address, finalAmount, this.coin);
    }

    /**
     * Gets the transaction fee of the payment.
     * @return
     */
    public BigDecimal getTransactionFees() {
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

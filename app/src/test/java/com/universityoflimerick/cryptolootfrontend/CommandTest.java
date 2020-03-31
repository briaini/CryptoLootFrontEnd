package com.universityoflimerick.cryptolootfrontend;

import com.universityoflimerick.cryptolootfrontend.adam.User.User;
import com.universityoflimerick.cryptolootfrontend.adam.User.UserFactory;
import com.universityoflimerick.cryptolootfrontend.adam.command.ActionInvoker;
import com.universityoflimerick.cryptolootfrontend.adam.command.CoinAction;
import com.universityoflimerick.cryptolootfrontend.adam.command.PayCoin;
import com.universityoflimerick.cryptolootfrontend.adam.command.RequestCoin;
import com.universityoflimerick.cryptolootfrontend.dillon.Coin;
import com.universityoflimerick.cryptolootfrontend.dillon.Crypto;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static junit.framework.TestCase.assertEquals;

public class CommandTest {

    private ActionInvoker actionInvoker;
    private CoinAction payCoin;
    private User user;
    private Coin coin;
    private PayCoin transaction;

    /**
     * Setup method used to instantiate the objects needed for the test.
     * Also need to set up seperate transaction obejects to calculate the fee to compare to in the test.
     */
    @Before
    public void setup(){
        actionInvoker = new ActionInvoker();
        UserFactory userFactory = new UserFactory();
        user = userFactory.getUser("regular");
        Crypto BTC = new Crypto("Bitcoin");
        coin = new Coin(BTC.getName(), BTC, BTC, "5.0000");
        user.addCoin(coin);
        BigDecimal amount = new BigDecimal("2.000");
        payCoin = new PayCoin(user, "abc123", amount, coin);
        User transactionUser = userFactory.getUser("regular");
        BigDecimal transactionAmount = new BigDecimal("2");
        transaction = new PayCoin(transactionUser, "", transactionAmount, coin);
    }

    /**
     * Adds the pay action to the invoker list and executes all actions on the list.
     * Compares the new amount the user has of that particular coin.
     */
    @Test
    public void command_test_payment(){
        actionInvoker.addAction(payCoin);
        actionInvoker.executeAction();
        Coin userCoin = user.matchCoin(coin.getName());
        BigDecimal coinAmount = userCoin.getBalanceInPurseCoin();
        BigDecimal transactionAmount = transaction.getTransactionFees();
        BigDecimal originalAmount = new BigDecimal("5.000");
        BigDecimal actualAmount = originalAmount.subtract(transactionAmount);

        assertEquals(userCoin.getBalanceInPurseCoin(), actualAmount);
    }
}

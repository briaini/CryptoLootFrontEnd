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
import static org.junit.Assert.assertNotEquals;

public class CommandTest {

    private ActionInvoker actionInvoker;
    private CoinAction payCoin;
    private User user;
    private Coin coin;
    private PayCoin transaction;
    private BigDecimal coinAmount;
    private Coin userCoin;
    private BigDecimal actualAmount;

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
        actionInvoker.addAction(payCoin);
        actionInvoker.executeAction();
        userCoin = user.matchCoin(coin.getName());
        BigDecimal coinAmount = userCoin.getBalanceInPurseCoin();
        BigDecimal transAmount = transaction.getTransactionFees();
        BigDecimal originalAmount = new BigDecimal("5.000");
        actualAmount = originalAmount.subtract(transAmount);
    }

    /**
     * Adds the pay action to the invoker list and executes all actions on the list.
     * Compares the new amount the user has of that particular coin.
     */
    @Test
    public void command_payment_test(){
        assertEquals(userCoin.getBalanceInPurseCoin(), actualAmount);
    }

    /**
     * A test to check that the values in the purse are different than an incorrect value.
     */
    @Test
    public void command_payment_incorrect_test(){
        BigDecimal incorrectAmount = new BigDecimal(1.12);
        assertNotEquals(userCoin.getBalanceInPurseCoin(), incorrectAmount);
    }
}

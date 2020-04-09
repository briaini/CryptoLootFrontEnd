package com.universityoflimerick.cryptolootfrontend;

import com.universityoflimerick.cryptolootfrontend.Model.Coin.Coin;
import com.universityoflimerick.cryptolootfrontend.Model.Coin.Crypto;
import com.universityoflimerick.cryptolootfrontend.Model.Coin.PayCoin;
import com.universityoflimerick.cryptolootfrontend.Model.User.User;
import com.universityoflimerick.cryptolootfrontend.Utils.factory.UserFactory;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VisitorTest {

    private BigDecimal roundedTest, roundedAmount;

    /**
     * Instantiates objects for the test to use.
     */
    @Before
    public void setup(){
        UserFactory userFactory = new UserFactory();
        User user = userFactory.getUser("regular");
        Crypto BTC = new Crypto("Bitcoin");
        Coin coin = new Coin(BTC.getName(), BTC, BTC, "4.0000");
        user.addCoin(coin);
        BigDecimal amount = new BigDecimal("1.000");
        PayCoin payCoin = new PayCoin(user, "abc123", amount, coin);
        double arithmetic = 1.000 * .02;
        double newAmount = 1.000 - arithmetic;
        BigDecimal testAmount = payCoin.getTransactionFees();
        BigDecimal actualAmount = new BigDecimal(newAmount);
        BigDecimal roundedTest = testAmount.round(new MathContext(10, RoundingMode.HALF_UP));
        BigDecimal roundedAmount = actualAmount.round(new MathContext(10, RoundingMode.HALF_UP));
    }

    /**
     * Calculates the transaction fee of a transaction of 1 bitcoin
     * Transaction fee is 1% which it compares it to.
     */
    @Test
    public void visitor_test_correct(){
        assertEquals(roundedTest, roundedAmount);
    }

    /**
     * Test an incorrect amount.
     */
    @Test
    public void visitor_test_incorrect(){
        BigDecimal wrongAmount = new BigDecimal(1.1);
        assertNotEquals(roundedTest, wrongAmount);
    }
}

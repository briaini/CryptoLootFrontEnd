package com.universityoflimerick.cryptolootfrontend;

import com.universityoflimerick.cryptolootfrontend.dillon.Coin;
import com.universityoflimerick.cryptolootfrontend.dillon.Crypto;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MoneyTest {

    private BigDecimal roundedAmount, roundedTest;

    /**
     * Instantiate objects for tests.
     * Transfer 2 bitcoin to ethereum.
     */
    @Before
    public void setup(){

        Crypto BTC = new Crypto("Bitcoin");
        BTC.setExchangeRate("1");
        Coin bitcoin = new Coin(BTC.getName(), BTC, BTC, "5.0000");

        Crypto ETH = new Crypto("Ethereum");
        ETH.setExchangeRate("48.433494");
        Coin ethereum = new Coin(ETH.getName(), BTC, ETH, "0.0");

        BigDecimal transferAmount = new BigDecimal("2.000");

        bitcoin.transfer(ethereum, transferAmount);
        BigDecimal testAmount = new BigDecimal(96.866988);
        roundedAmount = testAmount.round(new MathContext(10, RoundingMode.HALF_UP));
        roundedTest = (ethereum.getBalanceInPurseCoin()).round(new MathContext(10, RoundingMode.HALF_UP));
    }

    /**
     * Test that the transfer results are correct.
     */
    @Test
    public void money_correct_transfer_test(){
        assertEquals(roundedTest, roundedAmount);
    }

    /**
     * Test that incorrect amounts are not equal to transfer amount.
     */
    @Test
    public void money_incorrect_transfer_test(){
        BigDecimal incorrectAmount = new BigDecimal(1);
        assertNotEquals(roundedTest, incorrectAmount);
    }
}

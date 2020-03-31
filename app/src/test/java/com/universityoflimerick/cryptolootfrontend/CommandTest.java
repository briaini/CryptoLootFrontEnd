package com.universityoflimerick.cryptolootfrontend;

import com.universityoflimerick.cryptolootfrontend.Model.User.User;
import com.universityoflimerick.cryptolootfrontend.Utils.factory.UserFactory;
import com.universityoflimerick.cryptolootfrontend.Utils.command.ActionInvoker;
import com.universityoflimerick.cryptolootfrontend.Utils.command.CoinAction;
import com.universityoflimerick.cryptolootfrontend.Model.Coin.Coin;
import com.universityoflimerick.cryptolootfrontend.Model.Coin.Crypto;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CommandTest {

    ActionInvoker actionInvoker;
    CoinAction payCoin;
    CoinAction requestCoin;
    UserFactory userFactory;
    User user;
    Coin coin;
    Crypto BTC;


    @Before
    public void setup(){
        actionInvoker = new ActionInvoker();
        userFactory = new UserFactory();
        user = userFactory.getUser("regular");
        BTC = new Crypto("Bitcoin");
        coin = new Coin(BTC.getName(), BTC, BTC, "5.1234");
        user.addCoin(coin);
        BigDecimal amount = new BigDecimal(123.321);
        //payCoin = new PayCoin(user, "abc123", amount, "Bitcoin");
        //requestCoin = new RequestCoin(user, "abc123", amount, "Bitcoin");
    }

    @Test
    public void command_test(){

    }
}

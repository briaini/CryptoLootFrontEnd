package com.universityoflimerick.cryptolootfrontend.adam.command;

import java.util.ArrayList;
import java.util.List;

public class ActionInvoker {
    private List<CoinAction> coinActionsList = new ArrayList<CoinAction>();

    /**
     * Adds either a payment or request to coinActionList.
     * @param coinAction
     */
    public void addAction(CoinAction coinAction){
        coinActionsList.add(coinAction);
    }

    /**
     * Loops through coinActionList and executes all commands inside of it.
     */
    public void executeAction(){
        for(CoinAction coinAction: coinActionsList){
            coinAction.execute();
        }
        coinActionsList.clear();
    }
}

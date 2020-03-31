package com.universityoflimerick.cryptolootfrontend.Utils.command;

import java.util.ArrayList;
import java.util.List;

public class ActionInvoker {
    private List<CoinAction> coinActionsList = new ArrayList<CoinAction>();

    public void addAction(CoinAction coinAction){
        coinActionsList.add(coinAction);
    }

    public void executeAction(){
        for(CoinAction coinAction: coinActionsList){
            coinAction.execute();
        }
        coinActionsList.clear();
    }
}

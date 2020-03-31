package com.universityoflimerick.cryptolootfrontend.Utils.visitor;

import com.universityoflimerick.cryptolootfrontend.Model.Transaction.BtcTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.EthTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.LtcTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.RipTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.User.User;

public class TransactionCostVisitorImpl implements TransactionCostVisitor{

    private User loggedInUser;

    public TransactionCostVisitorImpl(User user){
        loggedInUser = user;
    }

    @Override
    public double visit(BtcTransaction btc)
    {
        double transactionCost;
        //check logged in user
        //if premium them apply transaction discount
        if(loggedInUser.getType().matches("Premium")){
            if(btc.isFastTransfer()){
                //1% transaction cost on bitcoin fast transfers
                transactionCost = btc.getSendAmount() * 0.01;
            }
            else {
                //0.05% transaction cost on Bitcoin for premium users
                transactionCost = btc.getSendAmount() * 0.005;
            }
        }
        else {
            if (btc.isFastTransfer()) {
                //2% transaction fee for free users using bitcoin fast transfer
                transactionCost = btc.getSendAmount() * 0.02;
            } else {
                //apply 1.25% transaction fee for free users
                transactionCost = btc.getSendAmount() * 0.0125;
            }
        }
        return transactionCost;
    }

    @Override
    public double visit(EthTransaction eth)
    {
        double transactionCost;
        //check logged in user
        //if premium them apply transaction discount
        if(loggedInUser.getType().matches("Premium")){
            if(!eth.getSendMessage().matches("")){
                //0.8% transaction cost on ethereum transfer with message attached
                transactionCost = eth.getSendAmount() * 0.008;
            }
            else {
                //0.02% transaction cost on premium users sending eth without message
                transactionCost = eth.getSendAmount() * 0.002;
            }
        }

        else {
            if(!eth.getSendMessage().matches("")){
                //2.5% transaction cost on ethereum transfer with message attached for free users
                transactionCost = eth.getSendAmount() * 0.025;
            }
            else {
                //apply 2% transaction fee for free users
                transactionCost = eth.getSendAmount() * 0.02;
            }
        }
        return transactionCost;
    }

    @Override
    public double visit(RipTransaction rip)
    {
        double transactionCost;

        //if premium user them apply transaction discount
        if(loggedInUser.getType().matches("Premium")){
            if(rip.isEncryptedTransaction()){
                //0.5% transaction fee on encrypting transaction for premium users
                transactionCost = rip.getSendAmount() * 0.005;
            }
            else {
                //Free Transactions Cost for Premium users sending normal ripple transactions
                transactionCost = 0;
            }
        }
        else{
            if(rip.isEncryptedTransaction()){
                //2% transaction fee on encrypting transaction for free users
                transactionCost = rip.getSendAmount() * 0.02;
            }
            else{
                //1.5% transaction fee on ripple for free users
                transactionCost = rip.getSendAmount() * 0.015;
            }
        }
        return transactionCost;
    }

    @Override
    public double visit(LtcTransaction ltc)
    {
        double transactionCost;

        //if premium user them apply transaction discount
        if(loggedInUser.getType().matches("Premium")){
            //free transaction fee on ltc for premium users
           transactionCost = 0;
        }
        else{
            //2% transaction fee on ltc for free users
           transactionCost = ltc.getSendAmount() * 0.02;
        }
        return transactionCost;
    }

}


package com.universityoflimerick.cryptolootfrontend.Utils.visitor;

import com.universityoflimerick.cryptolootfrontend.Model.Transaction.BtcTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.EthTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.LtcTransaction;
import com.universityoflimerick.cryptolootfrontend.Model.Transaction.RipTransaction;

public interface TransactionCostVisitor
{
    double visit(BtcTransaction btc);
    double visit(EthTransaction eth);
    double visit(RipTransaction rip);
    double visit(LtcTransaction ltc);
}
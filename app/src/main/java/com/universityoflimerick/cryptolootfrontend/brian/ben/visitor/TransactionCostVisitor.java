package com.universityoflimerick.cryptolootfrontend.brian.ben.visitor;

interface TransactionCostVisitor
{
    double visit(BtcTransaction btc);
    double visit(EthTransaction eth);
    double visit(RipTransaction rip);
    double visit(LtcTransaction ltc);
}
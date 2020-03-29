package com.universityoflimerick.cryptolootfrontend.ben.visitor;

interface TransactionCostVisitor
{
    double visit(BtcTransaction btc);
    double visit(EthTransaction eth);
    double visit(RipTransaction rip);
    double visit(LtcTransaction ltc);
}
package com.universityoflimerick.cryptolootfrontend.ben.visitor;

public interface TransactionElement {
    public double accept(TransactionCostVisitor visitor);
}
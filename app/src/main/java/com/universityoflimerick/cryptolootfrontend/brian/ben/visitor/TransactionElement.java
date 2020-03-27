package com.universityoflimerick.cryptolootfrontend.brian.ben.visitor;

public interface TransactionElement {
    public double accept(TransactionCostVisitor visitor);
}
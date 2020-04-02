package com.universityoflimerick.cryptolootfrontend.Model.Transaction;

import com.universityoflimerick.cryptolootfrontend.Utils.visitor.TransactionCostVisitor;

public interface TransactionElement {
    public double accept(TransactionCostVisitor visitor);
}
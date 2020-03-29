package com.universityoflimerick.cryptolootfrontend.ben.visitor;

public class RipTransaction implements TransactionElement {
    private String transactionId;
    private String sendAddress;
    private String receiveAdress;
    private double sendAmount;
    private boolean encryptedTransaction;

    public RipTransaction(String sendAddress, String receiveAdress, double sendAmount, boolean encryptedTransaction ){
        this.transactionId = "***randomString***";
        this.receiveAdress = receiveAdress;
        this.sendAddress = sendAddress;
        this.sendAmount = sendAmount;
        this.encryptedTransaction = encryptedTransaction;
    }

    public boolean isEncryptedTransaction() {
        return encryptedTransaction;
    }

    public void setEncryptedTransaction(boolean encryptedTransaction) {
        this.encryptedTransaction = encryptedTransaction;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getReceiveAdress() {
        return receiveAdress;
    }

    public void setReceiveAdress(String receiveAdress) {
        this.receiveAdress = receiveAdress;
    }

    public double getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(double sendAmount) {
        this.sendAmount = sendAmount;
    }

    @Override
    public double accept(TransactionCostVisitor visitor) {
        return visitor.visit(this);
    }
}

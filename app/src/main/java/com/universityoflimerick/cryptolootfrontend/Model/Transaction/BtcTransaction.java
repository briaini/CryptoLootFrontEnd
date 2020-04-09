package com.universityoflimerick.cryptolootfrontend.Model.Transaction;

import com.universityoflimerick.cryptolootfrontend.Utils.visitor.TransactionCostVisitor;

public class BtcTransaction implements TransactionElement {
    private String transactionId;
    private String sendAddress;
    private String receiveAdress;
    private double sendAmount;
    private boolean fastTransfer;

    public BtcTransaction(String sendAddress, String receiveAdress, double sendAmount, boolean fastTransfer){
        this.transactionId = "***randomString***";
        this.receiveAdress = receiveAdress;
        this.sendAddress = sendAddress;
        this.sendAmount = sendAmount;
        this.fastTransfer = fastTransfer;
    }

    /**
     * accept method calls the visit method for a specified visitor object
     * @return double which represents the transaction cost
     * @param visitor is the object that we call the visit method on and pass an instance of this BtcTransaction class
     */
    @Override
    public double accept(TransactionCostVisitor visitor)
    {
        return visitor.visit(this);
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

    public boolean isFastTransfer() {
        return fastTransfer;
    }

    public void setFastTransfer(boolean fastTransfer) {
        this.fastTransfer = fastTransfer;
    }

}

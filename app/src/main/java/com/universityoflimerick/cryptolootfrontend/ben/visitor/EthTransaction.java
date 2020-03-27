package com.universityoflimerick.cryptolootfrontend.ben.visitor;

public class EthTransaction implements TransactionElement{
    private String transactionId;
    private String sendAddress;
    private String receiveAdress;
    private double sendAmount;
    private String sendMessage;

    public EthTransaction(String sendAddress, String receiveAdress, double sendAmount, String sendMessage){
        this.transactionId = "***randomString***";
        this.receiveAdress = receiveAdress;
        this.sendAddress = sendAddress;
        this.sendAmount = sendAmount;
        this.sendMessage = sendMessage;
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

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public double accept(TransactionCostVisitor visitor) {
        return visitor.visit(this);
    }
}

package com.example.keabank.Model;

public class Transactions {
    private String transactionName;
    private String dopositBeforeTransaction;
    private String dopositAfterTransaction;
    private String date;
    private double transactionAmmount;

    public Transactions() {

    }

    public Transactions(String transactionName, double transactionAmmount, String dopositAfterTransaction, String date) {
        this.transactionName = transactionName;
        this.transactionAmmount = transactionAmmount;
        this.dopositAfterTransaction = dopositAfterTransaction;
        this.date = date;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getDopositBeforeTransaction() {
        return dopositBeforeTransaction;
    }

    public void setDopositBeforeTransaction(String dopositBeforeTransaction) {
        this.dopositBeforeTransaction = dopositBeforeTransaction;
    }

    public String getDopositAfterTransaction() {
        return dopositAfterTransaction;
    }

    public void setDopositAfterTransaction(String dopositAfterTransaction) {
        this.dopositAfterTransaction = dopositAfterTransaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTransactionAmmount() {
        return transactionAmmount;
    }

    public void setTransactionAmmount(double transactionAmmount) {
        this.transactionAmmount = transactionAmmount;
    }




}


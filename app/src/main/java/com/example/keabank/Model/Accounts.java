package com.example.keabank.Model;


import java.io.Serializable;

public class Accounts {
    private String accountName;
    private Double currentDeposit;
    private String AccountType;
    private Long accountNumber;
    private Long registrationnumber;






        public Accounts(String accountName, Double currentDeposit, String accountType) {
        this.accountName = accountName;
        this.currentDeposit = currentDeposit;
        this.AccountType = accountType;


        }

    public Accounts(String accountName, Double currentDeposit, String accountType, Long accountNumber, Long registrationnumber) {
        this.accountName = accountName;
        this.currentDeposit = currentDeposit;
        this.AccountType = accountType;
        this.accountNumber = accountNumber;
        this.registrationnumber = registrationnumber;
    }

    @Override
    public String toString() {
        return  accountName + " available deposit: " + currentDeposit + " AccountType: " + AccountType;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getCurrentDeposit() {
        return currentDeposit;
    }

    public void setCurrentDeposit(Double currentDeposit) {
        this.currentDeposit = currentDeposit;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getRegistrationnumber() {
        return registrationnumber;
    }

    public void setRegistrationnumber(Long registrationnumber) {
        this.registrationnumber = registrationnumber;
    }
}

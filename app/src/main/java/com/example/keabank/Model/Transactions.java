package com.example.keabank.Model;

import java.time.LocalDate;

public class Transactions {
    private String transactionName;
    private String message;
    private Long FromReg;
    private Long fromAccountNumer;
    private Long ToReg;
    private Long toAccountNumer;
    private String date;
    private String transactionAmmount;
    private Double currentdeposit;


    public Transactions() {

    }

    public Transactions(String transactionName, Long fromReg, Long fromAccountNumer, Long toReg, Long toAccountNumer, String date, String transactionAmmount) {
        this.transactionName = transactionName;
        this.FromReg = fromReg;
        this.fromAccountNumer = fromAccountNumer;
        this.ToReg = toReg;
        this.toAccountNumer = toAccountNumer;
        this.date = date;
        this.transactionAmmount = transactionAmmount;
    }

    public Transactions(String transactionName,String message ,Long fromReg, Long fromAccountNumer, Long toReg, Long toAccountNumer, String date, String transactionAmmount) {
        this.transactionName = transactionName;
        this.message= message;
        this.FromReg = fromReg;
        this.fromAccountNumer = fromAccountNumer;
        this.ToReg = toReg;
        this.toAccountNumer = toAccountNumer;
        this.date = date;
        this.transactionAmmount = transactionAmmount;
    }

    public Transactions(String transactionName, String date, String transactionAmmount, Double currentdeposit) {
        this.transactionName = transactionName;
        this.date = date;
        this.transactionAmmount = transactionAmmount;
        this.currentdeposit = currentdeposit;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public Long getFromReg() {
        return FromReg;
    }

    public void setFromReg(Long fromReg) {
        FromReg = fromReg;
    }

    public Long getFromAccountNumer() {
        return fromAccountNumer;
    }

    public void setFromAccountNumer(Long fromAccountNumer) {
        this.fromAccountNumer = fromAccountNumer;
    }

    public Long getToReg() {
        return ToReg;
    }

    public void setToReg(Long toReg) {
        ToReg = toReg;
    }

    public Long getToAccountNumer() {
        return toAccountNumer;
    }

    public void setToAccountNumer(Long toAccountNumer) {
        this.toAccountNumer = toAccountNumer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionAmmount() {
        return transactionAmmount;
    }

    public void setTransactionAmmount(String transactionAmmount) {
        this.transactionAmmount = transactionAmmount;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Double getCurrentdeposit() {
        return currentdeposit;
    }

    public void setCurrentdeposit(Double currentdeposit) {
        this.currentdeposit = currentdeposit;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transactionName='" + transactionName + '\'' +
                ", message='" + message + '\'' +
                ", FromReg=" + FromReg +
                ", fromAccountNumer=" + fromAccountNumer +
                ", ToReg=" + ToReg +
                ", toAccountNumer=" + toAccountNumer +
                ", date='" + date + '\'' +
                ", transactionAmmount='" + transactionAmmount + '\'' +
                '}';
    }
}



package com.example.keabank.Model;


abstract class TransActions {
        private String transactionName;
       private String dopositBeforeTransaction;
       private String dopositAfterTransaction;
        private String date;
        private double transactionAmmount;

        public TransActions() {

        }

        public TransActions(String transactionName, String dopositBeforeTransaction, String dopositAfterTransaction, String date) {
            this.transactionName = transactionName;
            this.dopositBeforeTransaction = dopositBeforeTransaction;
            this.dopositAfterTransaction = dopositAfterTransaction;
            this.date = date;

        }   public TransActions(String transactionName, double transactionAmmount, String dopositAfterTransaction, String date) {
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


    public class Accounts extends TransActions {
    private String accountName;
    private Double currentDeposit;
    private String AccountType;
    private int ResonseCode;
    private String Cpr;


    public Accounts(String transactionName, String dopositBeforeTransaction, String dopositAfterTransaction, String date) {
        super(transactionName, dopositBeforeTransaction, dopositAfterTransaction, date);
    }

        public Accounts(String transactionName, double transactionAmmount, String dopositAfterTransaction, String date, boolean sendingOrreciving) {
            super(transactionName, transactionAmmount, dopositAfterTransaction, date);
        }

    public Accounts(String accountName, Double currentDeposit, String accountType) {
        this.accountName = accountName;
        this.currentDeposit = currentDeposit;
        this.AccountType = accountType;
    }

    public Accounts(int ResonseCode){
        this.ResonseCode=ResonseCode;
    }
        public Accounts(String Cpr){
            this.Cpr=Cpr;
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

        public int getResonseCode() {
            return ResonseCode;
        }

        public void setResonseCode(int resonseCode) {
            ResonseCode = resonseCode;
        }

        public String getCpr() {
            return Cpr;
        }

        public void setCpr(String cpr) {
            Cpr = cpr;
        }

        @Override
    public String toString() {
        return  accountName + " available deposit: " + currentDeposit + " AccountType: " + AccountType;
    }
}

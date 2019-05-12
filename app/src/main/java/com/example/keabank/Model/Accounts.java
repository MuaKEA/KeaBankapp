package com.example.keabank.Model;




    public class Accounts {
    private String accountName;
    private Double currentDeposit;
    private String AccountType;
    private int ResonseCode;
    private String Cpr;



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

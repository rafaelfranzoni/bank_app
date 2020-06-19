package br.com.bank_app.model;

public class Credencial {

    private String userId;
    private String name;
    private String bankAccount;
    private String agency;
    private String balance;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Credencial{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", agency='" + agency + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}

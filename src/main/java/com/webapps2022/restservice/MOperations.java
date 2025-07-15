package com.webapps2022.restservice;

public class MOperations {

    String currency1;
    String currency2;
    Double amount_of_currency1;

    Double result;

    public MOperations() {
    }

    public MOperations(String currency1, String currency2, Double amount_of_currency1) {
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.amount_of_currency1 = amount_of_currency1;
    }

    public String getCurrency1() {
        return currency1;
    }

    public void setCurrency1(String currency1) {
        this.currency1 = currency1;
    }

    public String getCurrency2() {
        return currency2;
    }

    public void setCurrency2(String currency2) {
        this.currency2 = currency2;
    }

    public Double getAmount_of_currency1() {
        return amount_of_currency1;
    }

    public void setAmount_of_currency1(Double amount_of_currency1) {
        this.amount_of_currency1 = amount_of_currency1;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

}

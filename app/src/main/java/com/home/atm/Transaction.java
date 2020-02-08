package com.home.atm;

import org.json.JSONException;
import org.json.JSONObject;

public class Transaction {
    private String account;
    private String date;
    private int amount;
    private int type;

    Transaction() {

    }

    Transaction(String account, String date, int amount, int type) {
        this.date = date;
        this.account = account;
        this.amount = amount;
        this.type = type;
    }

    Transaction(JSONObject jsonObject) {
        try {
            date = jsonObject.getString("date");
            account = jsonObject.getString("account");
            amount = jsonObject.getInt("amount");
            type = jsonObject.getInt("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String getAccountStr() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setAmountValue(int amount) {
        this.amount = amount;
    }

    public void setTypeValue(int type) {
        this.type = type;
    }

    String getDateStr() {
        return date;
    }


    int getAmountValue() {
        return amount;
    }

    int getTypeValue() {
        return type;
    }
}

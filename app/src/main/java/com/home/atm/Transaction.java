package com.home.atm;

import org.json.JSONException;
import org.json.JSONObject;

public class Transaction {
    private String dateStr;
    private int amountValue;
    private int typeValue;

    public Transaction() {

    }

    Transaction(String dateStr, int amountValue, int typeValue) {
        this.dateStr = dateStr;
        this.amountValue = amountValue;
        this.typeValue = typeValue;
    }

    Transaction(JSONObject jsonObject) {
        try {
            dateStr = jsonObject.getString("date");
            amountValue = jsonObject.getInt("amount");
            typeValue = jsonObject.getInt("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }


    public void setAmountValue(int amountValue) {
        this.amountValue = amountValue;
    }

    public void setTypeValue(int typeValue) {
        this.typeValue = typeValue;
    }

    String getDateStr() {
        return dateStr;
    }


    int getAmountValue() {
        return amountValue;
    }

    int getTypeValue() {
        return typeValue;
    }
}

package com.home.atm;

import org.json.JSONException;
import org.json.JSONObject;

public class Transaction {
    public String accountStr;
    public String dateStr;
    public int amountValue;
    public int typeValue;

    Transaction(String accountStr, String dateStr, int amountValue, int typeValue) {
        this.dateStr = dateStr;
        this.accountStr = accountStr;
        this.amountValue = amountValue;
        this.typeValue = typeValue;
    }

    Transaction(JSONObject jsonObject) {
        try {
            dateStr = jsonObject.getString("date");
            accountStr = jsonObject.getString("account");
            amountValue = jsonObject.getInt("amount");
            typeValue = jsonObject.getInt("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getAccountStr() {
        return accountStr;
    }

    public void setAccountStr(String accountStr) {
        this.accountStr = accountStr;
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

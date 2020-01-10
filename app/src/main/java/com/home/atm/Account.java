package com.home.atm;

import java.util.ArrayList;
import java.util.List;

public class Account {
    int _id;
    String name;
    List<String> account_lst = new ArrayList<>();

    Account(int _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public List<String> getAccount_lst() {
        return account_lst;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount_lst(List<String> account_lst) {
        this.account_lst = account_lst;
    }
}

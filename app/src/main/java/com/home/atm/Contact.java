package com.home.atm;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private int _id;
    String name;
    private List<String> contact_lst = new ArrayList<>();

    Contact(int _id, String name) {
        this._id = _id;
        this.name = name;
    }

//    public int get_id() {
//        return _id;
//    }

    public String getName() {
        return name;
    }

    List<String> getContact_lst() {
        return contact_lst;
    }

//    public void set_id(int _id) {
//        this._id = _id;
//    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setContact_lst(List<String> contact_lst) {
//        this.contact_lst = contact_lst;
//    }
}

package com.home.atm;

public class mFunction {
    private String name;
    private int icons_res;

//    public mFunction(String name) {
//        this.name = name;
//    }

    mFunction(String name, int icons_res) {
        this.name = name;
        this.icons_res = icons_res;
    }

    int getIcons_res() {
        return icons_res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


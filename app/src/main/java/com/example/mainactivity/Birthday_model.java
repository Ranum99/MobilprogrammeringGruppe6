package com.example.mainactivity;

import java.util.ArrayList;

public class Birthday_model {

    private String name, phone, date;

    public Birthday_model(String name, String phone, String date) {
        this.date = date;
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public static void getData() {
        ArrayList names = new ArrayList<Birthday_model>();
        ArrayList phone = new ArrayList<Birthday_model>();
        ArrayList date = new ArrayList<Birthday_model>();

    }
}

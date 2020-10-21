package com.example.mainactivity;

public class User {

    public String getName() {
        return name;
    }

    public int id, mobilnr;
    public String name, email, password, birthday;

    public static final String SESSION = "UserSession";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String BIRTHDAY = "birthday";
    public static final String MOBILNR = "mobilnr";

    public User(int id, int mobilnr, String name, String email, String password, String birthday) {
        this.id = id;
        this.mobilnr = mobilnr;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package com.example.mainactivity;

public class User {
    public String name, email, password, birthday;

    public static final String SESSION = "UserSession";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String BIRTHDAY = "birthday";

    public User(String name, String email, String password, String birthday) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }
}

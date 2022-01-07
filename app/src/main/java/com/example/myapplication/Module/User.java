package com.example.myapplication.Module;

import android.text.TextUtils;
import android.util.Patterns;


public class User {
    String email;
    String password;
    String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String email, String password, String name) {

        this.email = email;
        this.password = password;
        this.name = name;
    }
    public boolean isValidEmail(){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean isValidPassword(){
        return !TextUtils.isEmpty(password) && password.length()>=6;
    }
}

package com.revature.models;

public class login { // light weight login class, that is used to read JSon passed in durring login
    String userName;
    String password;

    public login() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

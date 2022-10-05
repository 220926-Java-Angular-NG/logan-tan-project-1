package com.revature.models;

public class User { // Database information storage
    String firstName; // stores names will be used as the sorting variable
    String lastName;
    String userName;
    String Password; // Identifier
    String UID = null;
    String acctype = "EMP"; // 0 is employee, 1 is manager

    public User() {
    } // empty constructor for javalin to use

    public User(String firstName, String lastName, String userName, String Password, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.Password = Password;
        acctype = type;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setFirstName(String Name) {
        this.firstName = Name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String Name) {
        this.lastName = Name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String Name) {
        this.userName = Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setAcctype(String acctype) {
        this.acctype = acctype;
    }

    public String getAcctype() {
        return acctype;
    }
}

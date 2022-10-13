package com.revature.models;

public class User { // Database information storage
    String firstName; // stores names will be used as the sorting variable
    String lastName;
    String userName;
    String Password; // Identifier
    int UID;
    String acctype = "EMP"; // 0 is employee, 1 is manager
    byte[] pfp = null;
    String Email = null;
    String address = null;

    public User() {
    } // empty constructor for javalin to use

    public User(String firstName, String lastName, String userName, String Password, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.Password = Password;
        acctype = type;
    }
    public User(String firstName, String lastName, String userName, String Password, String type, int UID, byte[] pfp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.Password = Password;
        acctype = type;
        this.UID = UID;
        this.pfp = pfp;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String Name) {
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
    public int getUID(){return UID;}
    public void setUID(int UID){this.UID = UID;}
    public void setPfp(byte[] pfp){ this.pfp = pfp;}
    public byte[] getPfp(){return pfp;}
    public void setEmail(String email){Email = email;}
    public String getEmail(){return Email;}
    public void  setAddress(String address){this.address = address;}
    public String getAddress() {
        return address;
    }
}

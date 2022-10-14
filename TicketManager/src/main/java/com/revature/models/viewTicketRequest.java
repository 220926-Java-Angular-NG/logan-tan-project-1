package com.revature.models;

public class viewTicketRequest {
    String permission;
    String status = "%";

    String type = "%";

    public viewTicketRequest(){}

    public void setStatus(String status) {this.status = status;}

    public String getStatus() {return status;}

    public String getPermission() {return permission;}

    public void setPermission(String permission) {this.permission = permission;}

    public void setType(String type){this.type = type;}

    public String getType(){return type;}
}


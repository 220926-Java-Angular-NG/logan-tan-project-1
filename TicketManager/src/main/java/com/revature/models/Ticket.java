package com.revature.models;

import com.revature.Utils.RType;

import java.util.Objects;

import static com.revature.Utils.RType.*;

public class Ticket {
    float Reimburstment = 0;
    String Disc = "";
    String status = "PEN"; // PEN = pending, ACC = accepted, REJ = rejected
    String Owner = "";
    int id;
    RType type = ECT;
    public Ticket() {
    } // empty constructor for javalin to use

    public Ticket(float amt, String Des) {
        Reimburstment = amt;
        Disc = Des;
    }
    public Ticket(float amt, String Des,String status,String Owner, int id) {
        setDisc(Des);
        setOwner(Owner);
        setReimburstment(amt);
        setStatus(status);
        setId(id);
    }
    public Ticket(float amt, String Des,String status,String Owner, int id,String code) {
        setDisc(Des);
        setOwner(Owner);
        setReimburstment(amt);
        setStatus(status);
        setId(id);
        this.type = RType.getType(code);
    }

    public Ticket(float amt, String Des,String status) {
        Reimburstment = amt;
        Disc = Des;
        this.status = status;
    }
    public float getReimburstment(){return Reimburstment;}
    public String getDisc(){return Disc;}
    public String getStatus(){return status;}
    public void setReimburstment(float Reimburstment){this.Reimburstment = Reimburstment;}
    public void setDisc(String Disc){this.Disc = Disc;}
    public void setStatus(String status){this.status = status;}
    public String getOwner() {return Owner;}
    public void setOwner(String owner) {Owner = owner;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public RType getType(){return type;}
    public void setType(String code){this.type = RType.getType(code);}

    public String display() {
        String out = "";
        out += "Amount: " + getReimburstment();
        out +="\n" + getDisc();
        if(!Objects.equals(Owner, "")){
            out += "\nOwner: " + getOwner();
        }
        switch (status) {
            case "PEN":
                out += "\nPending";
                break;
            case "ACC":
                out += "\nAccepted";
                break;
            case "REJ":
                out += "\nRejected";
                break;
            default: {
                out += "\nStatus Unknown";
                break;
            }
        }
        out +="\nReimburstment Type:" + getType();
        out +="\nCompany pays:" + getReimburstment() * getType().getPercentage();
        return out;
    }
}

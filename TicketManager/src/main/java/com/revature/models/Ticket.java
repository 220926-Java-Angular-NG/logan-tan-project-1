package com.revature.models;

public class Ticket { // Database infomration storage
    float Reimburstment;
    String Disc;
    String status = "PEN"; // PEN = pending, ACC = accepted, REJ = rejected

    String Owner = "";

    public Ticket() {
    } // empty constructor for javalin to use

    public Ticket(float amt, String Des) {
        Reimburstment = amt;
        Disc = Des;
    }
    public Ticket(float amt, String Des,String status,String Owner) {
        setDisc(Des);
        setOwner(Owner);
        setReimburstment(amt);
        setStatus(status);
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
    public String display() {
        String out = "";
        out += "Amount: " + getReimburstment();
        out +="\n" + getDisc();
        if(Owner != ""){
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
        return out;
    }
}

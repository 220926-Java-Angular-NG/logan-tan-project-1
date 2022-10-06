package com.revature.models;

public class Ticket { // Database infomration storage
    float Reimburstment;
    String Disc;
    String status = "PEN"; // PEN = pending, ACC = accepted, REJ = rejected

    public Ticket() {
    } // empty constructor for javalin to use

    public Ticket(float amt, String Des) {
        Reimburstment = amt;
        Disc = Des;
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

    public void display() {
        System.out.println("Amount: " + Reimburstment);
        System.out.println(Disc);
        switch (status) {
            case "PEN":
                System.out.println("Pending");
                break;
            case "ACC":
                System.out.println("Accepted");
                break;
            case "REJ":
                System.out.println("Rejected");
                break;
            default: {
                System.out.println("Unknown");
                break;
            }
        }
    }
}

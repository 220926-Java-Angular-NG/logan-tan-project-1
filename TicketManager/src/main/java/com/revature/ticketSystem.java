package com.revature;
import io.javalin.Javalin;

public class ticketSystem {
    public static void main(String[] args) {
        databaseHandler db = new databaseHandler();
        Javalin app = Javalin.create().start(8080);
        app.get("/login",context -> {
            context.result("Login Screen");
        });
        app.post("/register",context -> {
                    User user = context.bodyAsClass(User.class);
                    db.registerUser(user.Name,user.Password);
                });
        app.get("/TeaPot",context -> {
            context.result("TeaPot");

        });
    }
}

class User { // Database information storage
    String Name; // stores Usernames will be used as the sorting variable
    String Password; // Identifier
    String UID = null;
    int acctype = 0; // 0 is employee, 1 is manager

    public User(){} // empty constructor for javalin to use
    public User(String Name, String Password, int type){
        this.Name = Name;
        this.Password = Password;
        acctype = type;
    }
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}

class Ticket { // Database infomration storage
    float Reimburstment;
    String Disc;
    short status = 0; // 0 = pending, 1 = accepted, 2 = rejected
    public Ticket(){} // empty constructor for javalin to use
    public Ticket(float amt, String Des){
        Reimburstment = amt;
        Disc = Des;
    }
    public void display(){
        System.out.println("Amount: " + Reimburstment);
        System.out.println(Disc);
        switch (status) {
            case 0:
                System.out.println("Pending");
                break;
            case 1:
                System.out.println("Accepted");
                break;
            case 2:
                System.out.println("Rejected");
                break;
            default:
            {break;}
        }
    }
}
package com.revature;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ticketSystem {
    public static void main(String[] args) {
        Connection db = null;
        try {
            db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","Shining");
            Javalin app = Javalin.create().start(8080);
            app.get("/login",context -> {
            });
            app.get("/TeaPot",context -> {
                context.result("TeaPot");

            });
            app.post("/users",context -> {
                context.result("1");
                context.redirect("/users");
            });
        } catch(Exception e){
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
}

class User { // Node like structure that will store information used
    int UID;
    String Name; // stores Usernames will be used as the sorting variable
    String Pass; // Identifier
    int acctype; // 0 is employee, 1 is manager
    Ticket[] tickets;

    public User(){} // empty constructor for javalin to use
    public User(String Name, String Password, int type){
        this.Name = Name;
        Pass = Password;
        acctype = type;
    }
}

class Ticket {
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
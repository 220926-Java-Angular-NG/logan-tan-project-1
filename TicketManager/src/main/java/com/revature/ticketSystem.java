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
                    db.registerUser(user.firstName,user.lastName,user.userName,user.Password);
                });
        app.post("/login",context -> {
            login login = context.bodyAsClass(login.class);
            User user = db.login(login.userName, login.password);
            if(user != null) {
                System.out.println(user.acctype);
            }
        });
        app.get("/TeaPot",context -> {
            context.result("TeaPot");

        });
    }
}
class login{
    String userName;
    String password;
    public login(){}

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
class User { // Database information storage
    String firstName; // stores names will be used as the sorting variable
    String lastName;
    String userName;
    String Password; // Identifier
    String UID = null;
    String acctype = "EMP"; // 0 is employee, 1 is manager

    public User(){} // empty constructor for javalin to use
    public User(String firstName,String lastName, String userName, String Password, String type){
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
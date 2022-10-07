package com.revature.Repos;
import com.revature.Services.employeeService;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.models.viewTicketRequest;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.sql.SQLException;
import java.util.List;

public class employeeHandler{
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    User user = null;
    List<Ticket> tickets = null;
    boolean loggedin = true;
    public employeeHandler(){}
    public employeeHandler(Javalin app, String path, databaseHandler db, User user){
        this.app = app;
        this.path = path;
        this.db = db;
        this.user = user;
    }

    public Handler HomePage = context ->{
        if(loggedin){
            context.result("Employee home page\n    addticket\n    viewtickets");
        }else{
            context.result("Not logged in").status(404);
        }
    };

    public Handler AddTicket = context -> {
        if(loggedin){
            Ticket ticket = context.bodyAsClass(Ticket.class);
            AddTicket(ticket);
        }else{
            context.result("Not logged in").status(404);
        }
    };

    public Handler ViewTickets = context -> {
        if(loggedin){
            StringBuilder TicketRaw = new StringBuilder();
            if(tickets != null) {
                for (Ticket ticket : tickets) {
                    TicketRaw.append(ticket.display()).append("\n\n");
                }
                context.result(String.valueOf(TicketRaw));
                context.json(tickets);
                tickets = null;

            }}else{
            context.result("Not logged in").status(404);
        }
    };

    public Handler GetTickets = context -> {
        if(loggedin){
            viewTicketRequest request = context.bodyAsClass(viewTicketRequest.class);
            tickets = ViewTickets(String.valueOf(user.getUID()),request.getStatus());
            context.redirect(path+"/viewticket");
        }else{
            context.result("Not logged in").status(404);
        }
    };
    public List<Ticket> ViewTickets(String who, String status) {
        List<Ticket> tickets = null;
        try{
        tickets = db.viewTickets(who,status);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return tickets;
    }
    public boolean ApproveDenyTicket(Ticket ticket) {
        return false;
    }
    public void AddTicket(Ticket ticket) {
        try{
        db.addTicket(ticket,user.getUID());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void Logout() {

    }
    public String getPath() {
        return path;
    }
    public Javalin getApp() {
        return app;
    }
    public databaseHandler getDb() {
        return db;
    }

    public User getUser() {
        return user;
    }
    public void setLoggedin(boolean state) {
        loggedin = state;
    }
    public boolean isLoggedin() {
        return false;
    }
}

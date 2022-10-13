package com.revature.Repos;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.models.viewTicketRequest;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class managerHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(managerHandler.class);
    databaseHandler db;
    String path;
    Javalin app;
    List<Ticket> tickets = null;
    User user;
    boolean loggedin = true;

    public managerHandler (Javalin app, String path, databaseHandler db, User user){
        this.app = app;
        this.path = path;
        this.db = db;
        this.user = user;
    }
    public Handler HomePage = context ->{
        if(loggedin){
            context.result("Manager home page\n    AddTicket\n    ViewTickets\n    Approve/DenyTickets\n   ViewMyTickets\n   ReviewEmployeeTickets");
        }else{
            context.result("Not logged in").status(404);
        }
    };
    public Handler AddTicket = context -> {
        if(loggedin){
            Ticket ticket = context.bodyAsClass(Ticket.class);
            if(ticket != null) {
                if(ticket.getReimburstment() > 0) {
                    if(ticket.getDisc().length() > 0) {
                        AddTicket(ticket);
                        context.result("Ticket Added").status(200);
                    } else{
                        context.result("You need a description").status(200);
                    }
                } else{
                    context.result("Improper reimburstment amount").status(400);
                }
            } else{
                context.result("could not read body").status(400);
            }
        }else{
            context.result("Not logged in").status(404);
        }
    };
    public void AddTicket(Ticket ticket) {
        try{
            db.addTicket(ticket,user.getUID());
        } catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
    }
    public Handler GetTickets = context ->{
        if(loggedin){
            viewTicketRequest request = context.bodyAsClass(viewTicketRequest.class);
            tickets = ViewTickets(String.valueOf(user.getUID()),request.getType(),request.getStatus());
            context.redirect(path+"/ViewTicket");
        }else{
            context.result("Not logged in").status(404);
        }
    };

    public Handler GetMyTickets = context -> {
        if(loggedin){
            viewTicketRequest request = context.bodyAsClass(viewTicketRequest.class);
            tickets = ViewMyTickets(String.valueOf(user.getUID()),request.getType(),request.getStatus());
            context.redirect(path+"/ViewTicket");
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
                //context.json(tickets);
                tickets = null;

            }
        }else{
            context.result("Not logged in").status(404);
        }
    };

    public Handler AccRejTickets = context ->{
        if(loggedin){
            String Nstate = context.pathParam("state");
            if(Nstate.equals("ACC") || Nstate.equals("REJ")){ //tickets can only be approved or rejected.
                String tid = context.pathParam("tid"); //get the unique ticket ID
                boolean exp = db.AprDenTicket(Nstate,Integer.parseInt(tid), user.getUID());
                if(!exp){
                    context.result("That action is Illegal, check that you have the correct Ticket ID," +
                            "your not attempting to clear your own ticket, or that it is not already accepted or rejected");
                }
            } else{
                context.result("Not attempting to accept or reject ticket");
            }
        }else{
            context.result("Not logged in").status(404);
        }
    };
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
        this.loggedin = state;
    }

    public boolean isLoggedin() {
        return false;
    }

    public List<Ticket> ViewTickets(String who, String type,String status) {
        List<Ticket> tickets = null;
        try{
            tickets = db.viewTickets("%",who,status,type, true);
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
        return tickets;
    }
    public List<Ticket> ViewMyTickets(String who, String type,String status) {
        List<Ticket> tickets = null;
        try{
            tickets = db.viewTickets("%",who,status, type,false);
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
        return tickets;
    }
}

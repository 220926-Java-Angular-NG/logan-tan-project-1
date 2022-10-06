package com.revature.Repos;
import com.revature.Utils.Handler;
import com.revature.models.Ticket;
import com.revature.models.User;
import io.javalin.Javalin;

import java.sql.SQLException;
import java.util.List;

public class employeeHandler implements Handler {
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    User user = null;
    public employeeHandler(Javalin app, String path, databaseHandler db, User user){
        this.app = app;
        this.path = path;
        this.db = db;
        this.user = user;
        app.get(path, context -> {
            context.result("Employee home page");

        });
        app.post(path+"/addticket",context -> {
            Ticket ticket = context.bodyAsClass(Ticket.class);
            AddTicket(ticket);
        });
        app.get(path+"/viewticket",context -> {
            ViewTickets(String.valueOf(user.getUID()),"%");
        });
    }
    @Override
    public void ViewTickets(String who, String status) {
        try{
        List<Ticket> tickets = db.viewTickets(who,status);
        for(Ticket ticket: tickets){
            ticket.display();
        }}catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean ApproveDenyTicket(Ticket ticket) {
        return false;
    }

    @Override
    public void AddTicket(Ticket ticket) {
        try{
        db.addTicket(ticket,user.getUID());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void Logout() {

    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Javalin getApp() {
        return app;
    }

    @Override
    public databaseHandler getDb() {
        return db;
    }
}

package com.revature.Repos;
import com.revature.models.Ticket;
import com.revature.models.User;
import io.javalin.Javalin;

import java.util.List;

public class managerHandler{
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    User user = null;
    List<Ticket> tickets = null;
    boolean loggedin = true;

    public List<Ticket> ViewTickets(String who, String status) {

        return null;
    }

    public boolean ApproveDenyTicket(Ticket ticket) {
        return false;
    }

    public void AddTicket(Ticket ticket) {
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
        this.loggedin = state;
    }

    public boolean isLoggedin() {
        return false;
    }
}

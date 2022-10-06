package com.revature.Repos;
import com.revature.Utils.Handler;
import com.revature.models.Ticket;
import io.javalin.Javalin;

public class managerHandler implements Handler {
    @Override
    public void ViewTickets(String who, String status) {

    }

    @Override
    public boolean ApproveDenyTicket(Ticket ticket) {
        return false;
    }

    @Override
    public void AddTicket(Ticket ticket) {
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

package com.revature.Repos;
import com.revature.Utils.userHandler;
import com.revature.models.Ticket;
import io.javalin.Javalin;

import java.util.List;

public class managerHandler implements userHandler {
    @Override
    public List<Ticket> ViewTickets(String who, String status) {

        return null;
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

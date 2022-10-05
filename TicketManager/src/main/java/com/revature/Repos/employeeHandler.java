package com.revature.Repos;
import com.revature.Utils.Handler;
import com.revature.models.Ticket;
import io.javalin.Javalin;

public class employeeHandler implements Handler {
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    public employeeHandler(Javalin app, String path, databaseHandler db){
        this.app = app;
        this.path = path;
        this.db = db;
        app.get(path, context -> {
            context.result("Employee home page");

        });
    }
    @Override
    public void ViewTickets() {

    }

    @Override
    public boolean ApproveDenyTicket(Ticket ticket) {
        return false;
    }

    @Override
    public boolean AddTicket(Ticket ticket) {
        return false;
    }

    @Override
    public void Logout() {

    }
}

package com.revature.Repos;
import com.revature.Utils.Handler;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.models.viewTicketRequest;
import io.javalin.Javalin;
import java.sql.SQLException;
import java.util.List;

public class employeeHandler implements Handler {
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    User user = null;
    List<Ticket> tickets = null;
    public employeeHandler(Javalin app, String path, databaseHandler db, User user){
        this.app = app;
        this.path = path;
        this.db = db;
        this.user = user;
        app.get(path, context -> {
            context.result("Employee home page\n    addticket\n    viewtickets");

        });
        app.post(path+"/addticket",context -> {
            Ticket ticket = context.bodyAsClass(Ticket.class);
            AddTicket(ticket);
        });
        app.post(path+"/viewticket",context -> {
            viewTicketRequest request = context.bodyAsClass(viewTicketRequest.class);
            tickets = ViewTickets(String.valueOf(user.getUID()),request.getStatus());
            context.redirect(path+"/viewticket");
        });
        app.get(path+"/viewticket",context -> {
            StringBuilder TicketRaw = new StringBuilder();
            if(tickets != null) {
                for (Ticket ticket : tickets) {
                    TicketRaw.append(ticket.display()).append("\n\n");
                }
                tickets = null;
            }
            context.result(TicketRaw.toString());
        });
    }
    @Override
    public List<Ticket> ViewTickets(String who, String status) {
        List<Ticket> tickets = null;
        try{
        tickets = db.viewTickets(who,status);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return tickets;
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

package com.revature.Repos;
import com.revature.Utils.userHandler;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.models.viewTicketRequest;
import io.javalin.Javalin;
import java.sql.SQLException;
import java.util.List;

public class employeeHandler implements userHandler {
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    User user = null;
    List<Ticket> tickets = null;
    boolean loggedin = true;
    public employeeHandler(Javalin app, String path, databaseHandler db, User user){
        this.app = app;
        this.path = path;
        this.db = db;
        this.user = user;
        app.get(path, context -> {
            if(loggedin){
            context.result("Employee home page\n    addticket\n    viewtickets");
            }else{
                context.result("Not logged in").status(200);
            }
        });
        app.post(path+"/addticket",context -> {
            if(loggedin){
            Ticket ticket = context.bodyAsClass(Ticket.class);
            AddTicket(ticket);
            }else{
                context.result("Not logged in").status(200);
            }
        });
        app.post(path+"/viewticket",context -> {
            if(loggedin){
            viewTicketRequest request = context.bodyAsClass(viewTicketRequest.class);
            tickets = ViewTickets(String.valueOf(user.getUID()),request.getStatus());
            context.redirect(path+"/viewticket");
            }else{
                context.result("Not logged in").status(200);
            }
        });
        app.get(path+"/viewticket",context -> {
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
                context.result("Not logged in").status(200);
            }
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

    public User getUser() {
        return user;
    }

    @Override
    public void setLoggedin(boolean state) {
        loggedin = state;
    }

    @Override
    public boolean isLoggedin() {
        return false;
    }
}

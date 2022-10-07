package com.revature.Services;

import com.revature.Repos.databaseHandler;
import com.revature.Repos.employeeHandler;
import com.revature.Utils.userService;
import com.revature.models.Ticket;
import com.revature.models.User;
import io.javalin.Javalin;

import java.sql.SQLException;
import java.util.List;

public class employeeService implements userService {
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    User user = null;
    List<Ticket> tickets = null;
    boolean loggedin = true;
    employeeHandler emh = null;
    public employeeService(){}
    public employeeService(Javalin app, String path, databaseHandler db, User user){
        emh = new employeeHandler(app,path,db,user);
        this.app = app;
        this.path = path;
        this.db = db;
        this.user = user;

        app.get(path,emh.HomePage);
        app.post(path+"/addticket", emh.AddTicket);
        app.post(path+"/viewticket", emh.GetTickets);
        app.get(path+"/viewticket",emh.ViewTickets);
    }

    @Override
    public List<Ticket> ViewTickets(String who, String status) throws SQLException {
        return null;
    }

    @Override
    public boolean ApproveDenyTicket(Ticket ticket) {
        return false;
    }

    @Override
    public void AddTicket(Ticket ticket) throws SQLException {

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

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setLoggedin(boolean state) {
        loggedin = state;
        emh.setLoggedin(state);

    }

    @Override
    public boolean isLoggedin() {
        return loggedin;
    }
}

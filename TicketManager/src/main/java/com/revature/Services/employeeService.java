package com.revature.Services;

import com.revature.Repos.databaseHandler;
import com.revature.Repos.employeeHandler;
import com.revature.Utils.userService;
import com.revature.models.Ticket;
import com.revature.models.User;
import io.javalin.Javalin;
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
        app.post(path+"/AddTicket", emh.AddTicket);
        app.post(path+"/ViewTicket", emh.GetTickets);
        app.get(path+"/ViewTicket",emh.ViewTickets);
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

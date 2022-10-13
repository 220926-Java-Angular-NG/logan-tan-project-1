package com.revature.Services;
import com.revature.Repos.databaseHandler;
import com.revature.Repos.managerHandler;
import com.revature.Utils.userService;
import com.revature.models.User;
import io.javalin.Javalin;

public class managerService implements userService {
    databaseHandler db;
    String path;
    Javalin app;
    User user;
    boolean state = false;
    public managerService(Javalin app, String path, databaseHandler db, User user){
        this.app = app;
        this.path = path;
        this.db = db;
        this. user = user;
        managerHandler mah = new managerHandler(app,path,db,user);
        app.get(path,mah.HomePage);
        app.post(path+"/AddTicket", mah.AddTicket);
        app.post(path+"/ViewMyTicket", mah.GetMyTickets);
        app.post(path+"/ViewTicket", mah.GetTickets);
        app.get(path+"/ViewTicket",mah.ViewTickets);
        app.patch(path+"/Tickets/<state>/{tid}",mah.AccRejTickets);
    }
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setLoggedin(boolean state) {
        this.state = state;
    }

    @Override
    public boolean isLoggedin() {
        return state;
    }

    @Override
    public User getUser() {
        return user;
    }
}

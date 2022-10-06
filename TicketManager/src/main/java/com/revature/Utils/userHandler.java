package com.revature.Utils;

import com.revature.Repos.databaseHandler;
import com.revature.models.Ticket;
import com.revature.models.User;
import io.javalin.Javalin;

import java.sql.SQLException;
import java.util.List;

public interface userHandler {
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    User user = null;
    List<Ticket> ViewTickets(String who, String status) throws SQLException;
    boolean ApproveDenyTicket(Ticket ticket);
    void AddTicket(Ticket ticket) throws SQLException;
    void Logout();
    String getPath();
    Javalin getApp();
    databaseHandler getDb();
    User getUser();
    void setLoggedin(boolean state);
    boolean isLoggedin();
}

package com.revature.Utils;

import com.revature.Repos.databaseHandler;
import com.revature.models.Ticket;
import io.javalin.Javalin;

import java.sql.SQLException;

public interface Handler {
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    void ViewTickets(String who, String status) throws SQLException;
    boolean ApproveDenyTicket(Ticket ticket);
    void AddTicket(Ticket ticket) throws SQLException;
    void Logout();
    String getPath();
    Javalin getApp();
    databaseHandler getDb();
}

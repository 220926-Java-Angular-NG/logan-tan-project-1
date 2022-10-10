package com.revature.Utils;

import com.revature.Repos.databaseHandler;
import com.revature.models.Ticket;
import com.revature.models.User;
import io.javalin.Javalin;

import java.sql.SQLException;
import java.util.List;

public interface userService {
    databaseHandler db = null;
    String path = null;
    Javalin app = null;
    User user = null;
    String getPath();
    void setLoggedin(boolean state);
    boolean isLoggedin();
}

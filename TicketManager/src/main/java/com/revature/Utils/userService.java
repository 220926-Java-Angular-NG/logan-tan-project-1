package com.revature.Utils;

import com.revature.models.User;

public interface userService {
    String getPath();
    void setLoggedin(boolean state);
    boolean isLoggedin();

    User getUser();
}

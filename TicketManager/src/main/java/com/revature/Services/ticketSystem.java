package com.revature.Services;
import com.revature.Repos.databaseHandler;
import com.revature.Utils.Handler;
import com.revature.models.User;
import com.revature.models.login;
import io.javalin.Javalin;

import java.util.concurrent.atomic.AtomicReference;

public class ticketSystem {
    public static void main(String[] args) {
        databaseHandler db = new databaseHandler();
        AtomicReference<Handler> logged = new AtomicReference<>();
        Javalin app = Javalin.create().start(8080);
        app.get("/login",context -> {
            context.result("Login Screen");
        });
        app.post("/register",context -> {
                    User user = context.bodyAsClass(User.class);
                    if(user.getAcctype().equals("EMP")){
                        if(db.registerEMP(user.getfirstName(),user.getLastName(),user.getUserName(),user.getPassword()))
                        {
                            context.result("Account created");
                        }else{
                            context.result("Process Failure");
                        }
                    } else{
                        if(db.registerMAN(user.getfirstName(),user.getLastName(),user.getUserName(),user.getPassword())){
                            context.result("Account created");
                        }else{
                            context.result("Process Failure");
                        }
                    }
                });
        app.post("/login",context -> {
            login login = context.bodyAsClass(login.class);
            User user = db.login(login.getUserName(), login.getPassword());
            if(user != null && user.getPassword().equals(login.getPassword())) {
                if(user.getAcctype().equals("EMP")){

                }
                context.result(user.getAcctype());
            } else{
                context.result("Login Failure");
            }
        });
        app.get("/TeaPot",context -> {
            context.result("TeaPot");

        });
    }
}


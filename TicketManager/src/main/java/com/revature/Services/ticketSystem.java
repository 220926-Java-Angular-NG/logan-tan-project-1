package com.revature.Services;
import com.revature.Repos.databaseHandler;
import com.revature.Repos.employeeHandler;
import com.revature.Utils.userHandler;
import com.revature.models.User;
import com.revature.models.login;
import io.javalin.Javalin;
import java.util.ArrayList;
import java.util.List;
public class ticketSystem {
    public static void main(String[] args) {
        databaseHandler db = new databaseHandler();
        List<userHandler> scessions = new ArrayList<>();
        List<String> loggedOn = new ArrayList<>();
        Javalin app = Javalin.create().start(8080);
        app.get("/login",context -> {
            context.result("Login Screen");
        });
        app.post("/register",context -> {
                    User user = context.bodyAsClass(User.class);
                    if(user.getAcctype().equals("EMP")){
                        if(db.registerEMP(user.getfirstName(),user.getLastName(),user.getUserName(),user.getPassword()))
                        {
                            context.result("Account created").status(200);
                        }else{
                            context.result("Process Failure").status(400);
                        }
                    } else{
                        if(db.registerMAN(user.getfirstName(),user.getLastName(),user.getUserName(),user.getPassword())){
                            context.result("Account created").status(200);
                        }else{
                            context.result("Process Failure").status(400);
                        }
                    }
                    context.redirect("/login");
                });
        app.post("/login",context -> {
            login login = context.bodyAsClass(login.class);
            userHandler temp = null;
            User user = db.login(login.getUserName(), login.getPassword());
            if(user != null  && user.getPassword().equals(login.getPassword())) {
                String path = "/"+user.getAcctype()+"/"+user.getLastName()+"/"+ user.getUID();
                if(!loggedOn.contains(user.getUserName())){
                    context.redirect(path);
                    scessions.add(new employeeHandler(app,path,db, user));
                    loggedOn.add(user.getUserName());
                } else{
                    for(userHandler scession: scessions){
                        if(scession.getPath().equals(path)){
                            temp = scession;
                            break;
                        }
                    }
                    if(temp != null){
                        context.redirect(path);
                        temp.setLoggedin(true);
                    }
                }
            } else{
                context.result("Login Failure").status(400);
            }
        });
        app.post("/logout",context -> {
            userHandler temp = null;
            login login = context.bodyAsClass(login.class);
            User user = db.findUser(login.getUserName());
            if(user != null && loggedOn.contains(user.getUserName())) {
                if(user.getAcctype().equals("EMP")){
                    String path = "/"+user.getAcctype()+"/"+user.getLastName()+"/"+ user.getUID();
                    for(userHandler scession: scessions){
                        if(scession.getPath().equals(path)){
                            temp = scession;
                            break;
                        }
                    }
                    if(temp != null){
                        temp.setLoggedin(false);
                    }
                }
                context.result("Logged Out");
            } else{
                context.result("Logout Failure");
            }
        });
        app.get("/TeaPot",context -> {
            context.result("TeaPot");

        });
    }
}


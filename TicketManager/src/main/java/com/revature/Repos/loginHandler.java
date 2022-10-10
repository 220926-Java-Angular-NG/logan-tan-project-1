package com.revature.Repos;
import com.revature.Services.employeeService;
import com.revature.Services.managerService;
import com.revature.Utils.userService;
import com.revature.models.User;
import com.revature.models.login;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class loginHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(employeeHandler.class);
    databaseHandler db = new databaseHandler();
    List<userService> scessions = new ArrayList<>();
    List<String> loggedOn = new ArrayList<>();
    Javalin app;
    public loginHandler(Javalin app){this.app = app;}
    public Handler LandingPage = context -> context.result("Login Screen");
    public Handler Registration = context -> {
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
    };
    public Handler login = context -> {
        login login = context.bodyAsClass(login.class);
        userService temp = null;
        User user = db.login(login.getUserName(), login.getPassword());
        if(user != null  && user.getPassword().equals(login.getPassword())) {
            String path = "/"+user.getAcctype()+"/"+user.getLastName()+"/"+ user.getUID();
            if(!loggedOn.contains(user.getUserName())){
                context.redirect(path);
                if(user.getAcctype().equals("EMP")){
                    scessions.add(new employeeService(app,path,db, user));
                }else {
                    scessions.add(new managerService(app,path,db, user));
                }
                loggedOn.add(user.getUserName());
            } else{
                for(userService scession: scessions){
                    if(scession.getPath().equals(path) && !scession.isLoggedin()){
                        temp = scession;
                        break;
                    }
                }
                if(temp != null){
                    context.redirect(path);
                    temp.setLoggedin(true);
                } else{
                    context.result("Login Failure").status(404);
                }
            }
        } else{
            context.result("Login Failure").status(404);
        }
    };
    public Handler logout  = context -> {
        userService temp = null;
        String param = context.pathParam("path");
        param = "/"+param; // path is saved with an inital / but pathparamerters don't
        login login = context.bodyAsClass(login.class);
        User user = db.findUser(login.getUserName());
        if(user != null && loggedOn.contains(user.getUserName())) {
            for(userService scession: scessions){
                if(scession.getPath().equals(param)){
                    temp = scession;
                    break;
                }
            }
            if(temp != null){
                temp.setLoggedin(false);
            }
            context.result("Logged Out");
        } else{
            context.result("Logout Failure");
        }
    };
}

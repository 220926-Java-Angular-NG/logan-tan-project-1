package com.revature.Repos;
import com.revature.Services.employeeService;
import com.revature.Services.managerService;
import com.revature.Utils.userService;
import com.revature.models.User;
import com.revature.models.login;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.List;

public class loginHandler {
    databaseHandler db = new databaseHandler();
    List<userService> scessions = new ArrayList<>();
    List<String> loggedOn = new ArrayList<>();
    Javalin app;
    public loginHandler(Javalin app){this.app = app;}
    public Handler LandingPage = context -> context.result("Login Screen");
    public Handler Registration = context -> {
        User user = context.bodyAsClass(User.class);
        boolean UsableStrings = true;
        String userNameTemp = "";
        String passwordTemp = "";
        String firstNameTemp = "";
        String lastnameTemp = "";
        if(user == null){
            UsableStrings = false;
            context.redirect("/body_not_found");
        }
        if(UsableStrings){
            userNameTemp = CleanString(user.getUserName());
            passwordTemp = CleanString(user.getPassword());
            firstNameTemp = CleanString(user.getfirstName());
            lastnameTemp = CleanString(user.getLastName());
            if(!(user.getUserName().equals(userNameTemp)&&user.getPassword().equals(passwordTemp)&&
                user.getfirstName().equals(firstNameTemp)&&user.getLastName().equals(lastnameTemp))){
                context.redirect("/Illegal_Characters_Used_In_Body");
                UsableStrings = false;

            }
            //the lines above check to see if there were any illegal characters in any of the passed strings
        }
        if(UsableStrings){
            if(!(userNameTemp.length() >= 2 && passwordTemp.length() >= 2 && firstNameTemp.length() >0 && lastnameTemp.length() >0)){
                context.redirect("/Illegal_Argument_Length_In_Body");
                UsableStrings = false;
            }
            // checks for username and password length
        }
        if(UsableStrings) {
            if (db.registerUsr(user.getfirstName(), user.getLastName(), user.getUserName(), user.getPassword(),user.getAcctype())) {
                context.result("Account created").status(200);
                context.redirect("/login");
            } else {
                context.result("Process Failure").status(400);
                context.redirect("/User_Already_Exists");
            }
        }
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

    public Handler Promote = context ->{
        boolean contine = true;
        User target = db.findUser(context.pathParam("target"));
        contine = !(target == null);
        if(contine) {
            String pathT = "/" + target.getAcctype() + "/" + target.getLastName() + "/" + target.getUID();
            User Auth = db.findUser(context.pathParam("authorizer"));
            contine = !(Auth == null);
            if(contine) {
                String pathA = "/" + Auth.getAcctype() + "/" + Auth.getLastName() + "/" + Auth.getUID();
                if (loggedOn.contains(target.getUserName())) { // check to see if they have an active scession
                    for (userService scession : scessions) {
                        if (scession.getPath().equals(pathT)) {
                            scession.setLoggedin(false); // force log them out if they do have a scession
                            break;
                        }
                    }
                }
                for (userService scession : scessions) {
                    if (scession.getPath().equals(pathA)) { // find the Authenticator
                        if(scession.isLoggedin()){ // see if Authenticator is logged in
                            db.updateUser(target.getUID());
                            context.result("Promoted");
                        } else{
                            context.result("Authenticator is not logged in");
                        }
                        break;
                    }
                }

            } else{
                context.result("Authenticator doesn't exist");
            }
        }else{
            context.result("Target for promotion doesn't exist");
        }

    };

    public String CleanString(String input){
        return input.replaceAll("[^a-zA-Z0-9-]","");
    }
}

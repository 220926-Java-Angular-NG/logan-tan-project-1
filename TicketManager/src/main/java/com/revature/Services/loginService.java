package com.revature.Services;
import com.revature.Repos.loginHandler;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class loginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(loginService.class);
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        loginHandler lnh = new loginHandler(app);
        app.get("/login",lnh.LandingPage);
        app.post("/register",lnh.Registration);
        app.post("/login",lnh.login);
        app.post("<path>/logout", lnh.logout);
        app.get("/body_not_found",context -> context.result("body_not_found"));
        app.get("/Illegal_Characters_Used_In_Body",context -> context.result("Illegal_Characters_Used_In_Body"));
        app.get("/Illegal_Argument_Length_In_Body",context -> context.result("Illegal_Argument_Length_In_Body"));
        app.get("/User_Already_Exists",context -> context.result("User_Already_Exists"));
        app.patch("/Promote/{target}/{authorizer}", lnh.Promote);



        app.get("/TeaPot",context -> { //Ignore the dummy test
            context.result("TeaPot").status(418);

        });
    }
}


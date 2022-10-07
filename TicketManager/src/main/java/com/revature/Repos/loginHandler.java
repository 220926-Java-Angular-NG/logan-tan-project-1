package com.revature.Repos;
import com.revature.Services.loginService;
import io.javalin.Javalin;
public class loginHandler {
    loginService LoginService;
    public loginHandler(){
        LoginService = new loginService();
    }
}

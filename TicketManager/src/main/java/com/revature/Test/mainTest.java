package com.revature.Test;
import com.revature.Services.loginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class mainTest {
    @Test
    public void connectionTest(){
        loginService.main(null);
    }
}

package com.revature.Test;
import com.revature.Services.ticketSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class mainTest {
    @Test
    public void connectionTest(){
        ticketSystem.main(null);
    }
}

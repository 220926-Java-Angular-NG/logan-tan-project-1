package com.revature.Repos;
import com.revature.Utils.Handler;
import com.revature.models.Ticket;

public class managerHandler implements Handler {
    @Override
    public void ViewTickets() {

    }

    @Override
    public boolean ApproveDenyTicket(Ticket ticket) {
        return false;
    }

    @Override
    public boolean AddTicket(Ticket ticket) {
        return false;
    }

    @Override
    public void Logout() {

    }
}

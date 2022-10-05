package com.revature.Utils;

import com.revature.models.Ticket;

public interface Handler {
    void ViewTickets();
    boolean ApproveDenyTicket(Ticket ticket);
    boolean AddTicket(Ticket ticket);
    void Logout();
}

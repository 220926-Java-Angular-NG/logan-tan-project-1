public class EmplyeeManager implements Manager {
    Users user;
    public EmplyeeManager(Users usr){
        user = usr;
    }

    public void ViewTickets() {

    }

    public void ApproveDenyTicket(Ticket ticket) {

    }

    public void AddTicket(Ticket ticket) {
        Ticket[] newtickets = new Ticket[user.tickets.length + 1];
        System.arraycopy(user.tickets, 0, newtickets, 0, user.tickets.length);
        newtickets[user.tickets.length] = ticket;
        user.tickets = newtickets;
    }
}

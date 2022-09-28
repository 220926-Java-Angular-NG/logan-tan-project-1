public class EmplyeeManager implements Manager {
    Users user;
    public EmplyeeManager(Users usr){
        user = usr;
    }

    public void ViewTickets() {
        for(int i = 0; i < user.tickets.length; i++){
            user.tickets[i].display();
        }
    }

    public void ApproveDenyTicket(Ticket ticket) {
        System.out.println("You don't have permission");
    }

    public void AddTicket(Ticket ticket) {
        Ticket[] newtickets = new Ticket[user.tickets.length + 1];
        System.arraycopy(user.tickets, 0, newtickets, 0, user.tickets.length);
        newtickets[user.tickets.length] = ticket;
        user.tickets = newtickets;
    }
}

public class Ticket {
    float Reimburstment;
    String Disc;
    short status; // 0 = pending, 1 = accepted, -1 = rejected
    public Ticket(float amt, String Des){
        Reimburstment = amt;
        Disc = Des;
    }
}

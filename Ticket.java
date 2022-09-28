public class Ticket {
    float Reimburstment;
    String Disc;
    short status = 0; // 0 = pending, 1 = accepted, 2 = rejected
    public Ticket(float amt, String Des){
        Reimburstment = amt;
        Disc = Des;
    }
    public void display(){
        System.out.println("Amount: " + Reimburstment);
        System.out.println(Disc);
        switch (status) {
            case 0 -> System.out.println("Pending");
            case 1 -> System.out.println("Accepted");
            case 2 -> System.out.println("Rejected");
            default -> {}
        }
    }
}

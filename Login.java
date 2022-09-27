public class Login { // This class should handel log in procedures
    public Login(){
        Database DB = new Database();
    }
    int GetID(String ID, String Password){
        return 0;
    }

    public static void main(String args[]) {
        Login login = new Login();

        System.out.println(login.GetID("ABC", "AAA"));
    }
}


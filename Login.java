public class Login { // This class should handel log in procedures
    Database DB = new Database();
    public Login(){
    }
    public String registster(String usr, String psw, int acctype){ // registration funct
        Users temp = DB.find(usr);
        if(temp == null){
            return "Error: username already taken";
        } else {
            DB.createUser(usr,psw,acctype);
            return "created";
        }
    }
    int GetID(String ID, String Password){ // check if user exists and has correct password
        Users temp = DB.find(ID);
        if(temp.acctype != -1 && temp.Pass.equals(Password)){
            return 1; // ID verified
        } else {
            return 0; // wrong
        }
    }

    public static void main(String args[]) {
        Login login = new Login();
        System.out.println(login.registster("ABC","AAA", 0));
        System.out.println(login.GetID("ABC", "AAA"));
    }
}


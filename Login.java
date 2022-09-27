public class Login { // This class should handel log in procedures
    Database DB = new Database();
    Manager session;
    public Login(){
    }
    public int registster(String usr, String psw){ // registration funct
        Users temp = DB.find(usr);
        if(temp != null){
            return -1;
        } else {
            DB.createUser(usr,psw,0);
            return 0;
        }
    }
    public int registster(String usr, String psw, int acctype){ // registration funct
        Users temp = DB.find(usr);
        if(temp != null){
            return -1; // error, user already exists
        } else {
            DB.createUser(usr,psw,acctype);
            return 0; // successfully created acc
        }
    }
    Users GetID(String ID, String Password){ // check if user exists and has correct password
        Users temp = DB.find(ID);
        if(temp != null && temp.Pass.equals(Password)){
            return temp; // ID verified, return the account type, to be processed by the state classes
        } else {
            return null; // wrong
        }
    }

    public static void main(String args[]) {
        Login login = new Login();
        login.registster("AAA","AAA", 1);
        login.registster("ABC","AAA", 1);
        if(0 != login.registster("ABC","AAA")){
            System.out.println("Error Username already found");
        }
        login.registster("AEC","AAA");
        login.registster("ACDC","AAA");
        Users user = login.GetID("ACDC", "AAA");
        if(user == null){
            System.out.println("Login failed ");
        }else if(user.acctype == 0){
            System.out.print("Welcome Employee ");
            System.out.println(user.ID);
        }else if(user.acctype == 1){
            System.out.print("Welcome Manager ");
            System.out.println(user.ID);
        }
    }
}


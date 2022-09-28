public class Login { // This class should handel log in procedures
    Database DB = new Database();
    Manager session = null;
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
    Manager GetID(String ID, String Password){ // check if user exists and has correct password
        if(session != null){
            System.err.println("Your already logged in");
            return session;
        }
        Users temp = DB.find(ID);
        if(temp != null && temp.Pass.equals(Password)){
            // ID verified, return the account type, to be processed by the state classes
            if(temp.acctype == 0){
                return new EmplyeeManager(temp);
            } else if (temp.acctype == 1){
                return new ManagerManager(temp);
            }
        }
        return null; // Wrong username or password
    }
    public void logout(){
        session = null;
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
        Manager Session = login.GetID("ACDC", "AAA");

    }
}


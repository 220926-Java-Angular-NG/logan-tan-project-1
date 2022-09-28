import jdk.jfr.Unsigned;

public class Database {
    Users root = null;
    public Database() {
        //if there was a file to load initialize here

    }

    public void createUser(String UID, String Pass, int type) {
        Users temp = new Users(UID, Pass, type);
        add(temp, root);
    }

    private void add(Users ad, Users loc) {
        if(root == null){
            root = ad;
            return;
        } else {
            int cmp = ad.ID.compareTo(loc.ID);
            if(cmp == 0){
                System.err.println("You should not have read this");
                return; // error case, due to this being a helper function this should never run
            } else if (cmp > 0) {
                if(loc.chldL == null){ // base case
                    loc.chldL = ad;
                    return;
                }
                loc = loc.chldL;
                add(ad,loc); // recurse
            } else{
                if(loc.chldR == null){ // base case
                    loc.chldR = ad;
                    return;
                }
                loc = loc.chldR;
                add(ad,loc); // recurse
            }
        }
    }
    public Users find(String username){
        if(root == null) {
            return null;
        }
        return findhelper(username,root);
    }
    private Users findhelper(String usr, Users loc){
        if(loc == null){
            return null; // base case
        }
        int cmp = usr.compareTo(loc.ID);
        if(cmp == 0){
            return loc; // base case
        }
        if(cmp > 0){
            loc = loc.chldL; // update
        } else{
            loc = loc.chldR; // update
        }
        return findhelper(usr, loc); // recurse
    }

}

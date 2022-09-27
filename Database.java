public class Database {
    Users root = null;
    public Database() {
        //if there was a file to load initialize here
    }

    public void createUser(String UID, String Pass, int type) {
        Users temp = new Users(UID, Pass, type);
        add(temp, root);
    }

    public void add(Users ad, Users loc) {
        if(root == null){
            root = ad;
        } else {
            int cmp = ad.ID.compareTo(loc.ID);
            if(cmp == 0){
                return; // error case, due to this being a helper function this should never run
            } else if (cmp > 0) {
                if(loc.chldL == null){ // base case
                    loc.chldL = ad;
                    return;
                }
                loc = loc.chldL;
            } else{
                if(loc.chldR == null){ // base case
                    loc.chldR = ad;
                    return;
                }
                loc = loc.chldR;
            }
            add(ad,loc); // recurse
        }
    }
    public Users find(String username){
        if(root == null) {
            return null;
        }
        return findhelper(username,root);
    }
    public Users findhelper(String usr, Users loc){
        int cmp = loc.ID.compareTo(usr);
        if(cmp == 0){
            return loc;
        } else if(cmp > 0){
            if (loc.chldL == null){
                return null;
            }
            loc = loc.chldL;
        } else{
            if (loc.chldR == null){
                return null;
            }
            loc = loc.chldR;
        }
        return findhelper(usr, loc);
    }

}

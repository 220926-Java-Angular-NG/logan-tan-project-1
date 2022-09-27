public class Database {
    public Database() {
        //if there was a file to load initialize here
    }

    public void createUser(String UID, String Pass, int type) {
        Users temp = new Users(UID, Pass, type);
        add(temp);
    }

    public void add(Users ad) {

    }
    public Users find(String username){
        return null;
    }
}

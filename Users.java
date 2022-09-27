public class Users { // Node like structure that will store information used
    String ID; // stores Usernames will be used as the sorting variable
    String Pass; // Identifier
    int acctype; // 0 is employee, 1 is manager

    Users chldL = null; //left child for BST implamentation
    Users chldR = null; //right child
    public Users(String UID, String Password, int type){
        ID = UID;
        Pass = Password;
        acctype = type;
    }
}

package com.revature.Repos;

import com.revature.Utils.ConnectionManager;
import com.revature.models.Ticket;
import com.revature.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class databaseHandler { // handels database quries
    private static final Logger LOGGER = LoggerFactory.getLogger(databaseHandler.class);
    Connection connection = null;
    PreparedStatement act = null;
    String Query = null;
    ResultSet res = null;
    public databaseHandler() {
        try {
            connection = ConnectionManager.getConn();
            act = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Accounts (firstName VARCHAR(40) not null, lastName VARCHAR(40) not null,Password VARCHAR(40) not null, userName VARCHAR(40) not null unique, pfp bytea, email VARCHAR(200), address VARCHAR(200),EID SERIAL PRIMARY KEY, accType VARCHAR(3) DEFAULT 'EMP')");
            act.execute();
            act = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Tickets (Amount VARCHAR(12), Description VARCHAR(140), Status VARCHAR(3) DEFAULT 'PEN', Type VARCHAR(3) DEFAULT 'ECT', eid INT not null, img bytea,TID Serial PRIMARY KEY, CONSTRAINT fk_Account FOREIGN KEY (EID) REFERENCES Accounts (EID))");
            act.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
    public databaseHandler(Connection connection) {
        this.connection = connection;

    }
    public boolean registerUsr(String firstName, String lastName, String userName,String Password,String acctype) throws SQLException {
        Query = "Select userName from Accounts where userName = ?;";
        act = connection.prepareStatement(Query);
        act.setString(1,userName);
        try {
            res = act.executeQuery();
        } catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
        if(!res.next()){
            Query = ("INSERT INTO Accounts (firstName,lastName,userName,Password,acctype) VALUES (?,?,?,?,?);");
            act = connection.prepareStatement(Query);
            act.setString(1,firstName);
            act.setString(2,lastName);
            act.setString(3,userName);
            act.setString(4,Password);
            act.setString(5,acctype);
            act.execute();
            return true; // The Username doesn't already exist adding to DB
        }
        return false; // The name exists was not added returing now
    }

    public User login(String userName, String Password) throws SQLException {
        Query = "Select * from Accounts where userName = ?;";
        act = connection.prepareStatement(Query);
        act.setString(1,userName);
        try {
            res = act.executeQuery();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
        if(!res.next()){
            return null; // There is no user with this userName
        } else{
            String userPassword = res.getString("Password");
            if(userPassword.equals(Password)){ //check that passwords match
                return new User(res.getString("firstname"),res.getString("lastname"),res.getString("username"),res.getString("password"),res.getString("acctype"),res.getInt("eid"),res.getBytes("pfp"));
            } else{
                return null; // Password didn't match
            }
        }
    }
    public User findUser(String userName) throws SQLException{
        try {
            Query = "Select * from Accounts where userName = ?;";
            act = connection.prepareStatement(Query);
            act.setString(1, userName);
            res = act.executeQuery();
        } catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
        if(!res.next()){
            return null; // There is no user with this userName
        } else {
            return new User(res.getString("firstname"),res.getString("lastname"),res.getString("username"),"",res.getString("acctype"),res.getInt("eid"),res.getBytes("pfp"));
        }
    }

    public void promoteUser(int eid) {
        Query = "update accounts SET acctype ='MAN' where eid = ? and acctype = 'EMP'";
        try{
            act = connection.prepareStatement(Query);
            act.setInt(1,eid);
            act.execute();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
    }

    public void customizeUser(User user) {
        Query = "update accounts SET firstname = ?, lastname = ?, email = ?, address = ? where eid = ?";
        try{
            act = connection.prepareStatement(Query);
            act.setString(1,user.getfirstName());
            act.setString(2,user.getLastName());
            act.setString(3,user.getEmail());
            act.setString(4,user.getAddress());
            act.setInt(5,user.getUID());
            act.execute();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
    }

    public void addUserIMG(int eid,byte[] bin) {
        Query = "update accounts SET pfp =? where eid = ?";
        try{
            act = connection.prepareStatement(Query);
            act.setBytes(1,bin);
            act.setInt(2,eid);
            act.execute();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
    }















    //Ticket management possibly could be refactored into a different class
    public void addTicket(Ticket ticket, int eid) throws SQLException {
        try{
    Query = "INSERT INTO Tickets (amount, description, status, eid, Type) VALUES (?,?,?,?,?)";
    act = connection.prepareStatement(Query);
    act.setString(1,String.valueOf(ticket.getReimburstment()));
    act.setString(2, ticket.getDisc());
    act.setString(3, ticket.getStatus());
    act.setInt(4,eid);
    act.setString(5,ticket.getType().getCode());
    act.execute();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
    }
    public void addRecitTicket(int eid, int tid,byte[] bin){
        try{

        Query = "update tickets SET img =? where tid = ? and eid = ?";
        act = connection.prepareStatement(Query);
        act.setBytes(1,bin);
        act.setInt(3,eid);
        act.setInt(2,tid);
        act.execute();
        } catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
    }
    public List<Ticket> viewTickets(String tid,String who, String status, String type,boolean manager) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try{
            if(manager){ // if a manager is simply reviewing tickets they should not see their own
                Query = "select * from tickets t join accounts a on a.eid = t.eid where cast(t.eid as text) not like ? and t.status like ? and t.type like ? and cast(t.tid as text) like ?";
            }else{
                Query = "select * from tickets t join accounts a on a.eid = t.eid where cast(t.eid as text) like ? and t.status like ? and t.type like ? and cast(t.tid as text) like ?";
            }
        act = connection.prepareStatement(Query);
        act.setString(1,who);
        act.setString(2,status);
        act.setString(3,type);
        act.setString(4,tid);
        ResultSet rs = act.executeQuery();
        while(rs.next()){
            tickets.add(new Ticket(Float.parseFloat(rs.getString("amount")),rs.getString("description"),rs.getString("status"), rs.getString("firstname")+" "+rs.getString("lastname"), rs.getInt("tid"),rs.getString("type"),rs.getBytes("img")));
        }
        }catch(SQLException e){
            LOGGER.error(e.getMessage());
        }
        return tickets;
    }
    public boolean AprDenTicket(String state, int id, int managerID) {
        Query = "select from tickets where tid = ? and status = 'PEN' and cast(eid ast text) not like ?"; // sanity check
        try{
            act = connection.prepareStatement(Query);
            act.setInt(1,id);
            act.setString(2, String.valueOf(managerID));
            ResultSet rs = act.executeQuery();
            if(!rs.next()){
                return false;
            }
    }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
        Query = "update tickets SET status =? where tid = ? and status = 'PEN'";
        try{
        act = connection.prepareStatement(Query);
        act.setString(1,state);
        act.setInt(2,id);
            act.execute();
        }catch (SQLException e){
            LOGGER.error(e.getMessage());
        }
        return true;
    }
}

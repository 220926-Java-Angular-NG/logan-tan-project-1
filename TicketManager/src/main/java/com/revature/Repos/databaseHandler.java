package com.revature.Repos;

import com.revature.models.Ticket;
import com.revature.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class databaseHandler { // handels database quries
    Connection connection = null;
    PreparedStatement act = null;
    String Query = null;
    ResultSet res = null;
    public databaseHandler() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=ticketdb", "postgres", "Shining");
            act = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Accounts (firstName VARCHAR(40) not null, lastName VARCHAR(40) not null,Password VARCHAR(40) not null, userName VARCHAR(40) not null unique,EID SERIAL PRIMARY KEY, accType VARCHAR(3) DEFAULT 'EMP')");
            act.execute();
            act = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Tickets (Amount VARCHAR(12), Description VARCHAR(140), Status VARCHAR(6), eid INT not null, TID Serial PRIMARY KEY, CONSTRAINT fk_Account FOREIGN KEY (EID) REFERENCES Accounts (EID))");
            act.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public boolean registerEMP(String firstName, String lastName, String userName,String Password) throws SQLException {
        Query = "Select userName from Accounts where userName = ?;";
        act = connection.prepareStatement(Query);
        act.setString(1,userName);
        res = act.executeQuery();
        if(!res.next()){
            Query = ("INSERT INTO Accounts (firstName,lastName,userName,Password) VALUES (?,?,?,?);");
            act = connection.prepareStatement(Query);
            act.setString(1,firstName);
            act.setString(2,lastName);
            act.setString(3,userName);
            act.setString(4,Password);
            act.execute();
            return true; // The Username doesn't already exist adding to DB
        }
        return false; // The name exists was not added returing now
    }

    public boolean registerMAN(String firstName, String lastName, String userName, String password) throws SQLException {
        Query = "Select userName from Accounts where userName = ?;";
        act = connection.prepareStatement(Query);
        act.setString(1,userName);
        res = act.executeQuery();
        if(!res.next()){
            Query = ("INSERT INTO Accounts (firstName,lastName,userName,Password,acctype) VALUES (?,?,?,?,'MAN');");
            act = connection.prepareStatement(Query);
            act.setString(1,firstName);
            act.setString(2,lastName);
            act.setString(3,userName);
            act.setString(4,password);
            act.execute();
            return true; // The Username doesn't already exist adding to DB
        }
        return false; // The name exists was not added returing now
    }

    public User login(String userName, String Password) throws SQLException {
        Query = "Select * from Accounts where userName = ?;";
        act = connection.prepareStatement(Query);
        act.setString(1,userName);
        res = act.executeQuery();
        if(!res.next()){
            return null; // There is no user with this userName
        } else{
            String userPassword = res.getString("Password");
            if(userPassword.equals(Password)){ //check that passwords match
                return new User(res.getString("firstname"),res.getString("lastname"),res.getString("username"),res.getString("password"),res.getString("acctype"),res.getInt("eid"));
            } else{
                return null; // Password didn't match
            }
        }
    }
    public User findUser(String userName) throws SQLException{
        Query = "Select * from Accounts where userName = ?;";
        act = connection.prepareStatement(Query);
        act.setString(1,userName);
        res = act.executeQuery();
        if(!res.next()){
            return null; // There is no user with this userName
        } else {
            return new User(res.getString("firstname"),res.getString("lastname"),res.getString("username"),"",res.getString("acctype"),res.getInt("eid"));
        }
    }
    public void addTicket(Ticket ticket, int eid) throws SQLException {
        try{
    Query = "INSERT INTO Tickets (amount, description, status,eid) VALUES (?,?,?,?)";
    act = connection.prepareStatement(Query);
    act.setString(1,String.valueOf(ticket.getReimburstment()));
    act.setString(2, ticket.getDisc());
    act.setString(3, ticket.getStatus());
    act.setInt(4,eid);
    act.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public List<Ticket> viewTickets(String who, String status) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try{
        Query = "select * from tickets t join accounts a on a.eid = t.eid where cast(t.eid as text) like ? and status like ? ";
        act = connection.prepareStatement(Query);
        act.setString(1,who);
        act.setString(2,status);
        ResultSet rs = act.executeQuery();
        while(rs.next()){
            tickets.add(new Ticket(Float.parseFloat(rs.getString("amount")),rs.getString("description"),rs.getString("status")));
        }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return tickets;
    }
}

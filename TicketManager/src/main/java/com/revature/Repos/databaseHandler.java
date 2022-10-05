package com.revature.Repos;

import com.revature.models.User;

import java.sql.*;

public class databaseHandler { // handels database quries
    Connection connection = null;
    PreparedStatement act = null;
    String Query = null;
    ResultSet res = null;
    public databaseHandler() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=ticketdb", "postgres", "Shining");
            act = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Account (firstName VARCHAR(40) not null, lastName VARCHAR(40) not null,Password VARCHAR(40) not null, userName VARCHAR(40) not null unique,EID SERIAL PRIMARY KEY, accType VARCHAR(3) DEFAULT 'EMP')");
            act.execute();
            act = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Tickets (Amount float, Description VARCHAR(140), Status VARCHAR(6), eid INT not null, TID Serial PRIMARY KEY, CONSTRAINT fk_Account FOREIGN KEY (EID) REFERENCES Account (EID))");
            act.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public boolean registerEMP(String firstName, String lastName, String userName,String Password) throws SQLException {
        Query = "Select userName from Account where userName = ?;";
        act = connection.prepareStatement(Query);
        act.setString(1,userName);
        res = act.executeQuery();
        if(!res.next()){
            Query = ("INSERT INTO Account (firstName,lastName,userName,Password) VALUES (?,?,?,?);");
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
        Query = "Select userName from Account where userName = ?;";
        act = connection.prepareStatement(Query);
        act.setString(1,userName);
        res = act.executeQuery();
        if(!res.next()){
            Query = ("INSERT INTO Account (firstName,lastName,userName,Password,acctype) VALUES (?,?,?,?,'MAN');");
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
        Query = "Select * from Account where userName = ?;";
        act = connection.prepareStatement(Query);
        act.setString(1,userName);
        res = act.executeQuery();
        if(!res.next()){
            return null; // There is no user with this userName
        } else{
            String userPassword = res.getString("Password");
            if(userPassword.equals(Password)){ //check that passwords match
                return new User(res.getString("firstname"),res.getString("lastname"),res.getString("username"),res.getString("password"),res.getString("acctype"));
            } else{
                return null; // Password didn't match
            }
        }

    }
}

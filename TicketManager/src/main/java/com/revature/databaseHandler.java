package com.revature;

import java.sql.*;

public class databaseHandler { // handels database quries
    Connection connection = null;
    Statement act = null;
    String Query = null;
    ResultSet res = null;
    public databaseHandler() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=ticketdb", "postgres", "Shining");
            act = connection.createStatement();
            act.execute("CREATE TABLE IF NOT EXISTS Account (firstName VARCHAR(40) not null, lastName VARCHAR(40) not null,Password VARCHAR(40) not null, userName VARCHAR(40) not null unique,EID SERIAL PRIMARY KEY, accType VARCHAR(3) DEFAULT 'EMP')");
            act.execute("CREATE TABLE IF NOT EXISTS Tickets (Amount float, Description VARCHAR(140), Status VARCHAR, TID Serial PRIMARY KEY, Owner int)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean registerUser(String firstName, String lastName, String userName,String Password) throws SQLException {
        Query = "Select userName from Account where userName = '" + userName +"';";
        res = act.executeQuery(Query);
        if(!res.next()){
            Query = "INSERT INTO Account (firstName,lastName,userName,Password) VALUES ('" + firstName +"','"+ lastName +"','"+userName+"','"+ Password+"');";
            act.execute(Query);
            return true; // The Username doesn't already exist adding to DB
        }
        return false; // The name exists was not added returing now
    }

    public User login(String userName, String Password) throws SQLException {
        Query = "Select * from Account where userName = '" + userName +"';";
        res = act.executeQuery(Query);
        if(!res.next()){
            return null; // There is no user with this userName
        } else{
            String userPassword = res.getString("Password");
            if(userPassword.equals(Password)){
                return new User(res.getString("firstname"),res.getString("lastname"),res.getString("username"),res.getString("password"),res.getString("acctype"));
            } else{
                return null; // Password didn't match
            }
        }

    }
}

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
            act.execute("CREATE TABLE IF NOT EXISTS Account (firstName VARCHAR(40), lastName VARCHAR(40),Password VARCHAR(40), EID SERIAL PRIMARY KEY, accType int)");
            act.execute("CREATE TABLE IF NOT EXISTS Tickets (Amount float, Description VARCHAR(140), Status VARCHAR, TID Serial PRIMARY KEY, Owner int)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean registerUser(String Name, String Password) throws SQLException {
        Query = "Select Name from Account where Name = '" + Name +"';";
        res = act.executeQuery(Query);
        if(!res.next()){
            Query = "INSERT INTO Account (Name,Password) VALUES ('" + Name +"','" + Password+"');";
            act.execute(Query);
            return true; // The Name doesn't already exist
        }
        return false; // The name exists was not added
    }
}

package com.revature;

import java.sql.*;

public class databaseHandler {
    Connection connection = null;
    Statement act = null;
    String Query = null;
    public databaseHandler() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Shining");
            act = connection.createStatement();
            act.execute("CREATE TABLE IF NOT EXISTS Account (Name VARCHAR(40), Password VARCHAR(40))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insert(String Name, String Password) throws SQLException {
        Query = "INSERT INTO Account (Name,Password) VALUES ('" + Name +"','" + Password+"');";
        act.execute(Query);
    }
}

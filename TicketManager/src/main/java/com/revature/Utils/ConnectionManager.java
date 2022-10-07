package com.revature.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager { //manages the connection to the database, though use of singleton

    private static Connection conn;
    private static Properties properties;

    private ConnectionManager(){}

    private static Properties loadProp(){
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("TIcketManager/src/main/resources/dbConfig.properties");
            properties.load(fileInputStream);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return properties;
    }

    public static Connection getConn() throws SQLException {
        if(properties == null){
            properties = loadProp();
        }
        if(conn == null || conn.isClosed()){
            conn = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
        }
        return conn;
    }
}

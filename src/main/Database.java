package main;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
Connection connection;
public Connection getConnection(){
    if (connection == null){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator", "root","3306");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    return connection;
}
public void close() throws SQLException {
    if (connection != null){
        connection.close();
        connection = null;
    }

}
}


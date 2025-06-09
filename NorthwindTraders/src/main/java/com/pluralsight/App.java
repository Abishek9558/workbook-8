package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup";

        //connection to the database
        Connection connection;
        connection = DriverManager.getConnection(url, username, password);

        // Create statement
        Statement statement = connection.createStatement();

        // Define your query
        String query = "SELECT ProductName FROM Products";

        // 2. Execute your query
        ResultSet results = statement.executeQuery(query);

        // Process the results
        while (results.next()) {
            String city = results.getString("ProductName");
            System.out.println(city);
        }

        // 3. Close the connection
        results.close();
        statement.close();
        connection.close();
    }
}
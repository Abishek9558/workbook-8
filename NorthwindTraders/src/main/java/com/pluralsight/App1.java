package com.pluralsight;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App1 {
    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup";


        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        ResultSet results = statement.executeQuery(query);

        System.out.printf("%-5s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
        System.out.println("-------------------------------------------------------------");

        while (results.next()) {
            int id = results.getInt("ProductID");
            String name = results.getString("ProductName");
            double price = results.getDouble("UnitPrice");
            int stock = results.getInt("UnitsInStock");

            System.out.printf("%-5d %-30s $%-9.2f %-10d%n", id, name, price, stock);
        }
        results.close();
        statement.close();
        connection.close();
    }
}

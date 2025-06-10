package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class App2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {

            String url = "jdbc:mysql://localhost:3306/northwind";
            String username = "root";
            String password = "yearup";

            connection = DriverManager.getConnection(url, username, password);

            // Menu loop
            int choice = -1;
            while (choice != 0) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        displayProducts(connection);
                        break;
                    case 2:
                        displayCustomers(connection);
                        break;
                    case 0:
                        System.out.println("Exiting program.");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error closing connection.");
            }
        }
    }

    private static void displayProducts(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";
            ResultSet results = statement.executeQuery(query);

            System.out.printf("\n%-5s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
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
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }

    private static void displayCustomers(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";
            ResultSet results = statement.executeQuery(query);

            System.out.printf("\n%-25s %-30s %-20s %-15s %-15s%n", "Contact Name", "Company Name", "City", "Country", "Phone");
            System.out.println("-----------------------------------------------------------------------------------------------");

            while (results.next()) {
                String contact = results.getString("ContactName");
                String company = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");

                System.out.printf("%-25s %-30s %-20s %-15s %-15s%n", contact, company, city, country, phone);
            }

            results.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }
}
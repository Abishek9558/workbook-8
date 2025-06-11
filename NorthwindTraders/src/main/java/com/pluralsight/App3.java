package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            int choice = -1;

            while (choice != 0) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
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
                    case 3:
                        displayCategoriesAndProducts(connection, scanner);
                        break;
                    case 0:
                        System.out.println("Exiting program.");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void displayProducts(Connection connection) {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try (
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(query)
        ) {
            System.out.printf("\n%-5s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("-------------------------------------------------------------");

            while (results.next()) {
                int id = results.getInt("ProductID");
                String name = results.getString("ProductName");
                double price = results.getDouble("UnitPrice");
                int stock = results.getInt("UnitsInStock");

                System.out.printf("%-5d %-30s $%-9.2f %-10d%n", id, name, price, stock);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }

    private static void displayCustomers(Connection connection) {
        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";

        try (
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(query)
        ) {
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
        } catch (Exception e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }

    private static void displayCategoriesAndProducts(Connection connection, Scanner scanner) {
        String categoriesQuery = "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID";

        try (
                Statement categoryStmt = connection.createStatement();
                ResultSet categories = categoryStmt.executeQuery(categoriesQuery)
        ) {
            System.out.printf("\n%-5s %-30s%n", "ID", "Category Name");
            System.out.println("--------------------------------");

            while (categories.next()) {
                int id = categories.getInt("CategoryID");
                String name = categories.getString("CategoryName");
                System.out.printf("%-5d %-30s%n", id, name);
            }

            System.out.print("\nEnter a Category ID to view products: ");
            int categoryId = Integer.parseInt(scanner.nextLine());

            String productsQuery = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                    "FROM Products WHERE CategoryID = ?";

            try (
                    PreparedStatement productStmt = connection.prepareStatement(productsQuery)
            ) {
                productStmt.setInt(1, categoryId);
                try (ResultSet products = productStmt.executeQuery()) {
                    System.out.printf("\n%-5s %-30s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
                    System.out.println("-------------------------------------------------------------");

                    while (products.next()) {
                        int id = products.getInt("ProductID");
                        String name = products.getString("ProductName");
                        double price = products.getDouble("UnitPrice");
                        int stock = products.getInt("UnitsInStock");

                        System.out.printf("%-5d %-30s $%-9.2f %-10d%n", id, name, price, stock);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error retrieving categories or products: " + e.getMessage());
        }
    }
}
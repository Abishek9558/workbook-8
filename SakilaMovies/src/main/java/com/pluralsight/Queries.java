package com.pluralsight;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;

public class Queries {

    private final DataSource dataSource;

    public Queries(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void findActorsByLastName(String lastName) {
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name LIKE ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + lastName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("ID: %d - %s %s%n",
                            rs.getInt("actor_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"));
                }
                if (!found) {
                    System.out.println("No actors found with last name: " + lastName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding actors: " + e.getMessage());
        }
    }

    public void findFilmsByActor(String firstName, String lastName) {
        String sql = """
                SELECT f.title 
                FROM film f
                JOIN film_actor fa ON f.film_id = fa.film_id
                JOIN actor a ON a.actor_id = fa.actor_id
                WHERE a.first_name = ? AND a.last_name = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                System.out.println("Movies starring " + firstName + " " + lastName + ":");
                while (rs.next()) {
                    found = true;
                    System.out.println("- " + rs.getString("title"));
                }
                if (!found) {
                    System.out.println("No movies found for that actor.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving films: " + e.getMessage());
        }
    }
}
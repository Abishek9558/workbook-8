package com.pluralsight;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Queries queries = new Queries(DataSource.getDataSource());
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an actor's last name: ");
        String lastName = scanner.nextLine().trim();
        queries.findActorsByLastName(lastName);

        System.out.print("Enter an actor's first name: ");
        String firstName = scanner.nextLine().trim();


        queries.findFilmsByActor(firstName, lastName);
    }
}


package com.pluralsight;

import java.util.Scanner;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an actor's last name: ");
        String lastName = scanner.nextLine().trim();

        List<Actor> actors = dataManager.searchActorsByLastName(lastName);

        if (actors.isEmpty()) {
            System.out.println("No actors found with last name: " + lastName);
            return;
        }

        for (Actor actor : actors) {
            System.out.println(actor);
        }

        System.out.print("Enter the ID of the actor to see their films: ");
        int actorId = scanner.nextInt();

        List<Film> films = dataManager.getFilmsByActorId(actorId);

        if (films.isEmpty()) {
            System.out.println("No films found for that actor.");
        } else {
            for (Film film : films) {
                System.out.println(film);
            }
        }
    }
}
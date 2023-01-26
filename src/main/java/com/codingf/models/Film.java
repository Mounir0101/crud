package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.fonctions.Read;
import com.codingf.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Film implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String title = "";
        String description = "";
        String release_year;
        String language_id;
        String rental_duration;
        String rental_rate;
        String length;
        String replacement_cost;
        String rating;

        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        Scanner input = new Scanner(System.in);

        System.out.println("Donnez le titre du film");
        title = input.nextLine();
        System.out.println("Donnez la description du film");
        description = input.nextLine();
        System.out.println("Donnez l'année de sortie du film");
        release_year = input.nextLine();
        System.out.println("Donnez l'id correspondant à la langue du film");
        language_id = input.nextLine();
        System.out.println("Donnez la durée de location du film (en semaines)");
        rental_duration = input.nextLine();
        System.out.println("Donnez le cout de location du film");
        rental_rate = input.nextLine();
        System.out.println("Donnez la durée du film en minutes");
        length = input.nextLine();
        System.out.println("Donnez le prix de remplacement du film en cas de perte");
        replacement_cost = input.nextLine();

        System.out.println("1 : G");
        System.out.println("2 : PG");
        System.out.println("3 : PG-13");
        System.out.println("4 : R");
        System.out.println("5 : NC-17");
        System.out.println("Quel est la classification de ce film ?");
        rating = input.nextLine();

        switch (rating) {

            case "1" -> rating = "G";
            case "2" -> rating = "PG";
            case "3" -> rating = "PG-13";
            case "4" -> rating = "R";
            case "5" -> rating = "NC-17";

        }

        try {

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM language WHERE language_id = " + language_id);

            if (!result.next()) {
                System.err.println("Il n'existe pas de langage ayant l'id " + language_id);
                System.err.println("Vous devez d'abord créer la langue correspondante");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM film");

            while (result.next()) {
                if (result.getString("title").equals(title)) {
                    System.err.println("Ce film existe déjà");
                    return true;
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating");
            columns = String.join(",", column);
            value = Arrays.asList(title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating);
            values = String.join("','", value);
            Create.create(con, "film", columns, values);

            System.out.println(green + "Le film " + title + " a bien été ajouté à la table film" + reset);

            return false;
        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }

    }

}

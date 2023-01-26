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

public class FilmCategory implements Tables {

    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String film_id = "";
        String category_id = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        Scanner input = new Scanner(System.in);

        System.out.println("Donnez l'id du film");
        film_id = input.nextLine();

        System.out.println("Donnez l'id de la catégorie du film");
        category_id = input.nextLine();

        try {

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM category WHERE category_id = '" + category_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas de catégorie ayant l'id " + category_id);
                System.err.println("Vous devez d'abord créer la catégorie correspondante");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM film WHERE film_id = '" + film_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas de film ayant l'id " + film_id);
                System.err.println("Vous devez d'abord créer le film correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM film_category");

            while (result.next()) {
                if (result.getString("category_id").equals(category_id) && result.getString("film_id").equals(film_id)) {
                    System.err.println("Cette catégorie est déjà affectée à ce film");
                    return true;
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("film_id", "category_id");
            columns = String.join(",", column);
            value = Arrays.asList(film_id, category_id);
            values = String.join("','", value);
            Create.create(con, "film_category", columns, values);

            result = statement.executeQuery("SELECT name FROM category WHERE category_id = '" + category_id + "'");

            String category_name = "";

            if (result.next()) {
                category_name = result.getString("name");
            }

            result = statement.executeQuery("SELECT title FROM film WHERE film_id = '" + film_id + "'");

            String film_name = "";

            if (result.next()) {
                film_name = result.getString("title");
            }

            System.out.println(green + "La catégorie " + category_name + " a été affectée au film " + film_name + reset);

            return false;


        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }

    }

}

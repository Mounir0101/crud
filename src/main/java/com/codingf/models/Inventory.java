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

public class Inventory implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String film_id = "";
        String store_id = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);

            while (true) {
                try {
                    System.out.println("Donnez l'id du film");
                    film_id = input.nextLine();
                    int film = Integer.parseInt(film_id);
                    break;
                }
                catch (NumberFormatException e) {
                    System.err.println("Vous devez rentrer un nombre entier");
                }
            }

            while (true) {
                try {
                    System.out.println("Donnez l'id du magasin");
                    store_id = input.nextLine();
                    int store = Integer.parseInt(store_id);
                    break;
                }
                catch (NumberFormatException e) {
                    System.err.println("Vous devez rentrer un nombre entier");
                }
            }

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM store WHERE store_id = '" + store_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas de magasin ayant l'id " + store_id);
                System.err.println("Vous devez d'abord créer le magasin correspondant");
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
            result = statement.executeQuery("SELECT * FROM inventory");

            while (result.next()) {
                if (result.getString("film_id").equals(film_id) && result.getString("store_id").equals(store_id)) {
                    System.err.println("Ce film est déjà présent dans ce magasin");
                    return true;
                }
            }

            String columns = "";
            String values = "";
            String film_title = "";

            column = Arrays.asList("film_id", "store_id");
            columns = String.join(",", column);
            value = Arrays.asList(film_id, store_id);
            values = String.join("','", value);
            Create.create(con, "inventory", columns, values);

            statement = con.createStatement();
            result = statement.executeQuery("SELECT title FROM film WHERE film_id = '" + film_id + "'");
            if (result.next()) {
                film_title = result.getString("title");
            }

            System.out.println(green + "Le film " + film_title + " a bien été ajouté à l'inventaire du magasin " + store_id + reset);

            return false;

        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }
    }

}

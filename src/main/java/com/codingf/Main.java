package com.codingf;

import com.codingf.fonctions.Delete;
import com.codingf.fonctions.Read;
import com.codingf.fonctions.Create;
import com.codingf.fonctions.Update;
import com.mysql.cj.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Problème de chargement du driver");
        }
        System.out.println("Le driver est chargé");
        String host = "localhost";
        String dbname = "sakila";
        int port = 3306;

        String URL = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        String username = "root";
        String password = "";

        Connection connection = DriverManager.getConnection(URL, username, password);

        String green = "\u001B[32m";
        String reset = "\u001B[0m";

        if (connection == null) {
            System.err.println("Erreur de connexion");
        } else {
            System.out.println(green + "Connexion établie" + reset);
        }
        Scanner nb = new Scanner(System.in);
        int table;

        while (true) {

            while (true) {

                System.out.println("1: Country");
                System.out.println("2: City");
                System.out.println("Quelle table voulez vous choisir ?");

                String input = nb.nextLine();

                try {
                    table = Integer.parseInt(input);
                    if (table < 1 || table > 2) {
                        System.err.println("Choisissez une action valide");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.err.println("nombre incorrect");
                }

            }

            String tableSelected = "";

            switch (table) {
                case 1 -> tableSelected = "country";
                case 2 -> tableSelected = "city";
            }

            int choice;

            while (true) {
                System.out.println("1: Créer");
                System.out.println("2: Lire");
                System.out.println("3: Mettre à jour");
                System.out.println("4: Supprimer");
                System.out.println("Que voulez vous faire avec cette table ?");

                String input = nb.nextLine();

                try {
                    choice = Integer.parseInt(input);
                    if (choice < 1 || choice > 4) {
                        System.err.println("Choisissez une action valide");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.err.println("nombre incorrect");
                }
            }

            Scanner input = new Scanner(System.in);
            boolean exists = false;

            switch (choice) {

                case 1:

                    String country = "";
                    String city = "";

                    switch (tableSelected) {

                        case "country":

                            System.out.println("Quel pays voulez vous ajouter ?");
                            country = input.nextLine();
                            Statement statement = connection.createStatement();
                            ResultSet result = statement.executeQuery("SELECT * FROM country");

                            while (result.next()) {
                                if (result.getString("country").equals(country)) {
                                    System.err.println("Ce pays existe déjà");
                                    exists = true;
                                    break;
                                }
                            }

                        case "city":

                            System.out.println("Quel ville voulez vous ajouter ?");
                            city = input.nextLine();
                            statement = connection.createStatement();
                            result = statement.executeQuery("SELECT * FROM city");

                            System.out.println("Dans quel pays se trouve cette ville ?");
                            country = input.nextLine();

                            while (result.next()) {
                                if (result.getString("city").equals(city)) {
                                    System.err.println("Cette vile existe déjà");
                                    exists = true;
                                    break;
                                }
                            }

                    }

                    if (exists) {
                        continue;
                    } else {
                        List<String> column;
                        List<String> value;
                        String columns = "";
                        String values = "";

                        if (tableSelected.equals("country")) {

                            column = Arrays.asList("country");
                            columns = String.join(",", column);
                            value = Arrays.asList(country);
                            values = String.join("','", value);
                            Create.create(connection, tableSelected, columns, values);

                        } else if (tableSelected.equals("city")) {

                            column = Arrays.asList("city", "country_id");
                            columns = String.join(",", column);
                            value = Arrays.asList(city, country);
                            values = String.join("','", value);
                            Create.create(connection, tableSelected, columns, values);

                        }
                    }
                    break;

                case 2:

                    Read.read(connection, tableSelected);
                    break;

                case 4:

                    Delete.delete(connection, "table", "column", "value");
                    break;


                case 3:
                    String column = "";
                    String values = "";

                    switch (tableSelected) {

                        case "country":

                            System.out.println("Quel pays voulez vous modifier ?");
                            country = input.nextLine();

                            Statement statement = connection.createStatement();
                            ResultSet result = statement.executeQuery("SELECT * FROM country");

                            while (result.next()) {
                                if (result.getString("country").equals(country)) {
                                    Update.update(connection, tableSelected, column, values);
                                    System.out.println("Ce pays existe déjà et va etre modifier");
                                    exists = true;

                                }
                            }
                            break;

                        case "city":

                            System.out.println("Quel ville voulez vous modifier ?");
                            column = input.next();
                            statement = connection.createStatement();

                            result = statement.executeQuery("SELECT * FROM city");

                            while (result.next()) {
                                if (result.getString("city").equals(column)) {

                                    System.out.println("Cette " + column + "existe voulez vous la modifier");
                                    exists = true;

                                }


                                values = input.next();


                                Update.update(connection, tableSelected, column, values);
                                System.err.println("Cette " + column + " a été modifier par " + values);
                                exists = true;
                                break;
                            }
                    }

            }

        }


    }
}



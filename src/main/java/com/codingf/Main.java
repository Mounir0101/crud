package com.codingf;

import com.codingf.fonctions.Read;
import com.codingf.interfaces.Tables;
import com.codingf.models.*;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
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

        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        if (connection == null) {
            System.err.println("Erreur de connexion");
        }
        else {
            System.out.println(green + "Connexion établie" + reset);
        }
        Scanner nb = new Scanner(System.in);
        int table;

        while(true) {

            while (true) {

                DatabaseMetaData databaseMetaData = connection.getMetaData();
                ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
                int iterator = 0;
                while(resultSet.next()) {
                    if (resultSet.getString("TABLE_NAME").equals("film_text") || resultSet.getString("TABLE_NAME").equals("sys_config")) {
                        continue;
                    }
                    else {
                        iterator++;
                        System.out.println(iterator + " : " + resultSet.getString("TABLE_NAME"));
                    }
                }

                //System.out.println("1: Country");
                //System.out.println("2: City");
                System.out.println("16: Quitter");
                System.out.println("Quelle table voulez vous choisir ?");

                String input = nb.nextLine();

                try {
                    table = Integer.parseInt(input);
                    if (table < 1 || table > 16) {
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
                case 1 -> tableSelected = "actor";
                case 2 -> tableSelected = "address";
                case 3 -> tableSelected = "category";
                case 4 -> tableSelected = "city";
                case 5 -> tableSelected = "country";
                case 6 -> tableSelected = "customer";
                case 7 -> tableSelected = "film";
                case 8 -> tableSelected = "film_actor";
                case 9 -> tableSelected = "film_category";
                //case 10 -> tableSelected = "film_text";
                case 10 -> tableSelected = "inventory";
                case 11 -> tableSelected = "language";
                case 12 -> tableSelected = "payment";
                case 13 -> tableSelected = "rental";
                case 14 -> tableSelected = "staff";
                case 15 -> tableSelected = "store";
                //case 17 -> tableSelected = "sys_config";
                case 16 -> System.exit(0);
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

            Tables actors = new Actor();
            Tables addresses = new Address();
            Tables categories = new Category();
            Tables countries = new Country();
            Tables cities = new City();
            Tables customers = new Customer();
            Tables films = new Film();
            Tables films_actors = new FilmActor();
            Tables films_categories = new FilmCategory();
            Tables inventories = new Inventory();
            Tables languages = new Language();
            Tables payments = new Payment();
            Tables rentals = new Rental();
            Tables staffs = new Staff();
            Tables stores = new Store();

            switch (choice) {

                case 1:

                    switch (tableSelected) {

                        case "actor":

                            if (actors.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "address":

                            System.out.println("Désolé, je n'ai pas réussi à rendre possible la création d'une adresse");
                            break;
                            /*Tables adresses = new Address();
                            if (adresses.create(connection, tableSelected)) {
                                continue;
                            }

                            break;*/

                        case "category":

                            if (categories.create(connection, tableSelected)) {
                                continue;
                            }
                            break;

                        case "country":

                            if (countries.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "city":

                            if (cities.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "customer":

                            if (customers.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "film":

                            if (films.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "film_actor":

                            if (films_actors.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "film_category":

                            if (films_categories.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "inventory":

                            if (inventories.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "language":

                            if (languages.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "payment":

                            if (payments.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "rental":

                            if (rentals.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "staff":

                            if (staffs.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                        case "store":

                            if (stores.create(connection, tableSelected)) {
                                continue;
                            }

                            break;

                    }

                    break;

                case 2:

                    switch (tableSelected) {

                        case "actor":

                            actors.read(connection, tableSelected);
                            break;

                        case "address":

                            addresses.read(connection, tableSelected);
                            break;

                        case "category":

                            categories.read(connection, tableSelected);
                            break;

                        case "country":

                            countries.read(connection, tableSelected);
                            break;

                        case "city":

                            cities.read(connection, tableSelected);
                            break;

                        case "customer":

                            customers.read(connection, tableSelected);
                            break;

                        case "film":

                            films.read(connection, tableSelected);
                            break;

                        case "film_actor":

                            films_actors.read(connection, tableSelected);
                            break;

                        case "film_category":

                            films_categories.read(connection, tableSelected);
                            break;

                        case "inventory":

                            inventories.read(connection, tableSelected);
                            break;

                        case "language":

                            languages.read(connection, tableSelected);
                            break;

                        case "payment":

                            payments.read(connection, tableSelected);
                            break;

                        case "rental":

                            rentals.read(connection, tableSelected);
                            break;

                        case "staff":

                            staffs.read(connection, tableSelected);
                            break;

                        case "store":

                            stores.read(connection, tableSelected);
                            break;

                    }

                    break;

            }

        }

    }
}
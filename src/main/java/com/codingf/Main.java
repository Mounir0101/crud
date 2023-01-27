package com.codingf;

import com.codingf.fonctions.Delete;
import com.codingf.fonctions.Read;
import com.codingf.fonctions.Create;
import com.codingf.fonctions.Update;
import com.codingf.interfaces.Tables;
import com.codingf.models.*;
import com.codingf.connection.DbConnection;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        /*try {
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
        } else {
            System.out.println(green + "Connexion établie" + reset);
        }*/

        Connection connection = DbConnection.dbConnection();

        Scanner nb = new Scanner(System.in);
        int table;

        while (true) {

            while (true) {

                DatabaseMetaData databaseMetaData = connection.getMetaData();
                ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
                int iterator = 0;
                while (resultSet.next()) {
                    if (resultSet.getString("TABLE_NAME").equals("sys_config")) {
                        continue;
                    } else {

                        iterator++;
                        System.out.println(iterator + " : " + resultSet.getString("TABLE_NAME"));
                    }
                }

                System.out.println("17 : Quitter");
                System.out.println("Quelle table voulez vous choisir ?");

                String input = nb.nextLine();

                try {
                    table = Integer.parseInt(input);
                    if (table < 1 || table > 17) {
                        System.err.println("Choisissez une action valide");
                        continue;
                    }
                    break;
                }
                catch (Exception e) {
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
                case 10 -> tableSelected = "film_text";
                case 11 -> tableSelected = "inventory";
                case 12 -> tableSelected = "language";
                case 13 -> tableSelected = "payment";
                case 14 -> tableSelected = "rental";
                case 15 -> tableSelected = "staff";
                case 16 -> tableSelected = "store";
                //case 17 -> tableSelected = "sys_config";
                case 17 -> System.exit(0);
            }

            int choice = 0;

            while (true) {
                if (!tableSelected.equals("film_text")) {
                    System.out.println("1: Créer un élément");
                    System.out.println("2: Lire la table");
                    System.out.println("3: Mettre à jour un élément");
                    System.out.println("4: Supprimer un élément");
                    System.out.println("5: Retour");
                    System.out.println("Que voulez vous faire avec cette table ?");
                }
                else {
                    Read.read(connection, tableSelected);
                    break;
                }

                String input = nb.nextLine();

                try {
                    choice = Integer.parseInt(input);
                    if (choice < 1 || choice > 5) {
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

                    Read.read(connection, tableSelected);

                    break;

                case 3:

                    Update.update(connection, tableSelected);

                    break;

                case 4:

                    if (Delete.delete(connection, tableSelected)) {
                        continue;
                    }

                    break;

                case 5:

                    continue;

            }

        }
    }
}







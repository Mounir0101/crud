package com.codingf;

import com.codingf.fonctions.Read;
import com.codingf.fonctions.Create;
import com.codingf.interfaces.Tables;
import com.codingf.models.City;
import com.codingf.models.Country;
import com.mysql.cj.Query;
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
                    iterator++;
                    System.out.println(iterator + " : " + resultSet.getString("TABLE_NAME"));
                }

                //System.out.println("1: Country");
                //System.out.println("2: City");
                System.out.println("24: Quitter");
                System.out.println("Quelle table voulez vous choisir ?");

                String input = nb.nextLine();

                try {
                    table = Integer.parseInt(input);
                    if (table < 1 || table > 3) {
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
                case 3 -> System.exit(0);
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
                    boolean existCountry = false;

                    if (exists) {
                        continue;
                    }
                    else {
                        List<String> column = null;
                        List<String> value = null;
                        String columns = "";
                        String values = "";

                        if (tableSelected.equals("country")) {

                            Tables countries = new Country();
                            if (countries.create(connection, tableSelected)) {
                                continue;
                            }

                        }

                        else if (tableSelected.equals("city")) {

                            //System.out.println(country);

                            Tables cities = new City();

                            if (cities.create(connection, tableSelected)) {
                                continue;
                            }


                        }
                    }

                    break;

                case 2:

                    Read.read(connection, tableSelected);

            }

        }

    }
}
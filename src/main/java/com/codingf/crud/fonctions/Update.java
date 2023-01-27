package com.codingf.crud.fonctions;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Update {
    public static boolean update(Connection con, String table) {

        try {

            final String green = "\u001B[32m";
            final String reset = "\u001B[0m";
            String[] elemento_id = new String[2];

             Statement stmt = con.createStatement();
             ResultSet column = stmt.executeQuery("SELECT *FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"   + table + "'");

             int i = 0;

            HashMap<Integer, String> column_list = new HashMap<>();

             while (column.next()) {

                 if (!column.getString("column_name").equals(table + "_id") && !column.getString("column_name").equals("last_update")) {
                     i++;
                     System.out.println(i + " : " + column.getString("column_name"));
                     column_list.put(i,column.getString("column_name"));
                 }

             }

             Scanner input = new Scanner(System.in);

            System.out.println("Choisissez la colonne de l'élément à modifier");
            String columnid = input.nextLine();
            int colonne_id = Integer.parseInt(columnid);

            String film_id;
            String actor_id;
            String category_id;

            if (table.equals("film_actor") || table.equals("film_category")) {

                System.out.println("Donnez l'id du film correspondant à l'élément que vous voulez modifier");
                film_id = input.nextLine();

                if (table.equals("film_actor")) {
                    System.out.println("Donnez l'id de l'acteur correspondant à l'élément que vous voulez modifier");
                    actor_id = input.nextLine();

                    stmt = con.createStatement();
                    ResultSet result = stmt.executeQuery("SELECT " + column_list.get(colonne_id) + " FROM " + table + " WHERE film_id = '" + film_id + "' AND actor_id = '" + actor_id + "'");

                    if (result.next()) {
                        System.out.println("Valeur actuelle de l'élément : " + result.getString(column_list.get(colonne_id)));
                    }

                    System.out.println("Par quoi voulez vous le modifier ?");
                    String value = input.nextLine();

                    if (column_list.get(colonne_id).contains("_id")) {
                        elemento_id = column_list.get(colonne_id).split("_");

                        stmt = con.createStatement();
                        result = stmt.executeQuery("SELECT * FROM "+ elemento_id[0] +" WHERE " + column_list.get(colonne_id) + " = '" + value + "'");

                        if (!result.next()) {
                            System.err.println("Il n'existe pas d'élément ayant l'id " + value + " dans la table " + elemento_id[0]);
                            System.err.println("Vous devez d'abord créer l'élément correspondante");
                            return true;

                        }
                    }

                    stmt = con.createStatement();

                    stmt.executeUpdate("UPDATE " + table + " SET " + column_list.get(colonne_id) + " = '" + value + "' WHERE film_id = '" + film_id + "' AND actor_id = '" + actor_id + "'");
                    System.out.println(green + "La modification a été effectuée" + reset);

                    return false;


                }
                else {
                    System.out.println("Donnez l'id de la catégorie correspondant à l'élément que vous voulez modifier");
                    category_id = input.nextLine();

                    stmt = con.createStatement();
                    ResultSet result = stmt.executeQuery("SELECT " + column_list.get(colonne_id) + " FROM " + table + " WHERE film_id = '" + film_id + "' AND category_id = '" + category_id + "'");

                    if (result.next()) {
                        System.out.println("Valeur actuelle de l'élément : " + result.getString(column_list.get(colonne_id)));
                    }

                    System.out.println("Par quoi voulez vous le modifier ?");
                    String value = input.nextLine();

                    stmt = con.createStatement();
                    result = stmt.executeQuery("SELECT * FROM "+ elemento_id[0] +" WHERE " + column_list.get(colonne_id) + " = '" + value + "'");

                    if (!result.next()) {
                        System.err.println("Il n'existe pas d'élément ayant l'id " + value + " dans la table " + elemento_id[0]);
                        System.err.println("Vous devez d'abord créer l'élément correspondante");
                        return true;

                    }

                    stmt = con.createStatement();

                    stmt.executeUpdate("UPDATE " + table + " SET " + column_list.get(colonne_id) + " = '" + value + "' WHERE film_id = '" + film_id + "' AND category_id = '" + category_id + "'");
                    System.out.println(green + "La modification a été effectuée" + reset);

                    return false;

                }

            }

            else {

                Read.read(con, table);

                String elementid;
                while (true) {
                    try {
                        System.out.println("Choisissez l'id de l'élément à modifier ");
                        elementid = input.nextLine();
                        int element = Integer.parseInt(elementid);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Vous devez rentrer un nombre entier");
                    }
                }

                stmt = con.createStatement();
                ResultSet result = stmt.executeQuery("SELECT " + column_list.get(colonne_id) + " FROM " + table + " WHERE " + table + "_id = '" + elementid + "'");

                if (result.next()) {
                    System.out.println("Valeur actuelle de l'élément : " + result.getString(column_list.get(colonne_id)));
                }

                System.out.println("Par quoi voulez vous le modifier ?");
                String value = input.nextLine();

                if (column_list.get(colonne_id).equals("first_name") || column_list.get(colonne_id).equals("last_name") || column_list.get(colonne_id).equals("title")) {
                    value = value.toUpperCase();
                }

                if (column_list.get(colonne_id).contains("_id")) {
                    elemento_id = column_list.get(colonne_id).split("_");
                    if (elemento_id[0].equals("manager")) {
                        stmt = con.createStatement();
                        result = stmt.executeQuery("SELECT * FROM " + elemento_id[1] + " WHERE " + column_list.get(colonne_id) + " = '" + value + "'");

                        if (!result.next()) {
                            System.err.println("Il n'existe pas d'élément ayant l'id " + value + " dans la table " + elemento_id[1]);
                            System.err.println("Vous devez d'abord créer l'élément correspondante");
                            return true;
                        }
                    } else {
                        stmt = con.createStatement();
                        result = stmt.executeQuery("SELECT * FROM " + elemento_id[0] + " WHERE " + column_list.get(colonne_id) + " = '" + value + "'");

                        if (!result.next()) {
                            System.err.println("Il n'existe pas d'élément ayant l'id " + value + " dans la table " + elemento_id[0]);
                            System.err.println("Vous devez d'abord créer l'élément correspondante");
                            return true;
                        }
                    }
                }

                stmt = con.createStatement();

                //System.out.println("UPDATE " + table + " SET '" + column_list.get(colonne_id) + "' = '" + value + "' WHERE " + table + "_id = '" + elementid + "'");

                stmt.executeUpdate("UPDATE " + table + " SET " + column_list.get(colonne_id) + " = '" + value + "' WHERE " + table + "_id = '" + elementid + "'");
                System.out.println(green + "La modification a été effectuée" + reset);
                return false;

            }

        }
        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }
        //return false;
    }
}


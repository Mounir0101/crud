package com.codingf.fonctions;

import java.sql.*;
import java.util.Scanner;

public class Delete {

    public static boolean delete(Connection con, String table) {

        try {

            String element_id;
            String film_id;
            String actor_id;
            String category_id;

            final String green = "\u001B[32m";
            final String reset = "\u001B[0m";

            Scanner input = new Scanner(System.in);

            if (table.equals("film_actor") || table.equals("film_category")) {

                System.out.println("Donnez l'id du film que vous voulez supprimer");
                film_id = input.nextLine();

                if (table.equals("film_actor")) {
                    System.out.println("Donnez l'id de l'acteur que vous voulez supprimer");
                    actor_id = input.nextLine();

                    Statement statement = con.createStatement();

                    statement.executeUpdate("DELETE FROM " + table + " WHERE film_id = " + film_id + " AND actor_id = " + actor_id);

                    System.out.println(green + "L'élément " + film_id + ", " + actor_id + " a bien été supprimé de la table " + table + reset);

                    return false;


                }
                else {
                    System.out.println("Donnez l'id de la catégorie que vous voulez supprimer");
                    category_id = input.nextLine();

                    Statement statement = con.createStatement();

                    statement.executeUpdate("DELETE FROM " + table + " WHERE film_id = " + film_id + " AND category_id = " + category_id);

                    System.out.println(green + "L'élément " + film_id + ", " + category_id + " a bien été supprimé de la table " + table + reset);

                    return false;

                }

            }

            else {

                Read.read(con, table);

                System.out.println("Donnez l'id de l'élément que vous voulez supprimer");
                element_id = input.nextLine();
            }

            Statement statement;

            //System.out.println(green + "Checkpoint 1" + reset);

            DatabaseMetaData databaseMetaData = con.getMetaData();
            ResultSet tableList = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});

            //System.out.println(green + "Checkpoint 2" + reset);

            while(tableList.next()) {
                if (tableList.getString("TABLE_NAME").equals("film_text") || tableList.getString("TABLE_NAME").equals("sys_config")) {
                    continue;
                }
                else {

                    //System.out.println(green + "Checkpoint 3" + reset);

                    statement = con.createStatement();
                    ResultSet columnList = statement.executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableList.getString("table_name") + "'");

                    while (columnList.next()) {

                        //System.out.println(green + "Checkpoint 4" + reset);

                        if (columnList.getString("column_name").equals(table + "_id") && !tableList.getString("table_name").equals(table)){

                            //System.out.println(green + "Checkpoint 5" + reset);
                            System.out.println("SELECT * FROM " + tableList.getString("table_name") + " WHERE " + table + "_id = '" + element_id + "'");

                            statement = con.createStatement();
                            ResultSet result = statement.executeQuery("SELECT * FROM " + tableList.getString("table_name") + " WHERE " + table + "_id = '" + element_id + "'");

                            if (result.next()) {
                                //System.out.println(green + "Checkpoint 6" + reset);
                                System.err.println("L'élément que vous essayez de supprimer est utilisé comme clé étrangère dans la table " + tableList.getString("table_name"));
                                return true;
                            }
                        }
                    }
                }
            }

            System.out.println("DELETE FROM " + table + " WHERE " + table + "_id = " + element_id);

            statement = con.createStatement();

            statement.executeUpdate("DELETE FROM " + table + " WHERE " + table + "_id = " + element_id);

            System.out.println(green + "L'élément " + element_id + " a bien été supprimé de la table " + table + reset);

            return false;

        }

        catch (SQLException e) {
            System.err.println("Vous ne pouvez pas supprimer cet élément : il est référencé dans une autre table");
            System.err.println("Erreur : " + e);
            return true;
        }

    }

}

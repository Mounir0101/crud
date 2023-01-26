package com.codingf.fonctions;
import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Update {
    public static void update(Connection con, String table) {

        try {

            final String green = "\u001B[32m";
            final String reset = "\u001B[0m";

             Statement stmt = con.createStatement();
             ResultSet column = stmt.executeQuery("SELECT *FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"   + table + "'");

             int i = 0;

            HashMap<Integer, String> column_list = new HashMap<Integer, String>();

             while (column.next()) {

                 i++;
                 System.out.println(i + " : " + column.getString("column_name"));
                 column_list.put(i,column.getString("column_name"));

             }
            Scanner input = new Scanner(System.in);

            System.out.println("Choisissez la colonne de l'élément à modifier");
            String columnid = input.nextLine();
            int colonne_id = Integer.parseInt(columnid);


            Read.read(con, table);
            System.out.println("Choisissez l'id de l'élément à modifier ");
            String elementid = input.nextLine();

            stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT " + column_list.get(colonne_id) + " FROM " + table + " WHERE " + table + "_id = '" + elementid + "'");

            if (result.next()) {
                System.out.println("Valeur actuelle de l'élément : " + result.getString(column_list.get(colonne_id)));
            }

            System.out.println("Par quoi voulez vous le modifier ?");
            String value = input.nextLine();
            stmt = con.createStatement();

            //System.out.println("UPDATE " + table + " SET '" + column_list.get(colonne_id) + "' = '" + value + "' WHERE " + table + "_id = '" + elementid + "'");

            stmt.executeUpdate("UPDATE " + table + " SET " + column_list.get(colonne_id) + " = '" + value + "' WHERE " + table + "_id = '" + elementid + "'" );
            System.out.println(green + "La modification a été effectuée" + reset);

        } catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }
    }
}


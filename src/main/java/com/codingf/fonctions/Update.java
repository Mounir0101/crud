package com.codingf.fonctions;
import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Update {
    public static void update(Connection con, String table) {

        try {


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

            System.out.println("choisissez la colonne de l'element à modifier");
            String columnid = input.nextLine();
            int colonne_id = Integer.parseInt(columnid);


            //Read.read(con, table);
            System.out.println("Choisissez l'id de l'element a modifier ");
            String elementid = input.nextLine();


            System.out.println("par quoi voulez vous le modifier ?");
            String value = input.nextLine();
            stmt = con.createStatement();

            System.out.println("UPDATE " + table + " SET '" + column_list.get(colonne_id) + "' = '" + value + "' WHERE " + table + "_id = '" + elementid + "'");
            stmt.executeUpdate("UPDATE " + table + " SET " + column_list.get(colonne_id) + " = '" + value + "' WHERE " + table + "_id = '" + elementid + "'" );
            System.out.println("La modification a été effectuée");

        } catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }
    }
}


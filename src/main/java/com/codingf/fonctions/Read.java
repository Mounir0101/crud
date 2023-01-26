package com.codingf.fonctions;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Read {
    public static void read(Connection con, String table) {
        try {

            final String red = "\u001B[31m";
            final String reset = "\u001B[0m";

            Statement stmt = con.createStatement();

            ResultSet list = stmt.executeQuery("SELECT * FROM " + table);

            System.out.println();

            stmt = con.createStatement();
            ResultSet columnList = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + table + "'");

            List<String> columns = new ArrayList<>();

            while (columnList.next()) {
                columns.add(columnList.getString("column_name"));
            }

            while (list.next()) {
                for (int j = 1; j <= columns.size(); j++) {
                    if (!columns.get(j-1).equals("picture") && !columns.get(j-1).equals("location")) {
                        System.out.println(columns.get(j-1) + " : " + list.getString(j));
                    }
                }

                System.out.println(red + "========================================================================================================" + reset);
            }

        }
        catch ( SQLException e ){
            System.err.println("Erreur : "+e);

        }
    }

}

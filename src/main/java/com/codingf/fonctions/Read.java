package com.codingf.fonctions;
import java.sql.*;
import java.util.List;

public class Read {
    public static void read(Connection con, String table, ResultSet list, List<String> columns) {
        try {

            while (list.next()) {
                for (int j = 1; j <= columns.size(); j++) {
                    if (!columns.get(j-1).equals("picture")) {
                        System.out.println(columns.get(j-1) + " : " + list.getString(j));
                    }
                }

                System.out.println("========================================================================================================");
            }

        }
        catch ( SQLException e ){
            System.err.println("Erreur : "+e);

        }
    }

}

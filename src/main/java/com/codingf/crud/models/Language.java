package com.codingf.crud.models;

import com.codingf.crud.fonctions.Create;
import com.codingf.crud.fonctions.Read;
import com.codingf.crud.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Language implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String language = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);

            System.out.println("Quel langue voulez vous ajouter ?");
            language = input.nextLine();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM language");

            while (result.next()) {
                if (result.getString("name").equals(language)) {
                    System.err.println("Cette langue existe déjà");
                    return true;
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("name");
            columns = String.join(",", column);
            value = Arrays.asList(language);
            values = String.join("','", value);
            Create.create(con, "language", columns, values);

            System.out.println(green + "La langue " + language + " a bien été ajouté à la table language" + reset);

            return false;

        }

        catch (SQLException e) {
            System.err.println("Erreur " + e);
            return true;
        }
    }

}

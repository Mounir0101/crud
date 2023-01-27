package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.fonctions.Read;
import com.codingf.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Category implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String category = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);

            System.out.println("Quel catégorie voulez vous ajouter ?");
            category = input.nextLine();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM category");

            while (result.next()) {
                if (result.getString("name").equals(category)) {
                    System.err.println("Cette catégorie existe déjà");
                    return true;
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("name");
            columns = String.join(",", column);
            value = Arrays.asList(category);
            values = String.join("','", value);
            Create.create(con, "category", columns, values);

            System.out.println(green + "La catégorie " + category + " a bien été ajouté à la table category" + reset);

            return false;

        }

        catch (SQLException e) {
            System.err.println("Erreur " + e);
            return true;
        }

    }

}

package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Country implements Tables{

    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String country = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);

            System.out.println("Quel pays voulez vous ajouter ?");
            country = input.nextLine();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM country");

            while (result.next()) {
                if (result.getString("country").equals(country)) {
                    System.err.println("Ce pays existe déjà");
                    return true;
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("country");
            columns = String.join(",", column);
            value = Arrays.asList(country);
            values = String.join("','", value);
            Create.create(con, "country", columns, values);

            System.out.println(green + "Le pays " + country + " a bien été ajouté à la table country" + reset);

            return false;


        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }
    }
}

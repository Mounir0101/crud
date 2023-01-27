package com.codingf.crud.models;

import com.codingf.crud.fonctions.Create;
import com.codingf.crud.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Actor implements Tables {

    @Override
    public boolean create(Connection con, String table) {

        String actor;
        List<String> column;
        List<String> value;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";
        String[] actor_name;

        Scanner input = new Scanner(System.in);

        System.out.println("Quel acteur/actrice voulez vous ajouter ?");
        actor = input.nextLine();
        actor_name = actor.split(" ");
        String first_name = actor_name[0];
        String last_name = actor_name[1];

        try {

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM actor");

            while (result.next()) {
                if (result.getString("first_name").equals(first_name.toUpperCase()) && result.getString("last_name").equals(last_name.toUpperCase())) {
                    System.err.println("Cet acteur existe déjà");
                    return true;
                }
            }

            String columns;
            String values;

            column = Arrays.asList("first_name", "last_name");
            columns = String.join(",", column);
            value = Arrays.asList(first_name.toUpperCase(), last_name.toUpperCase());
            values = String.join("','", value);
            Create.create(con, "actor", columns, values);

            System.out.println(green + "L'acteur " + first_name + " " + last_name + " a bien été ajouté à la table actor" + reset);

            return false;


        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }

    }
}

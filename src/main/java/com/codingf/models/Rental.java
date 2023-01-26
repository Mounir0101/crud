package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.fonctions.Read;
import com.codingf.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Rental implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String inventory_id = "";
        String customer_id = "";
        //String return_date = "";
        String staff_id = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);

            System.out.println("Donnez l'id de l'inventaire");
            inventory_id = input.nextLine();
            System.out.println("Donnez l'id du client");
            customer_id = input.nextLine();
            //System.out.println("Donnez l'id de l'inventaire");
            //return_date = input.nextLine();
            System.out.println("Donnez l'id du manager");
            staff_id = input.nextLine();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String rental_date = dtf.format(now);

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM inventory WHERE inventory_id = " + inventory_id);

            if (!result.next()) {
                System.err.println("Il n'existe pas d'inventaire ayant l'id " + inventory_id);
                System.err.println("Vous devez d'abord créer l'inventaire correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM customer WHERE customer_id = " + customer_id);

            if (!result.next()) {
                System.err.println("Il n'existe pas de client ayant l'id " + customer_id);
                System.err.println("Vous devez d'abord créer le client correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM staff WHERE staff_id = " + staff_id);

            if (!result.next()) {
                System.err.println("Il n'existe pas de membre du staff ayant l'id " + staff_id);
                System.err.println("Vous devez d'abord créer le membre du staff correspondant");
                return true;
            }

            /*statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM country");

            while (result.next()) {
                if (result.getString("country").equals(country)) {
                    System.err.println("Ce pays existe déjà");
                    return true;
                }
            }*/

            String columns = "";
            String values = "";

            column = Arrays.asList("rental_date", "inventory_id", "customer_id", "staff_id");
            columns = String.join(",", column);
            value = Arrays.asList(rental_date, inventory_id, customer_id, staff_id);
            values = String.join("','", value);
            Create.create(con, "rental", columns, values);

            System.out.println(green + "Le location a bien été ajouté à la table rental" + reset);

            return false;


        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }
    }

}

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

public class Store implements Tables {

    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String manager_staff_id = "";
        String address_id = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);

            System.out.println("Donnez l'id du manager de ce magasin");
            manager_staff_id = input.nextLine();
            System.out.println("Donnez l'id de l'adresse de ce magasin");
            address_id = input.nextLine();

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM staff WHERE staff_id = '" + manager_staff_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas de manager ayant l'id " + manager_staff_id);
                System.err.println("Vous devez d'abord créer le manager correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM address WHERE address_id = '" + address_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas d'adresse ayant l'id " + address_id);
                System.err.println("Vous devez d'abord créer l'adresse correspondante");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM store");

            while (result.next()) {
                if (result.getString("address_id").equals(address_id)) {
                    System.err.println("Il y a déjà un magasin à cette adresse");
                    return true;
                }
                else if (result.getString("manager_staff_id").equals(manager_staff_id)) {
                    System.err.println("Cette personne est déjà le manager d'un autre magasin");
                    return true;
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("manager_staff_id", "address_id");
            columns = String.join(",", column);
            value = Arrays.asList(manager_staff_id, address_id);
            values = String.join("','", value);
            Create.create(con, "store", columns, values);

            System.out.println("We got here");

            result = statement.executeQuery("SELECT first_name, last_name FROM staff WHERE staff_id = '" + manager_staff_id + "'");

            System.out.println("We even got here");

            String manager_name = "";

            if (result.next()) {
                String fn = result.getString("first_name");
                System.out.println(fn);
                String ln = result.getString("last_name");
                System.out.println(ln);
                manager_name = fn + " " + ln;
            }

            result = statement.executeQuery("SELECT address, city.city FROM address INNER JOIN city ON address.city_id=city.city_id WHERE address_id = '" + address_id + "'");

            String address = "";
            String city = "";

            if (result.next()) {
                address = result.getString("address");
                city = result.getString("city");
            }

            System.out.println(green + "Le magasin ayant pour manager " + manager_name + " a bien été crée à " + address + " à " + city + reset);

            return false;


        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }
    }

}

package com.codingf.crud.models;

import com.codingf.crud.fonctions.Create;
import com.codingf.crud.fonctions.Read;
import com.codingf.crud.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Rental implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String inventory_id = "";
        String customer_id = "";
        String return_date = "";
        String staff_id = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);


            while (true) {
                try {
                    System.out.println("Donnez l'id de l'inventaire");
                    inventory_id = input.nextLine();
                    int inventory = Integer.parseInt(inventory_id);
                    break;
                }
                catch (NumberFormatException e) {
                    System.err.println("Vous devez rentrer un nombre entier");
                }
            }

            while (true) {
                try {
                    System.out.println("Donnez l'id du client");
                    customer_id = input.nextLine();
                    int customer = Integer.parseInt(customer_id);
                    break;
                }
                catch (NumberFormatException e) {
                    System.err.println("Vous devez rentrer un nombre entier");
                }
            }

            while (true) {
                try {
                    System.out.println("Donnez la durée de location du film (en semaines)");
                    return_date = input.nextLine();
                    int date = Integer.parseInt(return_date);
                    break;
                }
                catch (NumberFormatException e) {
                    System.err.println("Vous devez rentrer un nombre entier");
                }
            }

            while (true) {
                try {
                    System.out.println("Donnez l'id du manager");
                    staff_id = input.nextLine();
                    int staff = Integer.parseInt(staff_id);
                    break;
                }
                catch (NumberFormatException e) {
                    System.err.println("Vous devez rentrer un nombre");
                }
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String rental_date = dtf.format(now);

            int weeks = Integer.parseInt(return_date);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_YEAR, weeks);
            //System.out.println(cal.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return_date = sdf.format(cal.getTime());
            //System.out.println("Date 6 weeks from now: " + dateTime);

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM inventory WHERE inventory_id = '" + inventory_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas d'inventaire ayant l'id " + inventory_id);
                System.err.println("Vous devez d'abord créer l'inventaire correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM customer WHERE customer_id = '" + customer_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas de client ayant l'id " + customer_id);
                System.err.println("Vous devez d'abord créer le client correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM staff WHERE staff_id = '" + staff_id + "'");

            if (!result.next()) {
                System.err.println("Il n'existe pas de membre du staff ayant l'id " + staff_id);
                System.err.println("Vous devez d'abord créer le membre du staff correspondant");
                return true;
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("rental_date", "inventory_id", "customer_id", "return_date", "staff_id");
            columns = String.join(",", column);
            value = Arrays.asList(rental_date, inventory_id, customer_id, return_date, staff_id);
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

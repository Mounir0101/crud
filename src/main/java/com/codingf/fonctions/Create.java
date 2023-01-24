package com.codingf.fonctions;

import java.sql.*;

public class Create {

    Connection con;

    String table;

    String name;

    public Create(Connection con, String table, String name) {
        this.con = con;
        this.table = table;
        this.name = name;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void create(Connection con, String table, String name) {

        try {
            System.out.println("We got here");
            Statement statement = con.createStatement();

            System.out.println("We create statement");

            System.out.println("Table is " + table + ", value is " + name);

            statement.execute("INSERT INTO " + table + "(country) VALUES('" + name + "')");

            ResultSet result = statement.executeQuery("SELECT * FROM country");

            while (result.next()) {
                System.out.println("Pays : " + result.getString("country"));
            }

        }
        catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }

    }

}

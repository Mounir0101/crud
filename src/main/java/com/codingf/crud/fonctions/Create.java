package com.codingf.crud.fonctions;

import java.sql.*;

public class Create {

    /**
     * Fonction pour ajouter un élément
     * @param con: la connection à la bdd
     * @param table: la table dans laquelle on veut ajouter l'élément
     * @param column: la liste des colonnes dans lesquelles on ajoute une valeur
     * @param value: les valeurs à ajouter
     */
    public static void create(Connection con, String table, String column, String value) {

        try {
            Statement statement = con.createStatement();

            System.out.println("INSERT INTO " + table + " (" + column + ") VALUES ('" + value + "')");

            statement.execute("INSERT INTO " + table + " (" + column + ") VALUES ('" + value + "')");

        }
        catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }

    }

}

package com.codingf.interfaces;

import com.codingf.fonctions.Create;

import java.sql.Connection;
import java.util.List;

public interface Tables {

    public void create(Connection con, String table, List<String> column, String value);

}

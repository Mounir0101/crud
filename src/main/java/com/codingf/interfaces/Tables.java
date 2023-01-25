package com.codingf.interfaces;

import com.codingf.fonctions.Create;

import java.sql.Connection;
import java.util.List;

public interface Tables {

    public boolean create(Connection con, String table);

    public void read(Connection con, String table);

    public void update(Connection con, String table);

}

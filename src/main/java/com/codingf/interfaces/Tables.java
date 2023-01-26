package com.codingf.interfaces;

import java.sql.Connection;

public interface Tables {

    boolean create(Connection con, String table);

}

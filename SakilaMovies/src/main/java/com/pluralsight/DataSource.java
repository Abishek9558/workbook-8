package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yearup";

    public static javax.sql.DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(DB_URL);
        ds.setUsername(DB_USER);
        ds.setPassword(DB_PASSWORD);
        return ds;
    }
}
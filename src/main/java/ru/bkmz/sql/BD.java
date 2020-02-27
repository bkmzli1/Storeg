package ru.bkmz.sql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class BD {
    private Connection conn;

    protected static final Logger logger = LogManager.getLogger();

    public BD(String fileConn) throws ClassNotFoundException {
        try {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + fileConn);
            logger.info("Connection: " + fileConn);
        } catch (SQLException e) {
            logger.warn("SQLException: ", e);
        }

    }

    public void setBD(String inquiry) throws SQLException {
        logger.info("Statement: " + inquiry);
        Statement statmt = conn.createStatement();
        statmt.execute(inquiry);

        statmt.close();

    }

    public ResultSet getBD(String inquiry) throws SQLException {
        logger.info("resultSet: " + inquiry);
        ResultSet resultSet = null;

        Statement statmt = conn.createStatement();
        resultSet = statmt.executeQuery(inquiry);
        statmt.close();


        return resultSet;

    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}

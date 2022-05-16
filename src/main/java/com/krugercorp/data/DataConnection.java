/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krugercorp.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author JEZ
 */
public class DataConnection {

    Connection connection = null;
    private CallableStatement callableStatement;
    public ResultSet select(String sql) throws SQLException {
        Statement ps = connection.createStatement();
        return ps.executeQuery(sql);
    }

    public int allOption(String sql) throws SQLException {
        Statement ps = connection.createStatement();
        ps.executeUpdate(sql);
        commit();
        return ps.getUpdateCount();
    }

    public void close() throws SQLException {
        this.connection.close();
    }

   
    public void open() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/kruger";
        String user = "postgres";
        String pass = "12345";
        connection = DriverManager.getConnection(url,user,pass);
        connection.isValid(5000);
        connection.setAutoCommit(false);
    }

    public DataConnection commit() throws SQLException {
        this.connection.commit();
        return this;
    }

    public DataConnection rollback() throws SQLException {
        this.connection.rollback();
        return this;
    }
}

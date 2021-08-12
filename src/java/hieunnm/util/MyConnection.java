package hieunnm.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author PC
 */
public class MyConnection implements Serializable {

    public static Connection getMyConnection() throws NamingException, SQLException {
        Connection conn = null;
        Context currContext = new InitialContext();
        Context tomeContext = (Context) currContext.lookup("java:comp/env");
        DataSource ds = (DataSource) tomeContext.lookup("DBConn");
        conn = ds.getConnection();
        return conn;
    }
}

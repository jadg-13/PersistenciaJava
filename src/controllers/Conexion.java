package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    private static final String SERVIDOR = "jadgDB2023.mssql.somee.com";
    private static final String USUARIO = "jadg13_SQLLogin_1";
    private static final String PW = "27pdahshwc";
    private static final String NOMBREBD = "jadgDB2023";
    //private static final String PUERTO = "1433";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    public Connection obtenerConexion(){
        try{
            String conexionUrl = "jdbc:sqlserver://" + SERVIDOR +//": " + PUERTO +
                    "; Databasename= " + NOMBREBD +"; user= " + USUARIO + 
                    "; password = " + PW + ";";
            Class.forName(DRIVER);      
            return (DriverManager.getConnection(conexionUrl));
        }catch(ClassNotFoundException | SQLException ex){
           Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }      
        return null;
    }
    
    
    public void close(Connection conn){
        try{
           conn.close();
        }catch(SQLException ex){
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

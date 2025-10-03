/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 * Clase que permite la conexion a las bd y que verifica el estado de la conexion.
 * @author maryori
 */
public class ConexionBD {
    private static ConexionBD instancia;
    private Connection conexionMySQL;
    private Connection conexionPostgre;
    private String baseDatosActiva;
    
    private ConexionBD() {
        baseDatosActiva = "Nada";
    }
    /**
     * Regresa la instancia de la conexion (nula o una nueva conexion)
     * @return instancia
     */
    public static ConexionBD obtenerInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }
    /**
     * Funcion que conecta la app a la bd en MySQL, retorna el estado de la conexion
     * @return conexionMySQL
     * @throws SQLException 
     */
    public Connection conectarMySQL() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/proyecto3_bd1";
            String usuario = "root";
            String contraseña = "12345678";
            
            if (conexionMySQL != null && !conexionMySQL.isClosed()) {
                conexionMySQL.close();
            }
            
            conexionMySQL = DriverManager.getConnection(url, usuario, contraseña);
            JOptionPane.showMessageDialog(null, "¡Conexión a MySQL exitosa!");
            baseDatosActiva = "MySQL";
            return conexionMySQL;
        } catch (SQLException e) {
            throw new SQLException("Error al conectar con MySQL: " + e.getMessage());
        }
    }
    /**
     * Funcion que conecta la app a la bd en Postgre, retorna el estado de la conexion
     * @return conexionPostgre
     * @throws SQLException 
     */
    public Connection conectarPostgreSQL() throws SQLException {
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String usuario = "postgres";
            String contraseña = "12345678";
            
            if (conexionPostgre != null && !conexionPostgre.isClosed()) {
                conexionPostgre.close();
            }
            
            conexionPostgre = DriverManager.getConnection(url, usuario, contraseña);
            JOptionPane.showMessageDialog(null, "¡Conexión a PostgreSQL exitosa!");
            baseDatosActiva = "Postgre";
            return conexionPostgre;
        } catch (SQLException e) {
            throw new SQLException("Error al conectar con PostgreSQL: " + e.getMessage());
        }
    }
    /**
     * Cierra la conexion a MySQL
     */
    public void desconectarMySQL() {
        try {
            if (conexionMySQL != null && !conexionMySQL.isClosed()) {
                conexionMySQL.close();
                JOptionPane.showMessageDialog(null, "Desconectado de MySQL");
            }
            if ("MySQL".equals(baseDatosActiva)) {
                baseDatosActiva = "Nada";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desconectar MySQL " + e.getMessage());
        }
    }
    /**
     * Cierra la conexion a Postgre
     */
    public void desconectarPostgre() {
        try {
            if (conexionPostgre != null && !conexionPostgre.isClosed()) {
                conexionPostgre.close();
                JOptionPane.showMessageDialog(null, "Desconectado de Postgre");
            }
            if ("Postgre".equals(baseDatosActiva)) {
                baseDatosActiva = "Nada";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desconectar Postgre " + e.getMessage());
        }
    }
    /**
     * Regresa la conexion a MySQL
     * @return conexionMySQL
     */
    public Connection getConexionMySQL() {
        return conexionMySQL;
    }
    /**
     * Regresa la conexion a Postgre
     * @return conexionPostgre
     */
    public Connection getConexionPostgre() {
        return conexionPostgre;
    }
    /**
     * Función que evalua que bd tiene una conexion activa (MySQL o Postgre).
     * @return conexionMySQL || conexionPostgre || Null
     */
    public Connection getConexionActiva(){
        if ("MySQL".equals(baseDatosActiva)) {
            try {
                if (conexionMySQL != null && !conexionMySQL.isClosed()) {
                    return conexionMySQL;
                }
            } catch (SQLException e) {
                baseDatosActiva = "Nada";
            }
        } else if ("Postgre".equals(baseDatosActiva)) {
            try {
                if (conexionPostgre != null && !conexionPostgre.isClosed()) {
                    return conexionPostgre;
                }
            } catch (SQLException e) {
                baseDatosActiva = "Nada";
            }
        }
        return null;
    }
    /**
     * Regresa el nombre de la base de datos activa utilizando la funcion getConexionActiva()
     * @return baseDatosActiva
     */
    public String getBaseDatosActiva(){
        return baseDatosActiva;
    }
    /**
     * Regresa la instancia de la conexion
     * @return instancia
     */
    public static ConexionBD getInstancia() {
        return instancia;
    }
    /**
     * Indica si la conexion a MySQL fue exitosa o no
     * @return conxionMySQL
     */
    public boolean conectadoMySQL(){
        try{
            return conexionMySQL != null && !conexionMySQL.isClosed();
        } catch (SQLException e){
            return false;
        }
    }
    /**
     * Indica si la conexion a Postgre fue exitosa o no
     * @return conexionPostgre
     */
    public boolean conectadoPostgre(){
        try{
            return conexionPostgre!= null && !conexionPostgre.isClosed();
        } catch (SQLException e){
            return false;
        }
    }
    /**
     * Indica si ambas bd están conectadas.
     * @return 
     */
    public boolean conectadas(){
        return conectadoMySQL() && conectadoPostgre();
    }
    /**
     * Indica si al menos una de las bd esta conectada a la app.
     * @return 
     */
    public boolean hayConexionActiva(){
        return !"Nada".equals(baseDatosActiva) && getConexionActiva() != null;
    }
}
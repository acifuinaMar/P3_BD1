/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;
import java.util.*;
import java.io.*;
/**
 * Clase que sincroniza datos entre bd MySQL y Postgre. 
 * Detecta y resuelve diferencias en los registros de persona. 
 * Lleva un registro de las transacciones en una bitacora.
 * @author maryori
 */
public class Sincronizar {
    private static final String ARCHIVO_BITACORA = "bitacora_transacciones.txt";
    /**
     * Hace la sincronizacion entre ambas bd; verifica conectividad, obtiene datos,
     * resuelve diferencias y registra la bitacora
     * @return ture si la sincronizacion fue exitosa, false si no
     */
    public boolean sincronizarBasesDatos(){
        try{
            Connection conexionMySQL = ConexionBD.getInstancia().getConexionMySQL();
            Connection conexionPostgre = ConexionBD.getInstancia().getConexionPostgre();
            System.out.println("   MySQL: " + (conexionMySQL != null && !conexionMySQL.isClosed()));
            System.out.println("   PostgreSQL: " + (conexionPostgre != null && !conexionPostgre.isClosed()));
            
            if (conexionMySQL == null || conexionPostgre == null){
                registrarTransaccion("Error", "Sincronizacion fallida", "Una o ambas BD no están conectadas");
                return false;
            }
            
            if(conexionMySQL.isClosed() || conexionPostgre.isClosed()){
                registrarTransaccion("Error", "Sincronizacion fallida", "Una o ambas BD no están conectadas");
                return false;
            }
            
            Map<String, Map<String, Object>> datosMySQL = obtenerDatosDeBD(conexionMySQL, "MySQL");
            Map<String, Map<String, Object>> datosPostgre = obtenerDatosDeBD(conexionPostgre, "Postgre");
            
            resolverDiferencias(datosMySQL, datosPostgre, conexionMySQL, conexionPostgre);
            registrarTransaccion("SINCRONIZACION", "Completada", "Cambios hechos en MySQL y en Postgre" );
            return true;
        
        } catch (SQLException e){
            registrarTransaccion("ERROR", "Sincronización fallida", "Excepción: " + e.getMessage());
            return false;
        }
    }
    /**
     * Obtiene todos los registros de la tabla personas de la bd Los datos se organizan tal que DPI es PK.
     * @param conexion Conexion a la bd
     * @param tipoBD Tipo de base de datos (MySQL o Postgre)
     * @return Mapa con los datos obtenidos ordenados por DPI
     * @throws SQLException Si ocurre un error al acceder a la bd
     */
    private Map<String, Map<String, Object>> obtenerDatosDeBD(Connection conexion, String tipoBD) throws SQLException {
        Map<String, Map<String, Object>> datos = new HashMap<>();
        String consulta = "SELECT * FROM personas";
        
        try (Statement declaracion = conexion.createStatement(); ResultSet resultado = declaracion.executeQuery(consulta)) {
            
            while (resultado.next()) {
                Map<String, Object> registro = new HashMap<>();
                String dpi = resultado.getString("dpi");
                
                registro.put("primer_nombre", resultado.getString("primer_nombre"));
                registro.put("segundo_nombre", resultado.getString("segundo_nombre"));
                registro.put("primer_apellido", resultado.getString("primer_apellido"));
                registro.put("segundo_apellido", resultado.getString("segundo_apellido"));
                registro.put("direccion_domiciliar", resultado.getString("direccion_domiciliar"));
                registro.put("telefono_casa", resultado.getString("telefono_casa"));
                registro.put("telefono_movil", resultado.getString("telefono_movil"));
                registro.put("salario_base", resultado.getDouble("salario_base"));
                registro.put("bonificacion", resultado.getDouble("bonificacion"));
                registro.put("ultima_modificacion", resultado.getTimestamp("ultima_modificacion"));
                registro.put("origen_bd", tipoBD);
                
                datos.put(dpi, registro);   
            }
        }
        return datos;
    }
    /**
     * Compara los datos de ambas bd y resuelve diferencias encontradas.Usa la bitacora como apoyo para ver qué operacion fue mas reciente y aplicar
     * los cambios respectivos.
     * @param datosMySQL Datos obtenidos de MySQL
     * @param datosPostgreSQL Datos obtenidos de Postgre
     * @param conexionMySQL Conexion a la bd de MySQL
     * @param conexionPostgre Conexion a la bd de Postgre
     * @throws SQLException Si hay error con alguna operacion.
     */
    private void resolverDiferencias(Map<String, Map<String, Object>> datosMySQL, Map<String, Map<String, Object>> datosPostgreSQL, Connection conexionMySQL, Connection conexionPostgre) throws SQLException {
        Set<String> todosLosDPI = new HashSet<>();
        todosLosDPI.addAll(datosMySQL.keySet());
        todosLosDPI.addAll(datosPostgreSQL.keySet());
        
        for (String dpi : todosLosDPI) {
            Map<String, Object> registroMySQL = datosMySQL.get(dpi);
            Map<String, Object> registroPostgreSQL = datosPostgreSQL.get(dpi);
            
            String ultimaOperacion = estadoDesdeBitacora(dpi);            
            
            if(registroMySQL == null && registroPostgreSQL != null){
                if("ELIMINAR".equals(ultimaOperacion)){
                    eliminarRegistro(conexionPostgre, dpi, "Desde MySQL");
                } else {
                    insertarRegistro(conexionMySQL, dpi, registroPostgreSQL, "Desde PostgreSQL");
                }
            } else if (registroPostgreSQL == null && registroMySQL != null){
                if ("ELIMINAR".equals(ultimaOperacion)) {
                    eliminarRegistro(conexionMySQL, dpi, "Desde PostgreSQL");
                } else {
                     insertarRegistro(conexionPostgre, dpi, registroMySQL, "Desde MySQL");
                }
            } else if (registroMySQL != null && registroPostgreSQL != null){
                Timestamp fechaModMySQL = (Timestamp) registroMySQL.get("ultima_modificacion");
                Timestamp fechaModPostgre = (Timestamp) registroPostgreSQL.get("ultima_modificacion");
                
                if (fechaModMySQL != null && fechaModPostgre != null) {
                    if (fechaModMySQL.after(fechaModPostgre)) {
                         actualizarRegistro(conexionPostgre, dpi, registroMySQL, "Desde MySQL");
                    } else if(fechaModPostgre.after(fechaModMySQL)){
                    actualizarRegistro(conexionMySQL, dpi, registroPostgreSQL, "Desde PostgreSQL");
                    }
                }
            }
        }
    }
    /**
     * Revisa la bitacora para ver la ultima operacion hecha sobre un DPI específico.Ignora sincronizaciones.
     * @param dpi Numero de dpi a consultar
     * @return String con la ultima operacion "INSERTAR", "ACTUALIZAR", "ELIMINAR", "ERROR"
     */
    private String estadoDesdeBitacora(String dpi){
        try(BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_BITACORA))){
            String linea;
            String ultimaOperacion = "No_Existe";
            
            while ((linea = br.readLine()) != null) {
                if(linea.contains("DPI: " + dpi)){
                    if(linea.contains("INSERTAR") && !linea.contains("INSERTAR_SINC")){
                        ultimaOperacion = "INSERTAR";
                    } else if (linea.contains("ACTUALIZAR") && !linea.contains("ACTUALIZAR_SINC")){
                        ultimaOperacion = "ACTUALIZAR";
                    } else if (linea.contains("ELIMINAR") && !linea.contains("ELIMINAR_SINC")){
                        ultimaOperacion = "ELIMINAR";
                    }
                }
            }
            return ultimaOperacion;
        } catch (IOException e){
            System.err.println("Error leyendo bitácora para DPI " + dpi + ": " + e.getMessage());
            return "ERROR";
        }
    }
    /**
     * Elimina un registro de la tabla personas en una bd especifica
     * @param conexion Conexion a la bd de la que se eliminara el registro
     * @param dpi Numero de dpi a eliminar
     * @param fuente Descripcion del origen del cambio desde bitacora
     * @throws SQLException En caso de error al eliminar
     */
    private void eliminarRegistro(Connection conexion, String dpi, String fuente) throws SQLException {
        String consulta = "DELETE FROM personas WHERE dpi=?";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, dpi);
            ps.executeUpdate();
            registrarTransaccion("ELIMINAR_SINC", dpi, fuente);
        }
    }
    
    /**
     * Inserta un nuevo registro en la tabla personas en una bd especifica
     * @param conexion Conexion a la bd donde se agregará el registro
     * @param dpi Numero de dpi del nuevo registro
     * @param registro Mapa con los datos a insertar
     * @param fuente Descripcion del origen del cambio desde bitacora
     * @throws SQLException En caso de error al insertar
     */
    private void insertarRegistro(Connection conexion, String dpi, Map<String, Object> registro, String fuente) throws SQLException {
        String consulta = "INSERT INTO personas (dpi, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, direccion_domiciliar, telefono_casa, telefono_movil, salario_base, bonificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, dpi);
            ps.setString(2, (String) registro.get("primer_nombre"));
            ps.setString(3, (String) registro.get("segundo_nombre"));
            ps.setString(4, (String) registro.get("primer_apellido"));
            ps.setString(5, (String) registro.get("segundo_apellido"));
            ps.setString(6, (String) registro.get("direccion_domiciliar"));
            ps.setString(7, (String) registro.get("telefono_casa"));
            ps.setString(8, (String) registro.get("telefono_movil"));
            ps.setDouble(9, (Double) registro.get("salario_base"));
            ps.setDouble(10, (Double) registro.get("bonificacion"));
            
            ps.executeUpdate();
            registrarTransaccion("INSERTAR_SINC", dpi, fuente);
        }
    }
    /**
     * Actualiza un registro existente en la tabla personas de la bd especificada
     * @param conexion Conexion a la bd donde se hara la actualizacion
     * @param dpi Numero de dpi a actualizar
     * @param registro Mapa con los nuevos registros relacionados al dpi
     * @param fuente Descripcion del origen del cambio desde bitacora
     * @throws SQLException En caso de error al actualizar
     */
    private void actualizarRegistro(Connection conexion, String dpi, Map<String, Object> registro, String fuente) throws SQLException {
        String consulta = "UPDATE personas SET primer_nombre=?, segundo_nombre=?, primer_apellido=?, segundo_apellido=?, direccion_domiciliar=?, telefono_casa=?, telefono_movil=?, salario_base=?, bonificacion=? WHERE dpi=?";
        
        try (PreparedStatement declaracion = conexion.prepareStatement(consulta)) {
            declaracion.setString(1, (String) registro.get("primer_nombre"));
            declaracion.setString(2, (String) registro.get("segundo_nombre"));
            declaracion.setString(3, (String) registro.get("primer_apellido"));
            declaracion.setString(4, (String) registro.get("segundo_apellido"));
            declaracion.setString(5, (String) registro.get("direccion_domiciliar"));
            declaracion.setString(6, (String) registro.get("telefono_casa"));
            declaracion.setString(7, (String) registro.get("telefono_movil"));
            declaracion.setDouble(8, (Double) registro.get("salario_base"));
            declaracion.setDouble(9, (Double) registro.get("bonificacion"));
            declaracion.setString(10, dpi);

            declaracion.executeUpdate();
            registrarTransaccion("ACTUALIZAR_SINC", dpi, fuente);
        }
    }
    /**
     * Registra una transaccion en la bitacora con timestamp, operacion realizada, dpi afectado y detalles extra
     * @param operacion Tipo de operacion realizada (insertar, actualizar o eliminar)
     * @param dpi Numero de dpi afectado
     * @param detalles Informacion adicional sobre la operacion.
     */
    public void registrarTransaccion(String operacion, String dpi, String detalles) {
        String fechaHora = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String entradaBitacora = String.format("[%s] %s - DPI: %s - %s", fechaHora, operacion, dpi, detalles);
        
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_BITACORA, true))) {
            escritor.println(entradaBitacora);
        } catch (IOException e) {
            System.err.println("Error al escribir en bitácora: " + e.getMessage());
        }
    }
}

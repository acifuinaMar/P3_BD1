/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import modelo.ConexionBD;
import modelo.Persona;
import modelo.Sincronizar;
import java.sql.*;
/**
 * Controlador principal para gestionar las operaciones en los bd y sincronizacion. 
 * @author maryori
 */
public class ControladorBD {
    private final ConexionBD conexionBD;
    private final Sincronizar sincronizador;
    /**
     * Constructor que inicializa las instancias de ConexionBD y Sincronizar.
     * Usa el patron singleton
     */
    public ControladorBD() {
        this.conexionBD = ConexionBD.obtenerInstancia();
        this.sincronizador = new Sincronizar();
    }
    /**
     * Establece la conexion con la bd MySQL
     * @return true si la conexion fue exitosa, false si no
     */
    public boolean conectarMySQL() {
        try {
            conexionBD.conectarMySQL();
            return true;
        } catch (SQLException e) {
            System.err.println("Error conectando MySQL: " + e.getMessage());
            return false;
        }
    }
    /**
     * Establece la conexion con la bd Postgre
     * @return true si la conexion fue exitosa, false si no
     */
    public boolean conectarPostgreSQL() {
        try {
            conexionBD.conectarPostgreSQL();
            return true;
        } catch (SQLException e) {
            System.err.println("Error conectando ¨Postgre: " + e.getMessage());
            return false;
        }
    }
    /**
     * Cierra la conexion con la bd MySQL
     */
    public void desconectarMySQL() {
        conexionBD.desconectarMySQL();
    }
    /**
     * Cierra la conexion con la bd Postgre
     */
    public void desconectarPostgreSQL() {
        conexionBD.desconectarPostgre();
    }
    /**
     * Agrega una nueva persona a la bd activa; registra la transaccion en la bitacora
     * @param persona Objeto persona con los datos a insertar
     * @return true si la insercion fue exitosa, false si no
     */
    public boolean agregarPersona(Persona persona) {
        try {
            Connection conexion = conexionBD.getConexionActiva();
            if (conexion == null) return false;
            
            String consulta = "INSERT INTO personas (dpi, primer_nombre, segundo_nombre, primer_apellido, " +
                            "segundo_apellido, direccion_domiciliar, telefono_casa, telefono_movil, " +
                            "salario_base, bonificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement declaracion = conexion.prepareStatement(consulta)) {
                declaracion.setString(1, persona.getDpi());
                declaracion.setString(2, persona.getPrimerNombre());
                declaracion.setString(3, persona.getSegundoNombre());
                declaracion.setString(4, persona.getPrimerApellido());
                declaracion.setString(5, persona.getSegundoApellido());
                declaracion.setString(6, persona.getDireccionDomiciliar());
                declaracion.setString(7, persona.getTelefonoCasa());
                declaracion.setString(8, persona.getTelefonoMovil());
                declaracion.setDouble(9, persona.getSalarioBase());
                declaracion.setDouble(10, persona.getBonificacion());
                
                int filasActualizadas = declaracion.executeUpdate();
                if (filasActualizadas > 0){
                    sincronizador.registrarTransaccion("INSERTAR", persona.getDpi(), "BD: " + conexionBD.getBaseDatosActiva());
                    return true;
                }
                
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al agregar persona: " + e.getMessage());
        }
        return false;
    }
    /**
     * Actualiza los datos de una persona existente en la bd activa, registra la transaccion en la bitacora
     * @param persona Objeto Personacon los datos actualizados
     * @return true si la actualizacion fue exitosa, false si no
     */
    public boolean actualizarPersona(Persona persona) {
        try {
            Connection conexion = conexionBD.getConexionActiva();
            if (conexion == null) return false;
            
            String consulta = "UPDATE personas SET primer_nombre=?, segundo_nombre=?, primer_apellido=?, " +
                            "segundo_apellido=?, direccion_domiciliar=?, telefono_casa=?, telefono_movil=?, " +
                            "salario_base=?, bonificacion=? WHERE dpi=?";
            
            try (PreparedStatement declaracion = conexion.prepareStatement(consulta)) {
                declaracion.setString(1, persona.getPrimerNombre());
                declaracion.setString(2, persona.getSegundoNombre());
                declaracion.setString(3, persona.getPrimerApellido());
                declaracion.setString(4, persona.getSegundoApellido());
                declaracion.setString(5, persona.getDireccionDomiciliar());
                declaracion.setString(6, persona.getTelefonoCasa());
                declaracion.setString(7, persona.getTelefonoMovil());
                declaracion.setDouble(8, persona.getSalarioBase());
                declaracion.setDouble(9, persona.getBonificacion());
                declaracion.setString(10, persona.getDpi());
                
                int filasAfectadas = declaracion.executeUpdate();
                if (filasAfectadas > 0) {
                    sincronizador.registrarTransaccion("ACTUALIZAR", persona.getDpi(), "Usuario");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar persona: " + e.getMessage());
        }
        return false;
    }
    /**
     * Elimina una persona de la bd activa usando su DPI, registra la transaccin en la bitacora
     * @param dpi Numero de dpi de la persona a eliminar
     * @return true si la eliminacion fue exitosa, false si no
     */
    public boolean eliminarPersona(String dpi) {
        try {
            Connection conexion = conexionBD.getConexionActiva();
            if (conexion == null) return false;
            
            String consulta = "DELETE FROM personas WHERE dpi=?";
            try (PreparedStatement declaracion = conexion.prepareStatement(consulta)) {
                declaracion.setString(1, dpi);
                
                int filasAfectadas = declaracion.executeUpdate();
                if (filasAfectadas > 0) {
                    sincronizador.registrarTransaccion("ELIMINAR", dpi, "BD: " + conexionBD.getBaseDatosActiva() + " - Usuario");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar persona: " + e.getMessage());
        }
        return false;
    }
    /**
     * Busca una persona en la bd activa por su dpi
     * @param dpi Numero de dpi de la persona a buscar
     * @return Objeto persona si se encuentra, null si no existe o hay error
     */
    public Persona buscarPersona(String dpi) {
        try {
            Connection conexion = conexionBD.getConexionActiva();
            if (conexion == null) return null;
            
            String consulta = "SELECT * FROM personas WHERE dpi = ?";
            try (PreparedStatement declaracion = conexion.prepareStatement(consulta)) {
                declaracion.setString(1, dpi);
                ResultSet resultado = declaracion.executeQuery();
                
                if (resultado.next()) {
                    return new Persona(
                        resultado.getString("dpi"),
                        resultado.getString("primer_nombre"),
                        resultado.getString("segundo_nombre"),
                        resultado.getString("primer_apellido"),
                        resultado.getString("segundo_apellido"),
                        resultado.getString("direccion_domiciliar"),
                        resultado.getString("telefono_casa"),
                        resultado.getString("telefono_movil"),
                        resultado.getDouble("salario_base"),
                        resultado.getDouble("bonificacion")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar persona: " + e.getMessage());
        }
        return null;
    }
    /**
     * Sincroniza ambas bd y verifica que esten conectadas antes de hacer algo 
     * @return true si la sincronizacion fue exitosa, false si no
     */
    public boolean sincronizarBasesDatos() {
        if (!conexionBD.conectadas()) {
            return false;
        }
        return sincronizador.sincronizarBasesDatos();
    }
    /**
     * Verifica si ambas bd estan conectadas
     * @return true si ambas estan conectadas, false si no
     */
    public boolean conectadas(){
        return conexionBD.conectadas();
    }
    /**
     * Verifica si hay una conexion activa en alguna bd
     * @return  true si hay una conexion activa, false si no
     */
    public boolean hayConexionActiva() {
        return conexionBD.hayConexionActiva();
    }
    /**
     * Obtiene el nombre de la bd activa al momento
     * @return Nombre de la bd activa o null si no hay conexion
     */
    public String obtenerBaseDatosActiva() {
        return conexionBD.getBaseDatosActiva();
    }
}

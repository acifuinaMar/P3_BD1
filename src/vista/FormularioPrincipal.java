/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import controlador.ControladorBD;
import modelo.Persona;
/**
 * Clase principal que representa la ventana principal de la app.
 * Coordina todos los componentes de la gui y gestiona la interaccion entre
 * vista y controlador.
 * @author maryori
 */
public class FormularioPrincipal extends JFrame {
    private ControladorBD controlador;
    private PanelConexiones panelConexiones;
    private PanelFormulario panelFormulario;
    private PanelOperaciones panelOperaciones;
    private PanelBitacora panelBitacora;
    /**
     * Constructor que inicializa la ventana principal y todos sus componentes.
     * Crea la interfaz de usuario y configura los manejadores de eventos.
     */
    public FormularioPrincipal() {
        this.controlador = new ControladorBD();
        inicializarVentana();
        crearComponentes();
        organizarComponentes();
        configurarManejadoresEventos();
    }
    /**
     * Configura las propiedades basicas de la ventana.
     * Establece titulo, tamaño, posicion.
     */
    private void inicializarVentana() {
        setTitle("Proyecto 3");
        setSize(1000, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("icon.png").getImage()); 
        setVisible(true);
    }
    /**
     * Crea e inicializa todos los componentes de la gui.
     * Instancia los paneles especializados para cada funcionalidad.
     */
    private void crearComponentes() {
        panelConexiones = new PanelConexiones();
        panelFormulario = new PanelFormulario();
        panelOperaciones = new PanelOperaciones();
        panelBitacora = new PanelBitacora();
    }
    /**
     * Organiza los componentes en el layout de la ventana.
     * Usa BorderLayout para distribuir los paneles en la gui.
     */
    private void organizarComponentes() {
        //Panel arriba - Conexiones
        add(panelConexiones, BorderLayout.NORTH);
        
        //Panel centro - Formulario y Operaciones
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(panelFormulario, BorderLayout.CENTER);
        panelCentral.add(panelOperaciones, BorderLayout.EAST);
        
        add(panelCentral, BorderLayout.CENTER);
        add(panelBitacora, BorderLayout.SOUTH);
    }
    /**
     * Configura todos los manejadores de eventos para los componentes de la gui.
     * Asocia acciones a los botones y define el comportamiento de la aplicacion.
     */
    private void configurarManejadoresEventos() {
        // Conectar a MySQL
        panelConexiones.obtenerBotonConectarMySQL().addActionListener(e -> conectarMySQL());
        
        // Conectar a PostgreSQL
        panelConexiones.obtenerBotonConectarPostgreSQL().addActionListener(e -> conectarPostgreSQL());
        
        // Desconectar MySQL
        panelConexiones.obtenerBotonDesconectarMySQL().addActionListener(e -> desconectarMySQL());
        
        // Desconectar PostgreSQL
        panelConexiones.obtenerBotonDesconectarPostgreSQL().addActionListener(e -> desconectarPostgreSQL());
        
        // Botón Agregar Persona
        panelOperaciones.obtenerBotonAgregar().addActionListener(e -> agregarPersona());
        
        // Botón Actualizar Persona
        panelOperaciones.obtenerBotonActualizar().addActionListener(e -> actualizarPersona());
        
        // Botón Eliminar Persona
        panelOperaciones.obtenerBotonEliminar().addActionListener(e -> eliminarPersona());
        
        // Botón Buscar Persona
        panelOperaciones.obtenerBotonBuscar().addActionListener(e -> buscarPersona());
        
        // Botón Limpiar
        panelOperaciones.obtenerBotonLimpiar().addActionListener(e -> limpiarCampos());
        
        // Botón Sincronizar
        panelConexiones.obtenerBotonSincronizar().addActionListener(e -> {
            if (!controlador.conectadas()) {
                JOptionPane.showMessageDialog(this, 
                    "Debe conectar ambas bases de datos antes de sincronizar", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                panelBitacora.agregarMensaje("Error: Ambas bases de datos deben estar conectadas para sincronizar");
                return;
            }

            if (controlador.sincronizarBasesDatos()) {
                panelBitacora.agregarMensaje("Sincronización completada exitosamente");
                JOptionPane.showMessageDialog(this, "Sincronización completada exitosamente");
            } else {
                panelBitacora.agregarMensaje("Error durante la sincronización");
                JOptionPane.showMessageDialog(this, "Error durante la sincronización", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    /**
     * Establece conexion con la bd de MySQL.
     * Actualiza la interfaz y la bitacora segun el resultado
     */
    private void conectarMySQL() {
        if (controlador.conectarMySQL()) {
            panelBitacora.agregarMensaje("Conectado a MySQL exitosamente");
            panelOperaciones.habilitarBotones();
            panelConexiones.actualizarEstado("MySQL", true);
        } else {
            panelBitacora.agregarMensaje("Error al conectar con MySQL");
        }
    }
    /**
     * Establece conexion con la bd de Postgre.
     * Actualiza la interfaz y la bitacora segun el resultado
     */
    private void conectarPostgreSQL() {
        if (controlador.conectarPostgreSQL()) {
            panelBitacora.agregarMensaje("Conectado a PostgreSQL exitosamente");
            panelOperaciones.habilitarBotones();
            panelConexiones.actualizarEstado("PostgreSQL", true);
        } else {
            panelBitacora.agregarMensaje("Error al conectar con PostgreSQL");
        }
    }
    /**
     * Cierra la actualizacion con la bd MySQL.
     * Actualiza la interfaz y registra en bitacora
     */
    private void desconectarMySQL() {
        controlador.desconectarMySQL();
        panelBitacora.agregarMensaje("Desconectado de MySQL");
        panelOperaciones.deshabilitarBotones();
        panelConexiones.actualizarEstado("MySQL", false);
    }
    /**
     * Cierra la actualizacion con la bd Postgre.
     * Actualiza la interfaz y registra en bitacora
     */
    private void desconectarPostgreSQL() {
        controlador.desconectarPostgreSQL();
        panelBitacora.agregarMensaje("Desconectado de PostgreSQL");
        panelOperaciones.deshabilitarBotones();
        panelConexiones.actualizarEstado("PostgreSQL", false);
    }
    /**
     * Agrega una nueva persona a la bd activa.
     */
    private void agregarPersona() {
        if (panelFormulario.validarCampos()) {
            Persona persona = panelFormulario.obtenerDatosPersona();
            if (controlador.agregarPersona(persona)) {
                panelBitacora.agregarMensaje("Persona agregada exitosamente - DPI: " + persona.getDpi());
                panelFormulario.limpiarCampos();
            } else {
                panelBitacora.agregarMensaje("Error al agregar persona - DPI: " + persona.getDpi());
            }
        }
    }
    /**
     * Actualiza los datos de una persona existente en la bd.
     */
    private void actualizarPersona() {
        if (panelFormulario.validarCampos()) {
            Persona persona = panelFormulario.obtenerDatosPersona();
            if (controlador.actualizarPersona(persona)) {
                panelBitacora.agregarMensaje("Persona actualizada exitosamente - DPI: " + persona.getDpi());
            } else {
                panelBitacora.agregarMensaje("Error al actualizar persona - DPI: " + persona.getDpi());
            }
        }
    }
    /**
     * Elimina a una persona de la bd usando su dpi.
     */
    private void eliminarPersona() {
        String dpi = panelFormulario.obtenerDPI();
        if (!dpi.isEmpty()) {
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar a la persona con DPI: " + dpi + "?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (controlador.eliminarPersona(dpi)) {
                    panelBitacora.agregarMensaje("Persona eliminada exitosamente - DPI: " + dpi);
                    panelFormulario.limpiarCampos();
                } else {
                    panelBitacora.agregarMensaje("Error al eliminar persona - DPI: " + dpi);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un DPI para eliminar");
        }
    }
    /**
     * Busca una persona en la bd por su dpi.
     */
    private void buscarPersona() {
        String dpi = panelFormulario.obtenerDPI();
        if (!dpi.isEmpty()) {
            Persona persona = controlador.buscarPersona(dpi);
            if (persona != null) {
                panelFormulario.establecerDatosPersona(persona);
                panelBitacora.agregarMensaje("Persona encontrada - DPI: " + dpi);
            } else {
                panelBitacora.agregarMensaje("Persona no encontrada - DPI: " + dpi);
                JOptionPane.showMessageDialog(this, "No se encontró la persona con DPI: " + dpi);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un DPI para buscar");
        }
    }
    /**
     * Limpia los campos del form.
     */
    private void limpiarCampos() {
        panelFormulario.limpiarCampos();
        panelBitacora.agregarMensaje("Campos del formulario limpiados");
    }
    /**
     * Metodo principal que inicia la app.
     * Crea y muestra la ventana principal en el Event Dispatch Thread de Swing.
     * @param args Argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormularioPrincipal().setVisible(true);
        });
    }
}
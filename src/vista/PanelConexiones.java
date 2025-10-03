/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
/**
 * Panel para gestionar las conexiones a bd; muestra el estado de las conexiones y ejecuta la sincronizacion.
 * @author maryori
 */
public class PanelConexiones extends JPanel {
    private JButton botonConectarMySQL, botonConectarPostgreSQL;
    private JButton botonSincronizar, botonDesconectarMySQL, botonDesconectarPostgreSQL;
    private JLabel etiquetaEstadoMySQL, etiquetaEstadoPostgreSQL;
    /**
     * Constructor que inicializa el panel de conexiones.
     * Configura el layout y los margenes.
     */
    public PanelConexiones() {
        setLayout(new GridLayout(3, 1, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inicializarComponentes();
    }
    /**
     * Inicializa y configura todos los componentes graficos del panel.
     * Organiza la gui en: botones de conexion, indicadores de estado y control de sincronizacion.
     */
    private void inicializarComponentes() {
        // Panel de botones de conexión
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        botonConectarMySQL = new JButton("Conectar MySQL");
        botonConectarPostgreSQL = new JButton("Conectar PostgreSQL");
        botonDesconectarMySQL = new JButton("Desconectar MySQL");
        botonDesconectarPostgreSQL = new JButton("Desconectar PostgreSQL");
        
        panelBotones.add(botonConectarMySQL);
        panelBotones.add(botonConectarPostgreSQL);
        panelBotones.add(botonDesconectarMySQL);
        panelBotones.add(botonDesconectarPostgreSQL);
        
        // Panel de estado
        JPanel panelEstado = new JPanel(new GridLayout(1, 2, 10, 10));
        etiquetaEstadoMySQL = crearEtiquetaEstado("MySQL: Desconectado");
        etiquetaEstadoPostgreSQL = crearEtiquetaEstado("PostgreSQL: Desconectado");
        
        panelEstado.add(etiquetaEstadoMySQL);
        panelEstado.add(etiquetaEstadoPostgreSQL);
        
        // Panel de sincronización
        JPanel panelSincronizacion = new JPanel(new FlowLayout());
        botonSincronizar = new JButton("Sincronizar Bases de Datos");
        panelSincronizacion.add(botonSincronizar);
        
        add(panelBotones);
        add(panelEstado);
        add(panelSincronizacion);
    }
    /**
     * Crea una etiqueda de estado de forma visual.
     * Las etiquetas son rojo/verde para indicar el estado de la conexion.
     * @param texto Texto inicial para la etiqueta de estado.
     * @return JLabel con el formato de estado
     */
    private JLabel crearEtiquetaEstado(String texto) {
        JLabel etiqueta = new JLabel(texto, JLabel.CENTER);
        etiqueta.setOpaque(true);
        etiqueta.setBackground(Color.RED);
        etiqueta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return etiqueta;
    }
    /**
     * ACTUALIZA la etiqueta de una bd especifica.
     * Cambia el texto y color de fondo de la etiqueta para mostrar el estado de la conexion.
     * @param tipoBD Tipo de bd (MySQL o Postgre)
     * @param conectado true si la bd esta conectada, false si no
     */
    public void actualizarEstado(String tipoBD, boolean conectado) {
        JLabel etiquetaEstado = tipoBD.equals("MySQL") ? etiquetaEstadoMySQL : etiquetaEstadoPostgreSQL;
        if (conectado) {
            etiquetaEstado.setText(tipoBD + ": Conectado");
            etiquetaEstado.setBackground(Color.GREEN);
        } else {
            etiquetaEstado.setText(tipoBD + ": Desconectado");
            etiquetaEstado.setBackground(Color.RED);
        }
    }
    
    // Getters para los botones
    public JButton obtenerBotonConectarMySQL() { return botonConectarMySQL; }
    public JButton obtenerBotonConectarPostgreSQL() { return botonConectarPostgreSQL; }
    public JButton obtenerBotonSincronizar() { return botonSincronizar; }
    public JButton obtenerBotonDesconectarMySQL() { return botonDesconectarMySQL; }
    public JButton obtenerBotonDesconectarPostgreSQL() { return botonDesconectarPostgreSQL; }
}

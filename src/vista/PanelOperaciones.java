/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
/**
 * Panel con los botones para operaciones CRUD.
 * @author maryori
 */
public class PanelOperaciones extends JPanel {
    private JButton botonAgregar, botonActualizar, botonEliminar, botonBuscar, botonLimpiar;
    /**
     * Constructor que inicializa el panel de operaciones.
     */
    public PanelOperaciones() {
        setLayout(new GridLayout(6, 1, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Operaciones CRUD"));
        setPreferredSize(new Dimension(200, 300));
        inicializarBotones();
    }
    /**
     * Inicializa y configura todos los botones de operaciones.
     */
    private void inicializarBotones() {
        botonAgregar = new JButton("Agregar Persona");
        botonActualizar = new JButton("Actualizar Persona");
        botonEliminar = new JButton("Eliminar Persona");
        botonBuscar = new JButton("Buscar por DPI");
        botonLimpiar = new JButton("Limpiar Campos");
        
        //Deshabilitar botones hasta conectar a una base de datos
        botonAgregar.setEnabled(false);
        botonActualizar.setEnabled(false);
        botonEliminar.setEnabled(false);
        botonBuscar.setEnabled(false);
        
        //Agregar botones al panel
        add(botonAgregar);
        add(botonActualizar);
        add(botonEliminar);
        add(botonBuscar);
        add(botonLimpiar);
        
        add(new JLabel());
    }
    /**
     * Habilita todos los botones de operaciones CRUD.
     */
    public void habilitarBotones() {
        botonAgregar.setEnabled(true);
        botonActualizar.setEnabled(true);
        botonEliminar.setEnabled(true);
        botonBuscar.setEnabled(true);
    }
    /**
     * Deshabilita todos los botones de operaciones CRUD.
     */
    public void deshabilitarBotones() {
        botonAgregar.setEnabled(false);
        botonActualizar.setEnabled(false);
        botonEliminar.setEnabled(false);
        botonBuscar.setEnabled(false);
    }
    
    // Getters para los botones
    public JButton obtenerBotonAgregar() { return botonAgregar; }
    public JButton obtenerBotonActualizar() { return botonActualizar; }
    public JButton obtenerBotonEliminar() { return botonEliminar; }
    public JButton obtenerBotonBuscar() { return botonBuscar; }
    public JButton obtenerBotonLimpiar() { return botonLimpiar; }
}

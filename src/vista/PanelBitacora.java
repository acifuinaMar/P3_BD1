/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;


import javax.swing.*;
import java.awt.*;
import java.io.*;
/**
 * Panel que muestra y gestina la bitacora de actividades de la app.
 * @author maryori
 */
public class PanelBitacora extends JPanel {
    private JTextArea areaBitacora;
    private JScrollPane panelDesplazamiento;
    private static final String ARCHIVO_BITACORA = "bitacora_aplicacion.txt";
    /**
     * Constructor que inicializa el panel de bitacora.
     */
    public PanelBitacora() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Bitácora de Actividad"));
        setPreferredSize(new Dimension(800, 150));
        inicializarComponentes();
    }
    /**
     * Inicializa y configura todos los componentes graficos del panel.
     */
    private void inicializarComponentes() {
        areaBitacora = new JTextArea();
        areaBitacora.setEditable(false);
        areaBitacora.setBackground(Color.BLACK);
        areaBitacora.setForeground(Color.GREEN);
        areaBitacora.setFont(new Font("Consolas", Font.PLAIN, 12));
        
        panelDesplazamiento = new JScrollPane(areaBitacora);
        panelDesplazamiento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        add(panelDesplazamiento, BorderLayout.CENTER);
        
        //Limpiar bitácora
        JButton botonLimpiarBitacora = new JButton("Limpiar Bitácora");
        botonLimpiarBitacora.addActionListener(e -> limpiarBitacora());
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(botonLimpiarBitacora);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    /**
     * Agrega un nuevo mensaje a la bitacora con timestamp.
     * @param mensaje Mensaje a agregar a la bitacora
     */
    public void agregarMensaje(String mensaje) {
        String fechaHora = java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String mensajeCompleto = "[" + fechaHora + "] " + mensaje;
        
        // Agregar al area de texto
        areaBitacora.append(mensajeCompleto + "\n");
        
        //Hacer scroll automatico al final
        areaBitacora.setCaretPosition(areaBitacora.getDocument().getLength());
        
        //Guardar en archivo txt
        guardarEnArchivo(mensajeCompleto);
    }
    /**
     * Guarda un mensaje en el archivo de bitacora txt
     * @param mensaje El mensaje completo con timestamp a guardar
     */
    private void guardarEnArchivo(String mensaje) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_BITACORA, true))) {
            escritor.println(mensaje);
        } catch (IOException e) {
            System.err.println("Error al guardar en bitácora: " + e.getMessage());
        }
    }
    /**
     * Limpia el contenido de la bitacora en la gui.
     * No afecta el archivo txt.
     * Registra la limpieza como un nuevo mensaje.
     */
    private void limpiarBitacora() {
        areaBitacora.setText("");
        agregarMensaje("Bitácora limpiada");
    }
    /**
     * Limpia completamente la bitacora, en gui y en el txt.
     */
    public void limpiarBitacoraCompleta() {
        areaBitacora.setText("");
        //limpiar archivo
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_BITACORA))) {
            escritor.print("");
        } catch (IOException e) {
            System.err.println("Error al limpiar archivo de bitácora: " + e.getMessage());
        }
        agregarMensaje("Bitácora completamente limpiada");
    }
}

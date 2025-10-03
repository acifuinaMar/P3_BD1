/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Persona;
/**
 * Panel que contiene el form para ingresar y editar los datos de personas.
 * @author maryori
 */
public class PanelFormulario extends JPanel {
    private JTextField campoDPI, campoPrimerNombre, campoSegundoNombre, campoPrimerApellido, campoSegundoApellido;
    private JTextField campoDireccion, campoTelefonoCasa, campoTelefonoMovil, campoSalarioBase, campoBonificacion;
    /**
     * Constructor que inicializa el panel del form.
     */
    public PanelFormulario() {
        setLayout(new GridLayout(5, 4, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Datos de la Persona"));
        inicializarCampos();
    }
    /**
     * Inicializa todos los campos de texto del form y los agrega al panel.
     */
    private void inicializarCampos() {
        campoDPI = new JTextField();
        campoPrimerNombre = new JTextField();
        campoSegundoNombre = new JTextField();
        campoPrimerApellido = new JTextField();
        campoSegundoApellido = new JTextField();
        campoDireccion = new JTextField();
        campoTelefonoCasa = new JTextField();
        campoTelefonoMovil = new JTextField();
        campoSalarioBase = new JTextField();
        campoBonificacion = new JTextField();
        
        //Etiquetas y campos al panel
        agregarCampo("DPI:", campoDPI);
        agregarCampo("Primer Nombre:", campoPrimerNombre);
        agregarCampo("Segundo Nombre:", campoSegundoNombre);
        agregarCampo("Primer Apellido:", campoPrimerApellido);
        agregarCampo("Segundo Apellido:", campoSegundoApellido);
        agregarCampo("Dirección Domiciliar:", campoDireccion);
        agregarCampo("Teléfono Casa:", campoTelefonoCasa);
        agregarCampo("Teléfono Móvil:", campoTelefonoMovil);
        agregarCampo("Salario Base:", campoSalarioBase);
        agregarCampo("Bonificación:", campoBonificacion);
    }
    /**
     * Agrega un campo al form con su etiqueta.
     * @param etiqueta Texto descriptivo del campo
     * @param campo Componente JTextField a agregar
     */
    private void agregarCampo(String etiqueta, JTextField campo) {
        add(new JLabel(etiqueta));
        add(campo);
    }
    /**
     * Valida los campos obligatorios del form.
     * @return true si los campos obligatorios (dpi, primer nombre y primer apellido) estan completos, false si no.
     */
    public boolean validarCampos() {
        if (campoDPI.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El DPI es obligatorio");
            campoDPI.requestFocus();
            return false;
        }
        if (campoPrimerNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El primer nombre es obligatorio");
            campoPrimerNombre.requestFocus();
            return false;
        }
        if (campoPrimerApellido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El primer apellido es obligatorio");
            campoPrimerApellido.requestFocus();
            return false;
        }
        
        // Validar que salario y bonificación sean números
        try {
            if (!campoSalarioBase.getText().trim().isEmpty()) {
                Double.valueOf(campoSalarioBase.getText().trim());
            }
            if (!campoBonificacion.getText().trim().isEmpty()) {
                Double.valueOf(campoBonificacion.getText().trim());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Salario y bonificación deben ser números válidos");
            return false;
        }
        
        return true;
    }
    /**
     * Obtiene los datos del form y crea un objeto Persona.
     * @return Objeto Persona con los datos del form.
     */
    public Persona obtenerDatosPersona() {
        double salario = campoSalarioBase.getText().trim().isEmpty() ? 0 : 
                        Double.parseDouble(campoSalarioBase.getText().trim());
        double bonificacion = campoBonificacion.getText().trim().isEmpty() ? 0 : 
                             Double.parseDouble(campoBonificacion.getText().trim());
        
        return new Persona(
            campoDPI.getText().trim(),
            campoPrimerNombre.getText().trim(),
            campoSegundoNombre.getText().trim(),
            campoPrimerApellido.getText().trim(),
            campoSegundoApellido.getText().trim(),
            campoDireccion.getText().trim(),
            campoTelefonoCasa.getText().trim(),
            campoTelefonoMovil.getText().trim(),
            salario,
            bonificacion
        );
    }
    /**
     * Establece los datos de una persona en los campos del form.
     * @param persona Objeto Persona con los datos a mostrar en el form.
     */
    public void establecerDatosPersona(Persona persona) {
        campoDPI.setText(persona.getDpi());
        campoPrimerNombre.setText(persona.getPrimerNombre());
        campoSegundoNombre.setText(persona.getSegundoNombre());
        campoPrimerApellido.setText(persona.getPrimerApellido());
        campoSegundoApellido.setText(persona.getSegundoApellido());
        campoDireccion.setText(persona.getDireccionDomiciliar());
        campoTelefonoCasa.setText(persona.getTelefonoCasa());
        campoTelefonoMovil.setText(persona.getTelefonoMovil());
        campoSalarioBase.setText(String.valueOf(persona.getSalarioBase()));
        campoBonificacion.setText(String.valueOf(persona.getBonificacion()));
    }
    /**
     * Limpia los campos del form, colocandolos vacios.
     */
    public void limpiarCampos() {
        campoDPI.setText("");
        campoPrimerNombre.setText("");
        campoSegundoNombre.setText("");
        campoPrimerApellido.setText("");
        campoSegundoApellido.setText("");
        campoDireccion.setText("");
        campoTelefonoCasa.setText("");
        campoTelefonoMovil.setText("");
        campoSalarioBase.setText("");
        campoBonificacion.setText("");
    }
    /**
     * Obtiene el valor del campo dpi.
     * @return Texto del campo dpi, sin espacios.
     */
    public String obtenerDPI() {
        return campoDPI.getText().trim();
    }
    /**
     * Establece el valor del campo dpi.
     * @param dpi Valor a establecer en el campo DPI.
     */
    public void establecerDPI(String dpi) {
        campoDPI.setText(dpi);
    }
}

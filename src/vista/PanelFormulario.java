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
        String dpi = campoDPI.getText().trim();
        if (dpi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El DPI es obligatorio");
            campoDPI.requestFocus();
            return false;
        }
        if(!validarCUI(dpi)){
            JOptionPane.showMessageDialog(this, "El DPI no tiene un formato válido");
            return false;
        }
        String primerNombre = campoPrimerNombre.getText().trim();
        if (primerNombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El primer nombre es obligatorio");
            campoPrimerNombre.requestFocus();
            return false;
        }
        String primerApellido = campoPrimerApellido.getText().trim();
        if (primerApellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El primer apellido es obligatorio");
            campoPrimerApellido.requestFocus();
            return false;
        }
        String segundoNombre = campoSegundoNombre.getText().trim();
        String segundoApellido = campoSegundoApellido.getText().trim();
        if(!validarNombres(primerNombre) || !validarNombres(segundoNombre) || !validarNombres(primerApellido) || !validarNombres(segundoApellido)){
            JOptionPane.showMessageDialog(this, "Revise los nombres y apellidos");
            return false;
        }
        
        String telefonoCasa = campoTelefonoCasa.getText().trim();
        String telefonoMovil = campoTelefonoMovil.getText().trim();
        if(!validarTelefono(telefonoCasa) || !validarTelefono(telefonoMovil)){
            JOptionPane.showMessageDialog(this, "Los teléfonos deben cumplir con el formato ####-####");
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
    
    public static boolean validarCUI(String cui){ 
        int digito; int suma = 0; 
        String numeros, codigo; 
        int numVerificador; 
        int[] arreglo = {9, 8, 7, 6, 5, 4, 3, 2}; 
        boolean encontrado = false; 
        boolean blnValidaCui = true; 
        int verificadorCalculado;
        String[] arregloCodigos = {
        "0101", "0102", "0103", "0104", "0105", "0106", "0107", "0108", "0109", "0110", "0111", "0112", "0113", "0114", "0115", "0116", "0117",
        "0201", "0202", "0203", "0204", "0205", "0206", "0207", "0208", 
        "0301", "0302", "0303", "0304", "0305", "0306", "0307", "0308", "0309", "0310", "0311", "0312", "0313", "0314", "0315", "0316",
        "0401", "0402", "0403", "0404", "0405", "0406", "0407", "0408", "0409", "0410", "0411", "0412", "0413", "0414", "0415", "0416",
        "0501", "0502", "0503", "0504", "0505", "0506", "0507", "0508", "0509", "0510", "0511", "0512", "0513",
        "0601", "0602", "0603", "0604", "0605", "0606", "0607", "0608", "0609", "0610", "0611", "0612", "0613", "0614",
        "0701", "0702", "0703", "0704", "0705", "0706", "0707", "0708", "0709", "0710", "0711", "0712", "0713", "0714", "0715", "0716", "0717", "0718", "0719",
        "0801", "0802", "0803", "0804", "0805", "0806", "0807", "0808",
        "0901", "0902", "0903", "0904", "0905", "0906", "0907", "0908", "0909", "0910", "0911", "0912", "0913", "0914", "0915", "0916", "0917", "0918", "0919", "0920", "0921", "0922", "0923", "0924",
        "1001", "1002", "1003", "1004", "1005", "1006", "1007", "1008", "1009", "1010", "1011", "1012", "1013", "1014", "1015", "1016", "1017", "1018", "1019", "1020",
        "1101", "1102", "1103", "1104", "1105", "1106", "1107", "1108", "1109",
        "1201", "1202", "1203", "1204", "1205", "1206", "1207", "1208", "1209", "1210", "1211", "1212", "1213", "1214", "1215", "1216", "1217", "1218", "1219", "1220", "1221", "1222", "1223", "1224", "1225", "1226", "1227", "1228", "1229",
        "1301", "1302", "1303", "1304", "1305", "1306", "1307", "1308", "1309", "1310", "1311", "1312", "1313", "1314", "1315", "1316", "1317", "1318", "1319", "1320", "1321", "1322", "1323", "1324", "1325", "1326", "1327", "1328", "1329", "1330", "1331", "1332",
        "1401", "1402", "1403", "1404", "1405", "1406", "1407", "1408", "1409", "1410", "1411", "1412", "1413", "1414", "1415", "1416", "1417", "1418", "1419", "1420", "1421", "1422", "1423", "1424",
        "1501", "1502", "1503", "1504", "1505", "1506", "1507", "1508", "1509",
        "1601", "1602", "1603", "1604", "1605", "1606", "1607", "1608", "1609", "1610", "1611", "1612", "1613", "1614", "1615", "1616", "1617", "1618",
        "1701", "1702", "1703", "1704", "1705", "1706", "1707", "1708", "1709", "1710", "1711", "1712",
        "1801", "1802", "1803", "1804", "1805",
        "1901", "1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910",
        "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011",
        "2101", "2102", "2103", "2104", "2105", "2106", "2107",
        "2201", "2202", "2203", "2204", "2205", "2206", "2207", "2208", "2209", "2210", "2211", "2212", "2213", "2214", "2215", "2216", "2217"
        };

        // Si el CUI tiene 13 caracteres, se divide en subcadenas
        if (cui.length() == 13) {
            numeros = cui.substring(0,8);
            numVerificador = Integer.parseInt(cui.substring(8,9));
            codigo = cui.substring(9,13);

            // Calculo del número verificador basado en "numeros"
            for (int i = 0; i < 8; i++){
                digito = Character.getNumericValue(numeros.charAt(i));
                suma += digito * arreglo[i];
            }

            // Aplicar módulo 11
            verificadorCalculado = 11 - (suma % 11);
            if (verificadorCalculado == 11 || verificadorCalculado == 10) {
                verificadorCalculado = 1;
            }

            // Validar el código (ajusta el arregloCodigos como sea necesario)
            for (String arregloCodigo : arregloCodigos) {
                if (arregloCodigo.equals(codigo)) {
                    encontrado = true;
                    break;
                }
            }

            // Validación final del CUI
            blnValidaCui = encontrado && numVerificador == verificadorCalculado;
        } else {
            System.out.println("El CUI debe tener 13 dígitos");
            blnValidaCui = false;
        }
        return blnValidaCui;
    }
    
    public static boolean validarNombres(String cadena){
        return !cadena.isEmpty() && cadena.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }
    public static boolean validarTelefono(String telefono){
        return !(telefono.length() != 9 || !telefono.matches("[2-7]\\d{3}-\\d{4}"));
    }
    /**
     * Establece el valor del campo dpi.
     * @param dpi Valor a establecer en el campo DPI.
     */
    public void establecerDPI(String dpi) {
        campoDPI.setText(dpi);
    }
}

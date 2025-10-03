/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase que modela un objeto Persona (unidad basica del proyecto)
 * @author maryori
 */
public class Persona {
    private String dpi;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String direccionDomiciliar;
    private String telefonoCasa;
    private String telefonoMovil;
    private double salarioBase;
    private double bonificacion;
    
    /**
     * Constructor del objeto Persona.
     * @param dpi Numero de documento personal de identificacion (PK)
     * @param primerNombre Primer nombre de la persona.
     * @param segundoNombre Segundo nombre de la persona.
     * @param primerApellido Primer apellido de la persona.
     * @param segundoApellido Segundo apellido de la persona.
     * @param direccionDomiciliar Direccion de la persona.
     * @param telefonoCasa Telefono de casa de la persona.
     * @param telefonoMovil Telefono celular de la persona.
     * @param salarioBase Salario base de la persona.
     * @param bonificacion Bonificacion de la persona.
     */
    public Persona(String dpi, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String direccionDomiciliar, String telefonoCasa, String telefonoMovil, double salarioBase, double bonificacion) {
        this.dpi = dpi;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.direccionDomiciliar = direccionDomiciliar;
        this.telefonoCasa = telefonoCasa;
        this.telefonoMovil = telefonoMovil;
        this.salarioBase = salarioBase;
        this.bonificacion = bonificacion;
    }
    
    //Getters y setters
    /**
     * Regresa el dpi de la persona
     * @return dpi
     */
    public String getDpi() {
        return dpi;
    }
    /**
     * Establece el dpi de la persona
     * @param dpi 
     */
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }
    /**
     * Regresa el primer nombre de la persona
     * @return primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }
    /**
     * Establece el primer nombre de la persona
     * @param primerNombre 
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }
    /**
     * Regresa el segundo nombre de la persona
     * @return segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }
    /**
     * Establece el segundo nombre de la persona
     * @param segundoNombre 
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }
    /**
     * Regresa el primer apellido de la persona
     * @return primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }
    /**
     * Establece el primer apellido de la persona
     * @param primerApellido 
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }
    /**
     * Regresa el segundo apellido de la persona
     * @return segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }
    /**
     * Establece el segundo apellido
     * @param segundoApellido 
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }
    /**
     * Regresa la direccion de la persona
     * @return direccionDomiciliar
     */
    public String getDireccionDomiciliar() {
        return direccionDomiciliar;
    }
    /**
     * Establece la direccion de la persona
     * @param direccionDomiciliar 
     */
    public void setDireccionDomiciliar(String direccionDomiciliar) {
        this.direccionDomiciliar = direccionDomiciliar;
    }
    /**
     * Regresa el telefono de casa
     * @return telefonoCasa
     */
    public String getTelefonoCasa() {
        return telefonoCasa;
    }
    /**
     * Establece el telefono de casa
     * @param telefonoCasa 
     */
    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }
    /**
     * Regresa el telefono celular
     * @return telefonoMovil
     */
    public String getTelefonoMovil() {
        return telefonoMovil;
    }
    /**
     * Establece el telefono celular
     * @param telefonoMovil 
     */
    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }
    /**
     * Regresa el salario base
     * @return 
     */
    public double getSalarioBase() {
        return salarioBase;
    }
    /**
     * Establece el salario base
     * @param salarioBase 
     */
    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }
    /**
     * Regresa la bonificacion
     * @return bonificacion
     */
    public double getBonificacion() {
        return bonificacion;
    }
    /**
     * Establece la bonificacion
     * @param bonificacion 
     */
    public void setBonificacion(double bonificacion) {
        this.bonificacion = bonificacion;
    }
}

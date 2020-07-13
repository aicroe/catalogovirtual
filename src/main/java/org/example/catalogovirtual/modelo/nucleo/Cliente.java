package org.example.catalogovirtual.modelo.nucleo;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Validador;
import java.util.ArrayList;
import java.util.Date;


/**
 * Clase que modela la información de los clientes 
 * (personas que alquilan vehículos) de la empresa y las operaciones
 * que se pueden realizar con la información.
 * 
 * @author Francisco Camacho
 * @version 14052015
 */
public class Cliente extends Usuario
{
    private String nombre;
    private int ci;
    private char licencia;
    private int telefono;
    private String direccion;
    private String lugarTrabajo;
    private String ocupacion;
    private ArrayList<Solicitud> solicitudes;
        
    private Cliente(int ci,  String password, 
            String nombre, char licencia, int telefono){
        
        super(String.valueOf(ci), password);
        this.nombre = nombre;
        this.ci = ci;
        this.licencia = licencia;
        this.telefono = telefono;
        solicitudes = new ArrayList<>();
        direccion = null;
        lugarTrabajo = null;
        ocupacion = null;
    }

    /**
     * @return the nombre
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     * @return the ci
     */
    public int getCi(){
        return ci;
    }

    /**
     * @param ci the CI to set
     */
    public void setCi(int ci){
        this.ci = ci;
    }

    /**
     * @return the licencia
     */
    public char getTipoLicencia(){
        return licencia;
    }

    /**
     * @param licencia the licencia to set
     */
    public void setLicencia(char licencia){
        this.licencia = licencia;
    }

    /**
     * @return the telf
     */
    public long getTelefono(){
        return telefono;
    }

    /**
     * @param telefono the telf to set
     */
    public void setTelefono(int telefono){
        this.telefono = telefono;
    }

    /**
     * @return the solicitudes
     */
    public ArrayList<Solicitud> getSolicitudes(){
        return solicitudes;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the lugarTrabajo
     */
    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    /**
     * @param lugarTrabajo the lugarTrabajo to set
     */
    public void setLugarTrabajo(String lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
    }

    /**
     * @return the ocupacion
     */
    public String getOcupacion() {
        return ocupacion;
    }

    /**
     * @param ocupacion the ocupacion to set
     */
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
    
    /**
     * @param solicitud 
     */
    public void aniadirSolicitud(Solicitud solicitud){
        
        if(puedeAniadirSolicitud() && solicitud.getCliente().equals(this)){
            solicitudes.add(solicitud);
        }else{
            throw new UnsupportedOperationException();
        }
    }
    
    /**
     * Elimina una solicitud dado sus fechas y el auto alquilado.
     * 
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @return devuelve true si elimino la solicitud.
     */
    public boolean eliminarSolicitud(Date fechaInicial, Date fechaFinal, Auto auto){
        
        int i = 0;
        while(i< solicitudes.size()){
            if(solicitudes.get(i).esIgual(fechaInicial, fechaFinal, auto)){
                return solicitudes.remove(i) != null;
            }else{
                i++;
            }
        }
        return false;
    }
    
    /**
     * @return si puede o no aniadir mas solicitudes
     */
    public boolean puedeAniadirSolicitud(){
        
        int numSolActivas = 0;
        for(Solicitud solicitud: solicitudes){
            if(solicitud.getEstado().equals(Solicitudes.ESTADO_EN_CURSO) ||
                    solicitud.getEstado().equals(Solicitudes.ESTADO_VENCIDO) ||
                    solicitud.getEstado().equals(Solicitudes.ESTADO_RESERVA)){
                numSolActivas++;
            }
        }
        return numSolActivas < Preferencias.getCantMaxSolCliente();
    }
    
    /**
     * Metodo para recuperar la contrasenia del cliente.
     * 
     * @param nuevaContr nueva contrasenia
     * @param clave el telefono de este usuario
     * @throws DatosInvalidosException 
     */
    public void recuperarContrPorTelf(
            String nuevaContr, 
            int clave) throws DatosInvalidosException{
        if(clave == telefono){
            if(Validador.validar(nuevaContr) && nuevaContr.length() >= 4){
                if(!setPassword(getPassword(), nuevaContr)){
                    throw new UnsupportedOperationException();
                }
                return;
            }
            throw new DatosInvalidosException("Password minimo 4 letras o numeros");
        }
        throw new DatosInvalidosException("Telefono no registrado");
    }
    
    /**
     * Crea un cliente de forma segura.
     * 
     * @param ci
     * @param password
     * @param nombre
     * @param licencia
     * @param telefono
     * @return el cliente creado
     * @throws DatosInvalidosException 
     */
    public static Cliente crearCliente(
            int ci, 
            String password, 
            String nombre,
            char licencia, 
            int telefono) throws DatosInvalidosException{
        String mensaje;
        if(Validador.validar(String.valueOf(ci)) && longitudValida(ci, 6, 8)){
            if(Validador.validar(password) && password.length() >= 4){
                if(nombre != null && nombre.length() >= 4){
                    if(longitudValida(telefono, 6, 8)){
                        if(licencia == 'A' || licencia == 'B' || licencia == 'C' ||
                                licencia == 'P'){
                                return new Cliente(ci, password, nombre, licencia, telefono);
                        }else{
                            mensaje = "Licencia debe ser 'A', 'B', 'C' o 'P'";
                        }
                    }else{
                        mensaje = "Telefono debe tener de 6 a 8 digitos";
                    }
                }else{
                    mensaje = "Nombre minimo 4 caracteres";
                }
            }else{
                mensaje = "Password minimo 4 letras o numeros";
            }
        }else{
            mensaje = "CI debe tener de 6 a 8 digitos";
        }
        throw new DatosInvalidosException(mensaje);
    }
    
    private static boolean longitudValida(long numero, int minLongitud, int maxLongitud)
    {
        int longitud = String.valueOf(numero).length();
        return longitud >= minLongitud && longitud <= maxLongitud;
    }
    
    @Override
    public String toString(){
        
        return String.valueOf(ci);
    }
}
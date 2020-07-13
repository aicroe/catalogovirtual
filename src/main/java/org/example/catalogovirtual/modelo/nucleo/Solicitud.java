package org.example.catalogovirtual.modelo.nucleo;

import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import java.io.Serializable;
import java.util.Date;

/**
 * La clase solicitud representa una solicitud creada al momento de alquilar 
 * un auto.
 * 
 * @author empujesoft
 * @version 2015.08.02
 */
public class Solicitud implements Comparable<Solicitud>, Serializable
{
    private Auto auto;
    private Cliente cliente;
    private Date fechaInicial;
    private Date fechaFinal;
    private String estado;
    private String observaciones;
    private double precioTotal;
    private double garantia;
    private double recaudacionFinal;
    
    public Solicitud(Auto auto, Cliente cliente){
        fechaInicial = null;
        fechaFinal = null;
        precioTotal = -1;
        estado = Solicitudes.ESTADO_RESERVA;
        observaciones = "";
        this.auto = auto;
        this.cliente = cliente;
        garantia = auto.getGarantia();
        recaudacionFinal = 0;
    }
    
    public Date getFechaInicial(){
        return fechaInicial;
    }
    
    public Date getFechaFinal(){
        return fechaFinal;
    }
    
    public double getPrecioTotal(){
        return precioTotal;
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    
    public Auto getAuto(){
        return auto;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public double getGarantia() {
        return garantia;
    }
    
    public void setFechaInicio(Date fecha){
        fechaInicial = fecha;
    }
    
    public void setFechaFinal(Date fecha){
        fechaFinal = fecha;
    }
    
    public void setPrecioTotal(double precio){
        precioTotal = precio;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public double getRecaudacionFinal() {
        return recaudacionFinal;
    }
    
    public void setRecaudacionFinal(double recaudacionFinal) {
        this.recaudacionFinal = recaudacionFinal;
    }
    
    public boolean esIgual(Date fechaInicial, Date fechaFinal, Auto auto)
    {
        return this.fechaFinal.compareTo(fechaFinal) == 0 &&
                this.fechaInicial.compareTo(fechaInicial) == 0 && 
                this.auto.compareTo(auto) == 0;
    }
    
    @Override
    public int compareTo(Solicitud solicitud){
        int comparacion = fechaFinal.compareTo(solicitud.getFechaFinal());
        if(comparacion == 0){
            if(auto.compareTo(solicitud.getAuto()) == 0 && 
                    cliente.compareTo(solicitud.getCliente()) == 0){
                comparacion = 0;
            }else{
                comparacion = -1;
            }
        }
        return comparacion;
    }
    
    @Override
    public String toString()
    {
        String string = "";
        string += "Solicitante:\n    CI:\t" + cliente.getCi() + "\n";
        string += "    Nombre:\t" + cliente.getNombre() + "\n";
        string += "    Licencia:\t" + cliente.getTipoLicencia() + "\n";
        string += "    Telefono:\t" + cliente.getTelefono() + "\n\n";
        string += "Reserva:\n    Auto:\t" + auto.getPlaca() + "\n";
        string += "    Precio Total:\t" + precioTotal + "\n";
        string += "    Garantia:\t" + garantia + "\n";
        string += "    Fecha Inicio:\t" + fechaInicial.toString() + "\n";
        string += "    Fecha Final:\t" + fechaFinal.toString() + "\n";
        return string;
    }
}

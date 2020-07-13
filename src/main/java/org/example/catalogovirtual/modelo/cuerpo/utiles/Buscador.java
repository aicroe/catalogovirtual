/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.modelo.cuerpo.utiles;

import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import java.util.ArrayList;

/**
 *
 * @author garcia
 */
public class Buscador {
    
    private String paramBusqueda;
    private MotorDeBusqueda motor;
    
    /**
     * Crea el Buscador con el parametro de busqueda.
     * @param paramBusqueda el parametro de filtrado
     */
    public Buscador(String paramBusqueda)
    {
        this.paramBusqueda = paramBusqueda;
        motor = null;
    }
    
    /**
     * Metodo para que las subclases actualicen el motor, si el motor no es 
     * actualizado no funciona nada.
     * @param motor el filtrado que se usara para buscar una lista
     */
    protected void setMotor(MotorDeBusqueda motor)
    {
        this.motor = motor;
    }
    
    /**
     * El metodo que se usa para buscar una lista de autos segun el filtro 
     * indicado al  crearse este objeto. Delega su implementacion a otro 
     * metodo buscar, que usa el motor.
     * @param autos lista de autos a buscar
     * @return autos devuelve los autos filtrados, o una 
     *          lista vacia si no filtra a ninguno
     */
    public ArrayList<Solicitud> buscar(ArrayList<Solicitud> autos)
    {
        if(motor == null) throw new UnsupportedOperationException();
        return buscar(autos, motor);
    }
    
    /**
     * Metodo que busca por medio del motor.
     * 
     * @param autos los autos a buscar
     * @param motor el motor, es el encargado de saber si un 
     *          auto esta o no dentro el filtro
     * @return devuelve una lista con los autos filtrados
     */
    private ArrayList<Solicitud> buscar(ArrayList<Solicitud> solicitudes, 
            MotorDeBusqueda motor){
        ArrayList<Solicitud> buscados = new ArrayList<>();
        for(Solicitud solicitud: solicitudes){
            if(motor.emparejan(solicitud, paramBusqueda)){
                buscados.add(solicitud);
            }
        }        
        return buscados;
    }
    
    /**
     * Clase encargada de saber si dado un parametro de busqueda y una 
     * solicitud esta solicitud es la buscada o no.
     */
    protected abstract class MotorDeBusqueda
    {
        /**
         * Prueba si la solicitud es la buscada o no.
         * @param solicitud una solicitud
         * @param param el parametro de busqueda
         * @return devuelve true si emparejan
         */
        public abstract boolean emparejan(Solicitud solicitud, String param);
    }
}

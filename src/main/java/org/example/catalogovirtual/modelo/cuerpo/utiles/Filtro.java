package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;
import java.util.ArrayList;


/**
 * Clase que modela los distintos filtros que tenemos
 * 
 * @author empujesoft
 * @version 2015.06.25
 */
public abstract class Filtro<T>
{
    private T filtro;
    private String nombre;
    private Filtrador filtrador;
    
    /**
     * Crea el filtro con el parametro de filtrado y el nombre.
     * 
     * @param filtro el parametro de filtrado
     * @param nombre el nombre del filtro
     */
    public Filtro(T filtro, String nombre)
    {
        this.filtro = filtro;
        this.nombre = nombre;
        filtrador = null;
    }
    
    /**
     * Metodo para que las subclases actualicen el filtrador, si el filtrador no es actualizado
     * no funciona nada.
     * 
     * @param filtrador el filtrado que se usara para filtrar una lista
     */
    protected void setFiltrador(Filtrador filtrador)
    {
        this.filtrador = filtrador;
    }
    
    @Override
    public String toString()
    {
        return nombre;
    }
    
    /**
     * El metodo que se usa para filtrar una lista de autos segun el filtro indicado al 
     * crearse este objeto. Delega su implementacion a otro metodo filtrar, que usa el filtrador.
     * 
     * @param autos lista de autos a filtrar
     * @return autos devuelve los autos filtrados, o una lista vacia si no filtra a ninguno
     */
    public ArrayList<Auto> filtrar(ArrayList<Auto> autos)
    {
        if(filtrador == null) throw new UnsupportedOperationException();
        return filtrar(autos, filtrador);
    }
    
    /**
     * Metodo que filtra por medio del filtrador.
     * 
     * @param autos los autos a filtrar
     * @param filtrador el filtrador, es el encargado de saber si un auto esta o no dentro el
     *          filtro
     * @return devuelve una lista con los autos filtrados
     */
    private ArrayList<Auto> filtrar(ArrayList<Auto> autos, Filtrador filtrador)
    {
        ArrayList<Auto> filtrados = new ArrayList<>();
        for(Auto auto: autos){
            if(filtrador.estaDentroElFiltro(auto, filtro)){
                filtrados.add(auto);
            }
        }
        
        return filtrados;
    }
    
    /**
     * Clase encargada de saber si dado un filtro y un auto este auto entra en los filtrados
     * o no.
     */
    protected abstract class Filtrador
    {
        /**
         * Ve si el auto pasado esta dentro de las especificaciones del filtro.
         * 
         * @param auto el auto al que se quiere ver si esta dentro de el filtro
         * @param filtro el filtro que especifica si el auto esta o no dento de los filtrados
         * @return devuelve true si el auto esta dentro del filtro
         */
        public abstract boolean estaDentroElFiltro(Auto auto, T filtro);
    }
}

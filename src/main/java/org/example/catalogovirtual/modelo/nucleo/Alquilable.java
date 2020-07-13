package org.example.catalogovirtual.modelo.nucleo;

import java.io.Serializable;


/**
 * Representa a un objeto que es alquilable en el catalogo
 *
 * @author empujesoft
 * @version 2015.17.05
 */
public abstract class Alquilable implements Serializable
{
    private double precioPorDia;
    private boolean disponible;
    
    /**
     * Al crearse un objeto alquilable inicia disponible
     */
    public Alquilable()
    {
        precioPorDia = -1;
        disponible = true;
    }
    
    /**
     * Devuelve el precio por dia que tiene este objeto.
     * 
     * @return el precion por dia del alquilable
     */
    public double getPrecioPorDia()
    {
        return precioPorDia;
    }

    /**
     * Actualizar el precio por dia.
     * 
     * @param precio 
     */
    public void setPrecioPorDia(double precio)
    {
        precioPorDia = precio;
    }

    /**
     * Revisa si no fue alquilado o ya esta disponible.
     * 
     * @return true si esta disponible
     */
    public boolean estaDisponible()
    {
        return disponible;
    }

    /**
     * Actualiza el estado disponible de este objeto.
     * 
     * @param disponible determina si esta o no disponible
     */
    public void setDisponible(boolean disponible)
    {
        this.disponible = disponible;
    }
}

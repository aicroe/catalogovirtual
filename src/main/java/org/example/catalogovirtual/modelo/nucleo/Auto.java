package org.example.catalogovirtual.modelo.nucleo;

import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;


/**
 * Clase que modela la informacion de los automoviles y el acceso a la misma
 * 
 * @autor empujesoft
 * @version 2015.03.15
 */
public abstract class Auto extends Alquilable implements Comparable<Auto>
{
    private String nombre;
    private String placa;
    private boolean tipoDeCaja;
    private int modelo;
    private int numeroDePasajeros;
    private final String nombreCategoria;
    private final int categoria;
    private Garantia garantia;
    
    public Auto(String nombreCategoria, int categoria, Garantia garantia)
    {
        nombre = null;
        placa = null;
        tipoDeCaja = false;
        modelo = -1;
        numeroDePasajeros = -1;
        this.nombreCategoria = nombreCategoria;
        this.categoria = categoria;
        this.garantia = garantia;
    }
    
    @Override
    public int compareTo(Auto auto)
    {
        if(auto == null) throw new NullPointerException("auto no puede ser nulo.");
        return getPlaca().compareTo(auto.getPlaca());
    }
    
    @Override
    public String toString()
    {
        return placa;
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the placa
     */
    public String getPlaca()
    {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa)
    {
        this.placa = placa;
    }

    /**
     * @return the nombreCategoria
     */
    public String getNombreCategoria()
    {
        return nombreCategoria;
    }

    /**
     * @return the tipoDeCaja
     */
    public boolean getTipoDeCaja()
    {
        return tipoDeCaja;
    }

    /**
     * @param tipoDeCaja the tipoDeCaja to set
     */
    public void setTipoDeCaja(boolean tipoDeCaja)
    {
        this.tipoDeCaja = tipoDeCaja;
    }

    /**
     * @return the modelo
     */
    public int getModelo()
    {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(int modelo)
    {
        this.modelo = modelo;
    }

    /**
     * @return the numeroDePasajeros
     */
    public int getNumeroDePasajeros()
    {
        return numeroDePasajeros;
    }

    /**
     * @param numeroDePasajeros the numeroDePasajeros to set
     */
    public void setNumeroDePasajeros(int numeroDePasajeros)
    {
        this.numeroDePasajeros = numeroDePasajeros;
    }

    /**
     * @return the categoria
     */
    public int getCategoria()
    {
        return categoria;
    }
    
    /**
     * @return la garantia
     */
    public double getGarantia()
    {
        return garantia.getGarantia();
    }
}
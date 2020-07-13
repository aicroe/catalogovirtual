package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Filtro por tipo de caja.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class FiltroDisponible extends Filtro<Boolean>
{
    
    public FiltroDisponible(boolean filtroDisponible)
    {
        super(filtroDisponible, "Disponible");
        setFiltrador(new FiltradorDisponible());
    }
    
    private class FiltradorDisponible extends Filtrador
    {
        @Override
        public boolean estaDentroElFiltro(Auto auto, Boolean filtro)
        {
            return auto.estaDisponible() == filtro;
        }
    }
}

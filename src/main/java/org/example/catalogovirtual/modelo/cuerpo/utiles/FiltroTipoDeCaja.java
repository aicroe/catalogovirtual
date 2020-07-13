package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Filtro por tipo de caja.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class FiltroTipoDeCaja extends Filtro<Boolean>
{
    
    public FiltroTipoDeCaja(boolean filtroTipoDeCaja)
    {
        super(filtroTipoDeCaja, "Tipo de Caja");
        setFiltrador(new FiltradorTipoDeCaja());
    }
    
    private class FiltradorTipoDeCaja extends Filtrador
    {
        @Override
        public boolean estaDentroElFiltro(Auto auto, Boolean filtro)
        {
            return auto.getTipoDeCaja() == filtro;
        }
    }
}

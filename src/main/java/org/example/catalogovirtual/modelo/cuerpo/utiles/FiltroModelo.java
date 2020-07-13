package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Filtro de modelos.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class FiltroModelo extends Filtro<Integer>
{
    
    public FiltroModelo(int filtroModelo)
    {
        super(filtroModelo, "Modelo");
        setFiltrador(new FiltradorModelo());
    }
    
    private class FiltradorModelo extends Filtrador
    {
        @Override
        public boolean estaDentroElFiltro(Auto auto, Integer filtro)
        {
            return auto.getModelo() == filtro;
        }
    }
}

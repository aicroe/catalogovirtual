package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Filtro por placa.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class FiltroPlaca extends Filtro<String>
{
    
    public FiltroPlaca(String filtroPlaca)
    {
        super(filtroPlaca.trim().toLowerCase(), "Placa");
        setFiltrador(new FiltradorPlaca());
    }
    
    private class FiltradorPlaca extends Filtrador
    {
        @Override
        public boolean estaDentroElFiltro(Auto auto, String filtro)
        {
            return auto.getPlaca().toLowerCase().contains(filtro);
        }
    }
}

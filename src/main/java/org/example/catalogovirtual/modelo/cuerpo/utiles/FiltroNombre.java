package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Filtro por nombre.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class FiltroNombre extends Filtro<String>
{
    
    public FiltroNombre(String filtroNombre)
    {
        super(filtroNombre.trim().toLowerCase(), "Nombre");
        setFiltrador(new FiltradorNombre());
    }
    
    private class FiltradorNombre extends Filtrador
    {
        @Override
        public boolean estaDentroElFiltro(Auto auto, String filtro)
        {
            return auto.getNombre().toLowerCase().contains(filtro);
        }
    }
}

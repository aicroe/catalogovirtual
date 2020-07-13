package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Filtro de numero de pasajeros.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class FiltroNroPasajeros extends Filtro<Integer>
{
    
    public FiltroNroPasajeros(int filtroNroPasajeros)
    {
        super(filtroNroPasajeros, "Nro de Pasajeros");
        setFiltrador(new FiltradorNroPasajeros());
    }
    
    private class FiltradorNroPasajeros extends Filtrador
    {
        @Override
        public boolean estaDentroElFiltro(Auto auto, Integer filtro)
        {
            return auto.getNumeroDePasajeros() == filtro;
        }
    }
}

package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Filtro de modelos.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class FiltroPrecio extends Filtro<Double>
{
    public static final double RANGO = 0.20;
    
    public FiltroPrecio(double filtroPrecio)
    {
        super(filtroPrecio, "Precio");
        setFiltrador(new FiltradorPrecio());
    }
    
    private class FiltradorPrecio extends Filtrador
    {
        @Override
        public boolean estaDentroElFiltro(Auto auto, Double filtro)
        {
            double precio = auto.getPrecioPorDia();
            double limInf = filtro - (filtro * RANGO);
            double limSup = filtro + (filtro * RANGO);
            return limInf <= precio && precio <= limSup;
        }
    }
}

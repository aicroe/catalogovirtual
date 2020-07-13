package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Ordenador por modelo.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class OrdenadorPrecio extends Ordenador
{
    
    public OrdenadorPrecio()
    {
        super("Precio");
        setComparador(new ComparadorPrecio());
    }
    
    private class ComparadorPrecio extends Comparador
    {
        @Override
        public int comparar(Auto autoA, Auto autoB)
        {
            return (int)(autoA.getPrecioPorDia() - autoB.getPrecioPorDia());
        }
    }
}

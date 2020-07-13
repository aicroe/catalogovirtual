package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Ordenador por placa.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class OrdenadorPlaca extends Ordenador
{
    
    public OrdenadorPlaca()
    {
        super("Placa");
        setComparador(new ComparadorPlaca());
    }
    
    private class ComparadorPlaca extends Comparador
    {
        @Override
        public int comparar(Auto autoA, Auto autoB)
        {
            return autoA.compareTo(autoB);
        }
    }
}

package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Ordenador por nombre.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class OrdenadorNombre extends Ordenador
{
    
    public OrdenadorNombre()
    {
        super("Nombre");
        setComparador(new ComparadorNombre());
    }
    
    private class ComparadorNombre extends Comparador
    {
        @Override
        public int comparar(Auto autoA, Auto autoB)
        {
            return autoA.getNombre().compareTo(autoB.getNombre());
        }
    }
}

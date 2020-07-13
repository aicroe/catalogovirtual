package org.example.catalogovirtual.modelo.cuerpo.utiles;


import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Ordenador por modelo.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class OrdenadorModelo extends Ordenador
{
    
    public OrdenadorModelo()
    {
        super("Modelo");
        setComparador(new ComparadorModelo());
    }
    
    private class ComparadorModelo extends Comparador
    {
        @Override
        public int comparar(Auto autoA, Auto autoB)
        {
            return autoA.getModelo() - autoB.getModelo();
        }
    }
}

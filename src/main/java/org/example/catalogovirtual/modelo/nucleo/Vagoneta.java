package org.example.catalogovirtual.modelo.nucleo;
 
import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;


/**
 * Especializacion de Auto, representa una Vagoneta
 * 
 * @autor empujesoft
 * @version 2015.03.15
 */
public class Vagoneta extends Auto
{
    public Vagoneta()
    {
        super(Autos.NOMBRE_VAGONETA, Autos.VAGONETA, 
                Garantia.getGarantia(Autos.VAGONETA));
    }
}

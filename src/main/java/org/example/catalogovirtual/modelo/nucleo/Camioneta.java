package org.example.catalogovirtual.modelo.nucleo;
 
import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;


/**
 * Especializacion de Auto, representa una Camioneta
 * 
 * @autor empujesoft
 * @version 2015.03.15
 */
public class Camioneta extends Auto
{
    public Camioneta()
    {
        super(Autos.NOMBRE_CAMIONETA, Autos.CAMIONETA, 
                Garantia.getGarantia(Autos.CAMIONETA));
    }
}

package org.example.catalogovirtual.modelo.nucleo;
 
import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;


/**
 * Especializacion de Auto, representa una Limosina
 * 
 * @autor empujesoft
 * @version 2015.03.15
 */
public class Limosina extends Auto
{
    public Limosina()
    {
        super(Autos.NOMBRE_LIMOSINA, Autos.LIMOSINA,
                Garantia.getGarantia(Autos.LIMOSINA));
    }
}

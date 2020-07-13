package org.example.catalogovirtual.modelo.nucleo;
 
import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;


/**
 * Especializacion de Auto, representa un Automovil
 * 
 * @autor empujesoft
 * @version 2015.03.15
 */
public class Automovil extends Auto
{
    public Automovil()
    {
        super(Autos.NOMBRE_AUTOMOVIL, Autos.AUTOMOVIL,
                Garantia.getGarantia(Autos.AUTOMOVIL));
    }
}

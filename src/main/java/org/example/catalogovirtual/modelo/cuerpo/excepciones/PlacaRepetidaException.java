package org.example.catalogovirtual.modelo.cuerpo.excepciones;


/**
 * Excepcion lanzada cuando se intenta meter un auto con una placa existente en el catalogo
 * 
 * @author empujesoft
 * @version 2015.06.03
 */
public class PlacaRepetidaException extends ElementoRepetidoException
{
    
    public PlacaRepetidaException(String placa)
    {
        super("Ya existe un auto con esta placa " + "Placa: " + placa + ".");
    }
}

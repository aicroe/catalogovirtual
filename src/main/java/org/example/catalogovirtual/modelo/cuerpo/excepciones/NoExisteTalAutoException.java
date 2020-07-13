package org.example.catalogovirtual.modelo.cuerpo.excepciones;


/**
 * Excepcion lanzada cuando se busca un auto con ciertas especificaciones 
 * pero tal auto no existe en nuestro catalogo
 * 
 * @author empujesoft
 * @version 2015.05.20
 */
public class NoExisteTalAutoException extends Exception
{
    public NoExisteTalAutoException()
    {
        super("No existe un auto con esa especificacion!");
    }
}

package org.example.catalogovirtual.modelo.cuerpo.excepciones;


/**
 * Excepcion lanzada cuando se intenta insertar un elemento ya existente en el arbol de busqueda
 * 
 * @author empujesof
 * @version 2015.06.12
 */
public class ElementoRepetidoException extends Exception
{
    public ElementoRepetidoException(String mensaje)
    {
        super(mensaje);
    }
}

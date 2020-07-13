package org.example.catalogovirtual.modelo.cuerpo.excepciones;

/**
 * Excepcion Solicitud no Valida
 *
 * @author empujesoft
 * @version 2015.08.02
 */
public class SolicitudNoValidaException extends Exception
{
    public SolicitudNoValidaException(String mensaje)
    {
        super(mensaje);
    }
}

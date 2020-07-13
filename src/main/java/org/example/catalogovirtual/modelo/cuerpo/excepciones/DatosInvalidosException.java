/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.modelo.cuerpo.excepciones;

/**
 * Excepcion datos invalidos.
 *
 * @author empujesoft
 * @version 2015.08.06
 */
public class DatosInvalidosException extends Exception
{
    public DatosInvalidosException(String mensaje)
    {
        super(mensaje);
    }
}

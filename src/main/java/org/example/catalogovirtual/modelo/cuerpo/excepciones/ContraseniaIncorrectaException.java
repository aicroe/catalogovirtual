package org.example.catalogovirtual.modelo.cuerpo.excepciones;


/**
 * Excepcion lanzada cuando el login de un usuario es correcto pero la contrace�a no lo es
 * 
 * @author empujesoft
 * @version 2015.05.28
 */
public class ContraseniaIncorrectaException extends Exception
{
    public ContraseniaIncorrectaException(){
        super("Contraseña Incorrecta");
    }
}
package org.example.catalogovirtual.modelo.cuerpo.excepciones;


/**
 * 
 * @author empujesoft
 */
public class UsuarioNoEncontradoException extends Exception
{
    public UsuarioNoEncontradoException(){
        super("Usuario no Registrado");
    }
}
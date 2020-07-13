package org.example.catalogovirtual.modelo.cuerpo.excepciones;

/**
 * Excepción lanzada al intentar crear un usuario que tenga la misma ID que otro ya existente en el registro
 * 
 * @author Adrian Ferrufino 
 * @version 15042015
 */
public class IDYaExistente extends ElementoRepetidoException
{
    public IDYaExistente(String msg){
        super(msg);
    }
}
package org.example.catalogovirtual.modelo.nucleo;


/**
 * Clase auxiliar que modela al administrador de la plataforma. 
 * Su objetivo es diferenciarlo de los clientes en clases superiores para 
 * evitar que estos tengan acceso a operaciones que no les corresponde 
 * ejecutar en el Catálogo.
 * 
 * @author Francisco Camacho
 * @version 14052015
 */
public final class Administrador extends Usuario
{
    public static final String LOGIN_DEFECTO = "Administrador";
    public static final String PASSWORD_DEFECTO = "0123";
    
    private Administrador(String ID, String password)
    {
        super(ID, password);
    }
    
    public static Administrador crearInstanciaDefecto() {
        
        return new Administrador(LOGIN_DEFECTO, PASSWORD_DEFECTO);
    }
}
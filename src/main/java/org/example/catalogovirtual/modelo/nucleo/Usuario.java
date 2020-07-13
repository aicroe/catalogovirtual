package org.example.catalogovirtual.modelo.nucleo;


import org.example.catalogovirtual.modelo.cuerpo.utiles.Validador;
import java.io.Serializable;

/**
 * Clase que modela la información básica de todo usuario del modelo y las 
 * funciones que se pueden efectuar con dicha información.
 * 
 * @author Francisco Camacho
 * @version 14052015
 */
public abstract class Usuario implements Comparable<Usuario>, Serializable
{
    private String login;
    private String password;
    
    /**
     * Constructor de la clase Usuario.
     * 
     * @param login cadena de caracteres (letras o números) usada 
     *  como login del usuario
     * @param password cadena de caracteres usada como contraseña del usuario
     */
    public Usuario(String login, String password){
        this.login = login;
        this.password = password;
    }
    
    /**
     * Método de control que permite al usuario identifcarse y ser reconocido 
     * para poder realizar operaciones.
     * 
     * @param login
     * @param password
     * @return verdadero si la información coincide, falso en caso contrario
     */
    public boolean verificar(String login, String password ){
        return verificarLogin(login) && verificarPassword(password);
    }
    
    /**
     * Método de acceso privado, auxiliar al método verificar(...).
     * 
     * @param password contraseña del usuario
     * @return correcto - verdadero si la contraseña coincide con la del 
     *  usuario, falso en caso contrario
     */
    private boolean verificarPassword(String password){
        return this.password.equals(password);
    }
    
    /**
     * Método de acceso privado, auxiliar al método verificar(...).
     * 
     * @paramidID id del usuario
     * @return correcto - verdadero si la login coincide con la del usuario, 
  falso en caso contrario
     */
    private boolean verificarLogin(String login){
        return this.login.equals(login);
    }
    
    /**
     * Método que permite el cambio del login. 
     * El cambio se produce siempre y cuando  la nueva login sea validada 
 (la login sólo puede contener caracteres alfanuméricos, hay 
 distinción de mayúsculas y minúsculas).
     * 
     * @param newLogin cadena de caracteres que se desea utilizar como nuevo login
     * @return exito - verdadero si se realizó el cambio, falso en caso contrario
     */
    public boolean setLogin(String newLogin){
        boolean exito = false;
        if(Validador.validar(newLogin)){
            exito = true;
            login = newLogin;
        }
        return exito;
    }
    
    /**
     * Método que permite el cambio de la contraseña.
     * El cambio se produce siempre que la nueva contraseña sea validada 
     * (la contraseña sólo puede contener caracteres alfanuméricos, hay 
     * distinción de mayúsculas y minúsculas).
     * 
     * @param antPassword password anterior, por seguridad
     * @param newPassword cadena de caracteres que se desea utilizar 
     *  como nueva contraseña
     * @return exito verdadero si se realizó el cambio, falso en caso contrario
     */
    public boolean setPassword(String antPassword, String newPassword){
        boolean exito = false;
        if(verificar(login, antPassword))
            if(Validador.validar(newPassword)){
                exito = true;
                password = newPassword;
            }
        return exito;
    }
    
    /**
     * @return login actual de usuario
     */
    public String getLogin(){
        return login;
    }
    
    /**
     * @return password actual de usuario
     */
    protected String getPassword(){
        return password;
    }
    
    /**
     * Método que permite la comparación entre dos usuario. La comparación se 
     * realiza en base al login de cada usuario.
     * 
     * @param usuario
     * @return devuelve un entero menor a cero, cero, o mayor si el login del 
     *  este usuario es respectivamente menor, igual, o mayor
     */
    @Override
    public int compareTo(Usuario usuario){
        return getLogin().compareTo(usuario.getLogin());
    }
}
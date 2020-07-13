package org.example.catalogovirtual.modelo.cuerpo;

import org.example.catalogovirtual.modelo.cuerpo.excepciones.IDYaExistente;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.UsuarioNoEncontradoException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.ContraseniaIncorrectaException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.nucleo.Administrador;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.modelo.nucleo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase encargada del amacenamiento y admnistración de la información de los 
 * clientes y el administrador.
 * 
 * @author Francisco Camacho 
 * @version 19052015
 */
public class RegistroUsuarios implements Serializable
{
    private Administrador administrador;
    private ArbolBBC clientes;
    
    /**
     * Método constructor de la clase Registro
     */
    public RegistroUsuarios(){
        administrador = Administrador.crearInstanciaDefecto();
        clientes = new ArbolBBC();
    }
    
    /**
     * Método de acceso público que permite insertar un nuevo cliente al registro. 
     * La operación puede o no ser realizada dependiendo de si ya existe un usuario con
     * la misma ID que la del nuevo usuario a ingresar.
     * 
     * @param cliente nuevo usuario a ingresarse al registro
     * @throws IDYaExistente
     */
    public void insertarNuevoCliente(Cliente cliente) throws IDYaExistente{
        if(administrador.compareTo(cliente) == 0){
            throw new IDYaExistente("Id en uso: " + cliente.getLogin());
        }
        clientes.insertarSR(cliente);
    }
    
    /**
     * Método que permite eliminar un determinado usuario, si es que 
     * pertenece al registro.
     * 
     * @param cliente usuario que se desea eliminar del registro, si es que perteneciese a este.
     */
    public void eliminarCliente(Cliente cliente){
        clientes.eliminar(cliente);
    }
    
    /**
     * Método que permite verificar si el registro posee la información 
     * de un usuario dado si id.
     * 
     * @param login del usuario a buscar
     * @return verdadero si el usuario pertenece al registro, falso en caso contrario
     */
    public boolean estaUsuarioPorLogin(String login){
        if(administrador.getLogin().compareTo(login) == 0){
            return true;
        }
        return clientes.buscarCliente(login) != null;
    }
    
    /**
     * Método que genera un ArrayList con todos los clientes que se encuentren 
     * en el RegistroUsuarios.
     * 
     * @return ArrayList con los clientes del registro
     */
    public ArrayList<Cliente> listarClientes(){
        return clientes.elementosEnBFS();
    }
    
    /**
     * Acceder al usuario que tenga el este id y password.
     *
     * @param login id del usuario que se quiere acceder
     * @param password password del usuario al que se quiere acceder
     * @return devuelve el usuario que coincide con el id y el password
     * @throws ContraseniaIncorrectaException
     * @throws UsuarioNoEncontradoException
     */
    public Usuario accederUsuario(String login, String password) 
            throws ContraseniaIncorrectaException, UsuarioNoEncontradoException{
        Usuario usuario = null;
        if(administrador.verificar(login, password)){
            usuario = administrador;
        }else if(administrador.getLogin().compareTo(login) == 0){
            throw new ContraseniaIncorrectaException();
        }else if(clientes.buscarCliente(login) != null){
            Cliente cliente = clientes.buscarCliente(login);
            if(cliente.verificar(login, password)){
                usuario = cliente;
            }else{
                throw new ContraseniaIncorrectaException();
            }
        }else{
            throw new UsuarioNoEncontradoException();
        }
        return usuario;
    }
    
    /**
     * No se puede acceder a un usuario sin el password, asi que para que un
     * sea posible cambiar la contrasenia necesitamos un metodo especial para,
     * con ese objetivo, este metodo permite cambiar la contrasenia de un
     * usuario con su login y el telefono como requisito para verificar al 
     * usuario, Entonces es posible recuperar la contrasenia de un usuario, sin
     * tener el password.
     * 
     * @param login
     * @param telefono
     * @param nuevaContr
     * @throws UsuarioNoEncontradoException
     * @throws DatosInvalidosException 
     */
    public void recuperarContrCliente(String login, int telefono, String nuevaContr) 
            throws UsuarioNoEncontradoException, DatosInvalidosException{
        
        Cliente cliente = clientes.buscarCliente(login);
        if(cliente != null){
            cliente.recuperarContrPorTelf(nuevaContr, telefono);
        }else{
            throw new UsuarioNoEncontradoException();
        }
    }
}
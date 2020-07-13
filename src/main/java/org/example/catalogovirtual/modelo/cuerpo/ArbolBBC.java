package org.example.catalogovirtual.modelo.cuerpo;


import org.example.catalogovirtual.modelo.cuerpo.excepciones.IDYaExistente;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.ElementoRepetidoException;
import org.example.catalogovirtual.modelo.nucleo.Cliente;


/**
 * Modelo del Arbol Binario de Busqueda Sin Repeticion (ArbolBBSR). A diferencia de un ArbolBB, 
 * esta estructura no permite ingresar mas de una vez a los elementos
 * 
 * @author Francisco Camacho
 * @version 19042015
 */
public class ArbolBBC extends ArbolBBSR<Cliente>
{
    @Override
    public ArbolBBC getIzq()
    {
        return (ArbolBBC) izq;
    }
    
    @Override
    public ArbolBBC getDer()
    {
        return (ArbolBBC) der;
    }
    
    /**
     * Insertar un nuevo cliente en el arbol.
     * 
     * @param cliente
     * @throws IDYaExistente
     */
    @Override
    public void insertarSR(Cliente cliente) throws IDYaExistente
    {
        try{
            super.insertarSR(cliente);
        }catch(ElementoRepetidoException ex){
            throw new IDYaExistente("Id en uso: " + cliente.getLogin());
        }
    }
    
    @Override
    public ArbolBBC crearInstancia()
    {
        return new ArbolBBC();
    }
    
    /**
     * Busca un cliente por id en el arbol, si lo encuetra devuelve el cliente.
     * 
     * @param login parametro por el que se busca al cliente
     * @return el cliente encontrado, si no nulo
     */
    public Cliente buscarCliente(String login){
        Cliente clienteEncontrado = null;
        if(!(estaVacia()) && login != null){
            if(raiz.getLogin().compareTo(login) == 0) clienteEncontrado = raiz;
            else{if(raiz.getLogin().compareTo(login) < 0) clienteEncontrado = getDer().buscarCliente(login);
                 else clienteEncontrado = getIzq().buscarCliente(login);
                }
            }
        return clienteEncontrado;
    }
}
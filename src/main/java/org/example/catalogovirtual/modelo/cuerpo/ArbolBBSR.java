package org.example.catalogovirtual.modelo.cuerpo;

import org.example.catalogovirtual.modelo.cuerpo.excepciones.ElementoRepetidoException;


/**
 * Arbol binario de busqueda que no permite repeticiones.
 * 
 * @author empujesoft
 * @version 2015.07.18
 */
public class ArbolBBSR<T extends Comparable> extends ArbolBB<T>
{
    
    @Override
    public ArbolBBSR<T> getDer()
    {
        return (ArbolBBSR) der;
    }
    
    @Override
    public ArbolBBSR<T> getIzq()
    {
        return (ArbolBBSR) izq;
    }
    
    @Override
    public ArbolBBSR<T> crearInstancia()
    {
        return new ArbolBBSR<>();
    }
    
    /**
     * Use el Metodo insertarSR en su lugar.
     * Rechaza el metodo insertar de la super clase por que esta permite insertar elementos, 
     * comparablemente iguales lo cual no podemos permitir ya que el objetivo es no poder insertar 
     * elementos repetidos en arbol.
     * 
     * @param dato
     * @throws UnsupportedOperationException lanza la excepcion si el metodo es llamado
     */
    @Override
    @Deprecated
    public void insertar(T dato)
    {
        throw new UnsupportedOperationException("Herencia Rechazada");
    }
    
    /**
     * Insertar Sin Repeticion un obejto T comparable en el arbol binario, se pocicionan respecto a su 
     * comparacion. Los menores a la raiz a la izquierda, los mayores a la derecha. Si el arbol esta vacio 
     * el elemento es puesto en la raiz del arbol. Si el dato es nulo simplemente no es insertado, y 
     * el metodo termina. Si el elemento a insertar ya esta en el arbol se lanza la excepcion elemento 
     * repetido.
     * 
     * @param dato, objetp tipo T que se insertara al arbol
     * @throws ElementoRepetidoException 
     */
    public void insertarSR(T dato) throws ElementoRepetidoException
    {
        if(dato == null)return;
        if(estaVacia()){
            raiz = dato;
            izq = crearInstancia();
            der = crearInstancia();
        }else{
            int comparacion = dato.compareTo(raiz);
            if(comparacion > 0){
                getDer().insertarSR(dato);
            }else if(comparacion < 0){
                getIzq().insertarSR(dato);
            }else if(comparacion == 0){
                throw new ElementoRepetidoException(dato.toString());
            }
        }
    }
}

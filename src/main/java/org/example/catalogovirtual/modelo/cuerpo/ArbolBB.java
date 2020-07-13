package org.example.catalogovirtual.modelo.cuerpo;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que modela el comportamiento y los elementos del Arbol binario de busqueda pero que no acepta
 * elementos repetidos en el arbol y en ese caso se lanza una excepcion.
 * 
 * @autor empujesoft
 * @version 2015.05.05
 * @param <T> T debe ser Comparable por que es una arbol binario de busquda
 */

public class ArbolBB<T extends Comparable> implements Serializable
{
    protected T raiz;
    protected ArbolBB<T> izq;
    protected ArbolBB<T> der;
    
    /**
     * La raiz, el subarbol izquierdo y derecho son iniciados en nulo.
     * El arbol se crea vacio.
     */
    public ArbolBB()
    {
        raiz = null;
        izq = null;
        der = null;
    }
    
    /**
     * Devuelve la raiz de este arbol
     * 
     * @return raiz T objeto tipo T
     */
    public T getRaiz()
    {
        return raiz;
    }
    
    /**
     * Devuelve el arbol derecho
     * 
     * @return der ArbolBB arbol derecho
     */
    public ArbolBB<T> getDer()
    {
        return der;
    }
    
    /**
     * Devuelve el arbol izquierdo
     * 
     * @return izq ArbolBB arbol izq
     */
    public ArbolBB<T> getIzq()
    {
        return izq;
    }
    
    /**
     * Ve si el arbol esta vacio.
     * 
     * @return devuelve true si el arbol esta vacio
     */
    public boolean estaVacia()
    {
        return raiz == null && der == null && izq == null;
    }
    
    /**
     * Recorre todos los elementos de este arbol en inorden.
     * 
     * recorre el arbol en in orden y los imprime cunado los visita
     */
    public void recorrerEnInOrden()
    {
        if(!estaVacia()){
            izq.recorrerEnInOrden();
            System.out.println(raiz);
            der.recorrerEnInOrden();
        }
    }
    
    /**
     * Cuenta los elementos almacenados en este arbol.
     * 
     * @return cantidad de elementos que este arbol almacena
     */
    public int contarElementosAlmacenados()
    {
        if(estaVacia()){
            return 0;
        }else{
            return 1 + izq.contarElementosAlmacenados() + der.contarElementosAlmacenados();
        }
    }
    
    /**
     * Ve si el elemento dado esta en el arbol.
     * 
     * @param dato elemento que se buscara en el arbol
     * @return devuelve true si el elemento buscado esta en el arbol
     */
    public boolean buscar(T dato)
    {
        if(dato == null)return false;
        if(!estaVacia()){
            int comparacion = dato.compareTo(raiz);
            if(comparacion == 0){
                return true;
            }else if(comparacion < 0){
                return izq.buscar(dato);
            }else if(comparacion > 0){
                return der.buscar(dato);
            }
        }
        return false;
    }
    
    /**
     * Insertar el dato en el arbol. Inserta el dato comparable en el arbol binario
     * de busqueda si el arbol esta vacio se insertar en la raiz, si no lo esta 
     * entonces se compara esta dato con la raiz, si es menor se inserta a la izquierda,
     * si es mayor se inserta a la derecha, no contiene elementos nulos si se intenta 
     * insertar uno.
     * 
     * @param dato el dato que se quiere insertar en el arbol
     */
    public void insertar(T dato)
    {
        if(dato == null)return;
        if(estaVacia()){
            raiz = dato;
            izq = crearInstancia();
            der = crearInstancia();
        }else{
            int comparacion = dato.compareTo(raiz);
            if(comparacion >= 0){
                der.insertar(dato);
            }else if(comparacion < 0){
                izq.insertar(dato);
            }
        }
    }
    
    /**
     * Crea una instancia de ArbolBB con el mismo tipo de datos que este arbol.
     * Especial interes para una sub clase que quiera heredar de ArbolBB debera
     * sobreescribir este metodo. Este metodo se llama cuando se inserta un nuevo dato,
     * puede ocacionar errores de casting si no se sobreescribe este metodo.
     * 
     * @return un nuevo arbol con el mismo tipo que este arbol
     */
    public ArbolBB<T> crearInstancia()
    {
        return new ArbolBB<>();
    }
    
    /**
     * Devuelve el mayor elemento almacenado en este arbol.
     * 
     * @return la mayor raiz de este arbol
     */
    public T obtenerLaMayorRaiz()
    {
        if(!estaVacia()){
            if(der.estaVacia()){
                return raiz;
            }else{
                return der.obtenerLaMayorRaiz();
            }
        }
        return null;
    }
    
    /**
     * Elimina el elemento deseado de este arbol, si es que esta.
     * 
     * @param elemento elimina el objeto tipo T dado si esta en el arbol
     * @return devuleve true si se elimino con exito
     */
    public boolean eliminar(T elemento)
    {
        if(elemento == null)return false;
        if(!estaVacia()){
            if(elemento.compareTo(raiz) == 0){
                if(izq.estaVacia() && der.estaVacia()){
                    raiz = null;
                    izq = null;
                    der = null;
                }else if(der.estaVacia()){
                    ArbolBB<T> tmp = izq;
                    raiz = tmp.raiz;
                    der = tmp.der;
                    izq = tmp.izq;
                    izq.eliminar(elemento);
                }else if(izq.estaVacia()){
                    ArbolBB<T> tmp = der;
                    raiz = tmp.raiz;
                    der = tmp.der;
                    izq = tmp.izq;
                    der.eliminar(elemento);
                }else{
                    raiz = izq.sacarLaMayorRaiz();
                }
                return true;
            }else if(elemento.compareTo(raiz) > 0){
                return der.eliminar(elemento);
            }else{
                return izq.eliminar(elemento);
            }
        }
        return false;
    }
    
    /**
     * Saca la mayor raiz del arbol, metodo usado solo para la eliminacion cuando la raiz a eliminar
     * tiene dos hijos.
     * 
     * @return objeto T, saca la mayor raiz de este arbol. Le sirve al metodo eliminar()
     */
    private T sacarLaMayorRaiz()
    {
        T mayorRaiz = null;
        if(!estaVacia()){
            if(der.estaVacia()){
                mayorRaiz = raiz;
                eliminar(raiz);
            }else{
                mayorRaiz = der.sacarLaMayorRaiz();
            }
        }
        return mayorRaiz;
    }
    
    /**
     * Devuelve en un ArrayList los elelemtos del arbol en BFS, o los elementos del arbol
     * recorridos en amplitud. Visitando primero la raiz y luego sus ramas y asi recursivamente.
     * 
     * @return una lista con los elementos de este arbol en BFS
     */
    public ArrayList<T> elementosEnBFS()
    {
        ArrayList<T> lista = new ArrayList<>();
        if(!estaVacia()){
            Cola<ArbolBB> cola = new Cola<>();
            cola.insert(this);
            while(!cola.estaVacia()){
                ArbolBB<T> actual = cola.remove();
                if(!actual.estaVacia()){
                    lista.add(actual.getRaiz());
                    cola.insert(actual.getIzq());
                    cola.insert(actual.getDer());
                }
            }
        }
        return lista;
    }
    
    /**
     * Devuelve un ArrayList con todos los elementod del arbol en inorden
     * 
     * @return lista de los elementos de este arbol
     */
    public ArrayList<T> elementosEnInOrden()
    {
        ArrayList<T> arreglo = new ArrayList<>();
        insertarEnArrayElmtos(arreglo);
        return arreglo;
    }
    
    /**
     * Inserta todos los elementos del arbol en inorden dentro el arreglo pasado.
     * 
     * @param arreglo lista donde los elementos del arbol seran insertados.
     */
    private void insertarEnArrayElmtos(ArrayList<T> arreglo)
    {
        if(!estaVacia()){
            izq.insertarEnArrayElmtos(arreglo);
            arreglo.add(raiz);
            der.insertarEnArrayElmtos(arreglo);
        }
    }
    
    @Override
    public String toString()
    {
        if(!estaVacia()){
            return super.toString();
        }else{
            return "vacio";
        }
    }
    
    /**
     * Una cola privada, el arbol la usa cuando se invoca el metodo elementos en BFS
     * pues para la implementacion del algoritmo se necesita la ayuda de una cola.
     */
    private class Cola<T>
    {
        private Nodo<T> primero;
        private Nodo<T> ultimo;
        
        public Cola()
        {
            primero = null; ultimo = null;
        }
        
        public boolean estaVacia()
        {
            return primero == null;
        }
        
        public void insert(T dato)
        {
            Nodo<T> nuevo = new Nodo<>(dato);
            if(estaVacia()){
                primero = nuevo;
                ultimo = nuevo;
            }else{
                ultimo.sig = nuevo;
                ultimo = nuevo;
            }
        }
        
        public T remove()
        {
            if(!estaVacia()){
                Nodo<T> buf = primero;
                primero = primero.sig;
                if(primero == null){
                    ultimo = null;
                }
                return buf.dato;
            }
            return null;
        }
        
        class Nodo<T>
        {
            T dato;
            Nodo<T> sig;
            
            Nodo(T dato)
            {
                this.dato = dato;
                sig = null;
            }
        }
    }
}
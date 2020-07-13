package org.example.catalogovirtual.modelo.cuerpo;

import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Filtro;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Ordenador;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.NoExisteTalAutoException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.CatalogoIncoherenteException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.PlacaRepetidaException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.CategoriaNoExisteException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Catalogo de autos, unico catalogo para todos los usuarios
 * 
 * @autor empujesoft
 * @verison 2015.05.20
 */
public class Catalogo implements Serializable
{
    private ArbolBBA[] categorias;
    private ArrayList<Auto> ultimaLista;
    
    /**
     * Crea un catalogo vacio.
     */
    public Catalogo()
    {
        categorias = new ArbolBBA[Autos.NUMERO_DE_CATEGORIAS];
        categorias[Autos.AUTOMOVIL] = new ArbolBBA();
        categorias[Autos.CAMIONETA] = new ArbolBBA();
        categorias[Autos.VAGONETA] = new ArbolBBA();
        categorias[Autos.LIMOSINA] = new ArbolBBA();
        ultimaLista = new ArrayList<>();
    }
    
    /**
     * Revisa en todo el catalogo si hay autos con placa repetidas.
     * 
     * @throws CatalogoIncoherenteException excepcion en tiempo de ejecucion. Detiene todo el programa si hay
     *                                      incoherencias en el catalogo. No puede pasar si los archivos 
     *                                      en la carpeta 'bin/' no fueron modificados.
     */
    public void verificarCargaDeAutos()
    {
        verificarCargaDeAutos(0);
    }
    
    /**
     * Revisa si hay algun auto con placa repetida en todo el catalogo, primero un indice actual
     * se presupone que en un mismo arbol no hay repeticiones. Entonces se busca en sus aleda�os pero no
     * se busca otra vez entre dos arboles que ya fueron procesados una vez.
     * 
     * @param actual indice del arbol por el que empieza a buscar repeticiones, para buscar en todo el 
     *               catalogo debe ser 0.
     * @throws CatalogoIncoherenteException excepcion en tiempo de ejecucion. Detiene todo el programa si hay
     *                                      incoherencias en el catalogo. No puede pasar si los archivos 
     *                                      en la carpeta 'bin/' no fueron modificados.
     */
    private void verificarCargaDeAutos(int actual)
    {
        if(actual == Autos.NUMERO_DE_CATEGORIAS){
            return;
        }else{
            ArrayList<Auto> contenidoActual = listarCategoria(actual);
            int i = actual + 1;
            while(i < Autos.NUMERO_DE_CATEGORIAS){
                int j = 0;
                while(j < contenidoActual.size()){
                    boolean hayRepetido;
                    Auto auto = contenidoActual.get(j);
                    hayRepetido = categorias[i].estaAutoPorPlaca(auto.getPlaca());
                    if(hayRepetido){
                        throw new CatalogoIncoherenteException(actual, auto.getPlaca());
                    }
                    j++;
                }
                i++;
            }
            verificarCargaDeAutos(actual + 1);
        }
    }
    
    /**
     * Devuelve la ultima accion realizada, referente a filtro, ordenamiento o vista.
     * 
     * @return ultimaLista la ultima accion realiza en el catalogo, o ultima busqueda
     */
    public ArrayList<Auto> getUltimaLista()
    {
        return ultimaLista;
    }
    
    /**
     * Devuelve una lista de todos los autos almacenados en este catalogo.
     * 
     * @return todos los autos del catalogo
     */
    public ArrayList<Auto> listarCatalogo()
    {
        ArrayList<Auto> autos = new ArrayList<>();
        autos.addAll(listarCategoria(Autos.AUTOMOVIL));
        autos.addAll(listarCategoria(Autos.VAGONETA));
        autos.addAll(listarCategoria(Autos.CAMIONETA));
        autos.addAll(listarCategoria(Autos.LIMOSINA));
        ultimaLista = autos;
        return autos;
    }
    
    /**
     * Devuelve una lista con los autos de la categoria deseada.
     * 
     * @param categoria de autos de la lista que queremos
     * @return lista de autos del catalogo que estan en tal categoria
     * @throws CategoriaNoExisteException
     */
    public ArrayList<Auto> listarCategoria(int categoria)
    {
        chequearCategoriaEnRango(categoria);
        ArrayList<Auto> autos = categorias[categoria].elementosEnBFS();
        ultimaLista = autos;
        return autos;
    }
    
    /**
     * Inserta un nuevo auto en el catalogo, pero primero revisa si su placa esta repetida. Si
     * no lo esta se insertar sin problemas en su categoria correspondiente.
     * 
     * @param auto una instancia de una de las clases concretas que heredan de Auto
     * @throws PlacaRepetidaException
     * @throws DatosInvalidosException
     * @throws NullPointerException
     */
    public void insertarNuevoAuto(Auto auto) throws PlacaRepetidaException, DatosInvalidosException
    {
        if(auto == null) throw new NullPointerException("auto");
        int categoria = auto.getCategoria();
        chequearPlacaAutoRepetida(auto.getPlaca(), categoria);
        Autos.verificarAutoParaInsertar(auto);
        categorias[categoria].insertarSR(auto);
    }
    
    /**
     * Elimina un auto del catalogo, si solo esta en el catalogo. Elimina un auto por su placa.
     * 
     * @param placa la placa del auto que se quiere eliminar
     * @return true si el auto fue encontrado y eliminado
     */
    public boolean eliminarAutoPorPlaca(String placa)
    {
        boolean eliminado = false;
        int categoria = 0;
        while(!eliminado && categoria < Autos.NUMERO_DE_CATEGORIAS){
            eliminado = categorias[categoria].eliminarPorPlaca(placa);
            categoria++;
        }
        if(eliminado && ultimaLista != null){
            boolean estabaEnUltimaLista = false;
            int i = 0;
            while(!estabaEnUltimaLista && i < ultimaLista.size()){
                Auto actual = ultimaLista.get(i);
                if(actual.getPlaca().equals(placa)){
                    ultimaLista.remove(i);
                    estabaEnUltimaLista = true;
                }else{
                    i++;
                }
            }
        }
        return eliminado;
    }
    
    /**
     * Revisa en todos los arboles del catalogo menos en uno, si hay un 
     * auto con la placa dada. Este metodo se debe llamar antes de insertar 
     * un auto en la categoria indicada, entonces el arbol en el que evita 
     * buscar un auto con esa placa es el arbol en el que se insertara el auto.
     * Si se encuentra que hay un auto con esa placa se lanza la excepcion 
     * de placa repetida.
     * 
     * @param placa la placa del auto a buscar
     * @param categoriaNoRevisar el indice de la categoria que no se revisara
     * @throws PlacaRepetidaException
     */
    private void chequearPlacaAutoRepetida(String placa, int categoriaNoRevisar)
            throws PlacaRepetidaException
    {
        boolean esPlacaRepetida = false;
        int i = 0;
        while(!esPlacaRepetida && i < Autos.NUMERO_DE_CATEGORIAS){
            if(i != categoriaNoRevisar){
                esPlacaRepetida = categorias[i].estaAutoPorPlaca(placa);
            }
            i++;
        }
        if(esPlacaRepetida){
            throw new PlacaRepetidaException(placa);
        }
    }
    
    /**
     * Verifica si la categoria esta en el rango esta en rango. Si no lo 
     * esta lanza una RuntimeException.
     * 
     * @param categoria la categoria que se va a revisar
     * @throws CategoriaNoExisteException
     */
    private void chequearCategoriaEnRango(int categoria) throws CategoriaNoExisteException
    {
        if(categoria < 0 || categoria >= Autos.NUMERO_DE_CATEGORIAS){ 
            throw new CategoriaNoExisteException();
        }
    }
    
    /**
     * Filtra una lista del catalogo con el filtro especificado y devuelve 
     * una lista con los autos filtrados. Si la categoria es -1 se filtra todo
     * el catalogo.
     * 
     * @param filtro el filtro que se usara para filtrar una lista del catalogo
     * @param categoria la categoria en la cual se aplicara el filtro
     * @return una lista con los autos filtrados, devuelve una lista vacia si 
     *  no hubo ningun emparejemiento
     * @throws CategoriaNoExisteException
     */
    public ArrayList<Auto> filtrarListaDeCatalogo(Filtro filtro, int categoria)
    {
        ArrayList<Auto> autos;
        if(categoria == Autos.TODAS_LAS_CATEGORIAS){
            autos = listarCatalogo();
        }else{
            chequearCategoriaEnRango(categoria);
            autos = listarCategoria(categoria);
        }
        return ultimaLista = filtro.filtrar(autos);
    }
    
    /**
     * Filtra una lista del catalogo con los filtros especificados, es un 
     * metodo de filtrado mejorado pero mas pesado, devuelve una lista con 
     * los autos filtrados. Si la categoria es -1 se filtra todo el catalogo.
     * 
     * @param filtros los filtros que se usaran para filtrar una lista del 
     *  catalogo
     * @param categoria la categoria en la cual se aplicara el filtro
     * @return una lista con los autos filtrados, devuelve una lista vacia 
     *  si no hubo ningun emparejemiento
     * @throws CategoriaNoExisteException
     */
    public ArrayList<Auto> filtrarListaDeCatalogo(ArrayList<Filtro> filtros, int categoria)
    {
        ArrayList<Auto> autos;
        if(categoria == Autos.TODAS_LAS_CATEGORIAS){
            autos = listarCatalogo();
        }else{
            chequearCategoriaEnRango(categoria);
            autos = listarCategoria(categoria);
        }
        for(Filtro filtro: filtros){
            autos = filtro.filtrar(autos);
        }
        return ultimaLista = autos;
    }
    
    /**
     * Ordena la ultima accion realizada. Si la ultima accion es nula 
     * devuelve nulo. Si no ordena la lista segun el objeto ordenador 
     * que es pasado.
     * 
     * @param ordenador el ordenador que se usara para ordenar la ultima accion
     * @return una lista con los elementos ordenados. vacia si la ultima accion 
     *  esta vacia pero no nula.
     */
    public ArrayList<Auto> ordenarUltimaLista(Ordenador ordenador)
    {
        if(ultimaLista == null) return null;
        return ultimaLista = ordenador.ordenar(ultimaLista);
    }
    
    /**
     * Mira si esta el auto en el catalogo.
     * 
     * @param placa la placa del auto que se quiere saber si esta o no el catalogo
     * @return devuelve true si esta en el catalogo
     */
    public boolean estaAutoPorPlaca(String placa)
    {
        try{
            return seleccionarAuto(placa) != null;
        }catch(NoExisteTalAutoException ex){
            return false;
        }
    }
    
    /**
     * Se busca al auto en todo el catalogo.
     * 
     * @param placa la placa del auto a seleccionar
     * @return seleccionado el auto seleccionado
     * @throws NoExisteTalAutoException
     */
    public Auto seleccionarAuto(String placa) throws NoExisteTalAutoException
    {
        Auto seleccionado = null;
        int categoria = 0;
        while(seleccionado == null && categoria < Autos.NUMERO_DE_CATEGORIAS){
            seleccionado = categorias[categoria].getAutoPorPlaca(placa);
            categoria++;
        }
        if(seleccionado == null){
            throw new NoExisteTalAutoException();
        }else{
            return seleccionado;
        }
    }
    
}

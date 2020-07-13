package org.example.catalogovirtual.modelo.cuerpo.utiles;


import java.util.ArrayList;
import org.example.catalogovirtual.modelo.nucleo.Auto;

/**
 * Ordenar los autos segun una caracteristica.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public abstract class Ordenador
{
    private String nombre;
    private Comparador comparador;
    
    /**
     * Se crea un ordenador de autos, con el nombre especificado.
     * 
     * @param nombre el nombre del ordenador
     */
    public Ordenador(String nombre)
    {
        this.nombre = nombre;
        comparador = null;
    }
    
    @Override
    public String toString()
    {
        return nombre;
    }
    
    /**
     * Metodo que deben usar las subclases para actualizar el comparador, que
     * es necesario para ordenar. Si no es actualizado nada funciona.
     * 
     * @param comparador es el comparador necesario para ordenar una lista
     */
    protected void setComparador(Comparador comparador)
    {
        this.comparador = comparador;
    }
    
    /**
     * El metodo principal que ordena una lista de autos. El algoritmo es sencillo, busca el menor de la 
     * lista mientras no haya sido marcado, lo pone al principio de la lista resultado y lo marca, y asi hasta
     * que todos estan marcados.
     * 
     * @param autos la lista de autos que se quiere ordenar
     * @return una lista con los autos ordenados
     */
    public ArrayList<Auto> ordenar(ArrayList<Auto> autos)
    {
        if(comparador == null) throw new UnsupportedOperationException();
        return ordenar(autos, comparador);
    }
    
    /**
     * Metodo interno que ordena una lista segun un comparador.
     * 
     * @param autos la lista de autos que se quiere ordenar
     * @param comparador el que compara los autos segun algun atributo especifico
     * @return la lista de los autos ordenados
     */
    private ArrayList<Auto> ordenar(ArrayList<Auto> autos, Comparador comparador)
    {
        ArrayList<Auto> ordenados = new ArrayList<>(autos.size());
        boolean[] marcados = new boolean[autos.size()];
        for(int i = 0; i< autos.size(); i++){
            Auto menorNoMarcado = obtenerElMenorYMarcar(autos, marcados, comparador);
            ordenados.add(menorNoMarcado);
        }
        return ordenados;
    }
    
    /**
     * Metodo interno que busca el menor de la lista de autos mientras no este marcado, y
     * lo devuelve pero primero lo marca.
     * 
     * @param autos los autos que se quieren ordenar
     * @param marcados registro de los autos que ya fueron marcados
     * @param comparador el que compara los autos segun una caracteristica especifica
     * @return devuelve el menor de auto de la lista no marcado, si todos estan marcados 
     *          devuelve nulo
     */
    private Auto obtenerElMenorYMarcar(ArrayList<Auto> autos, boolean[] marcados, Comparador comparador)
    {
        int indiceDelMenor = -1;
        for(int i = 0; i< autos.size(); i++){
            if(!marcados[i]){
                Auto actual = autos.get(i);
                Auto menorActual = (indiceDelMenor >= 0)? autos.get(indiceDelMenor): actual;
                int comparacion = 0;
                if(comparador.comparar(actual, menorActual) <= 0){
                    indiceDelMenor = i;
                }
            }
        }
        if(indiceDelMenor >= 0){
            marcados[indiceDelMenor] = true;
            return autos.get(indiceDelMenor);
        }
        return null;
    }
    
    /**
     * Clase que se encarga de comparar dos autos dados.
     */
    protected abstract class Comparador
    {
        /**
         * Compara el auto A respecto al auto B. Devuelve 0 si son iguales, menor a 0 si el auto A
         * es menor a B y mayor a 0 si el auto A es mayor a B.
         * 
         * @param autoA el auto que se comparara respecto a el autoB
         * @param autoB el auto con el que se compara el autoA
         * @return 0 si son iguales, menor a 0 si A es menor a B y mayor a 0 si A es mayor
         */
        public abstract int comparar(Auto autoA, Auto autoB);
    }
}

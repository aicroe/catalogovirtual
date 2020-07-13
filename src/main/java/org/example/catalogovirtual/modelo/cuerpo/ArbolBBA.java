package org.example.catalogovirtual.modelo.cuerpo;


import org.example.catalogovirtual.modelo.cuerpo.excepciones.PlacaRepetidaException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.ElementoRepetidoException;
import org.example.catalogovirtual.modelo.nucleo.Auto;


/**
 * Arbol binario de busqueda para autos. Especializacion de ArbolBB para almacenar los 
 * autos del catalogo.
 * 
 * @author empujesoft
 * @version 2015.06.12
 */
public class ArbolBBA extends ArbolBBSR<Auto>
{
    @Override
    public ArbolBBA getIzq()
    {
        return (ArbolBBA) izq;
    }
    
    @Override
    public ArbolBBA getDer()
    {
        return (ArbolBBA) der;
    }
    
    /**
     * Inserta un auto en el arbol. Los autos se comparan y son almacenados dependiendo, 
     * si son mayores que la raiz a la derecha, si son menores la izquierda. Si son 
     * iguales se lanza la excepcion placa repetida. Si auto es nulo el no se inserta y el 
     * metodo termina. Si ya existe un auto con esa placa se lanza la excepcion placa repetida.
     * 
     * @param auto el auto a insertar en el arbol
     * @throws PlacaRepetidaException
     */
    @Override
    public void insertarSR(Auto auto) throws PlacaRepetidaException
    {
        try{
            super.insertarSR(auto);
        }catch(ElementoRepetidoException ex){
            throw new PlacaRepetidaException(auto.getPlaca());
        }
    }
    
    @Override
    public ArbolBBA crearInstancia()
    {
        return new ArbolBBA();
    }
    
    /**
     * Deprecado eliminar(...) metodo que elimina a un auto sin tener en cuenta 
     * algunas consideraciones de seguridad. Use en su lugar eliminarPorPlaca(...) o
     * eliminarForzadamente(...);
     * @param auto
     * @return 
     */
    @Override
    @Deprecated
    public boolean eliminar(Auto auto)
    {
        return super.eliminar(auto);
    }
    
    /**
     * Elimina un auto de este arbol dado la placa de tal auto. Si el auto no esta en el 
     * arbol, no se hace nada. Si la placa es nula el metodo termina. Se usa el metodo 
     * eliminar(...) de la superclase cuando se encuentra al auto buscado se lo elimina.
     * 
     * @param placa la placa del auto que se va a eliminar
     * @return devuelve true si se elimino el auto con exito si es que estaba en el arbol, false
     *          si no estaba en cuyo caso no se elimino, o el auto no estaba disponible y por 
     *          seguridad no se elimino, si quiere eliminar de todas formas el auto, use el metodo
     *          eliminarForzadamente(...) para hacerlo si tener en cuenta si esta o no disponible
     */
    public boolean eliminarPorPlaca(String placa)
    {
        if(placa == null) return false;
        if(!estaVacia()){
            Auto actual = getRaiz();
            if(placa.compareTo(actual.getPlaca()) == 0){
                if(actual.estaDisponible()){
                    return super.eliminar(actual);
                }else{
                    return false;
                }
            }else if(placa.compareTo(actual.getPlaca()) < 0){
                return getIzq().eliminarPorPlaca(placa);
            }else{
                return getDer().eliminarPorPlaca(placa);
            }
        }
        return false;
    }
    
    /**
     * Usa el mismo algoritmo de eliminacion que eliminarPorPlaca(...) solo que en lugar de
     * ver si el auto esta disponible o no este metodo lo elimina de todos modos si tener en cuenta
     * este inconveniente. Puede ocacionar errores en el funcionamiento del catalogo si se 
     * elimina un auto que ha sido alquilado. Solo usar en casos extremadamente necesarios.
     * 
     * @param placa la placa del auto a eliminar
     * @return devuelve true si elimino al auto
     */
    public boolean eliminarForzadamente(String placa)
    {
        if(placa == null) return false;
        if(!estaVacia()){
            Auto actual = getRaiz();
            if(placa.compareTo(actual.getPlaca()) == 0){
                return super.eliminar(actual);
            }else if(placa.compareTo(actual.getPlaca()) < 0){
                return getIzq().eliminarPorPlaca(placa);
            }else{
                return getDer().eliminarPorPlaca(placa);
            }
        }
        return false;
    }
    
    /**
     * Devuelve el auto que coincida con la placa se�alada, si es que esta en este arbol.
     * 
     * @param placa del auto a buscar
     * @return el auto encontrado, nulo si no lo encuentra
     */
    public Auto getAutoPorPlaca(String placa)
    {
        if(!estaVacia() && placa != null){
            Auto actual = getRaiz();
            if(placa.compareTo(actual.getPlaca()) == 0){
                return actual;
            }else if(placa.compareTo(actual.getPlaca()) > 0){
                return getDer().getAutoPorPlaca(placa);
            }else{
                return getIzq().getAutoPorPlaca(placa);
            }
        }
        return null;
    }
    
    /**
     * Ve si el hay algun auto con esa placa en este arbol.
     * 
     * @param placa placa del auto a buscar
     * @return devuelve true si el auto esta en el arbol
     */
    public boolean estaAutoPorPlaca(String placa)
    {
        return getAutoPorPlaca(placa) != null;
    }
}

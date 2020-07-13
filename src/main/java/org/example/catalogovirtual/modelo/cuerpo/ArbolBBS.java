package org.example.catalogovirtual.modelo.cuerpo;

import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import java.util.Date;

/**
 * Arbol binario de Solicitudes.
 * 
 * @author empujesoft
 * @version 2015.08.02
 */
public class ArbolBBS extends ArbolBB<Solicitud>
{
    @Override
    public ArbolBBS getIzq()
    {
        return (ArbolBBS) izq;
    }
    @Override
    public ArbolBBS getDer()
    {
        return (ArbolBBS) der;
    }
    
    @Override
    public ArbolBBS crearInstancia()
    {
        return new ArbolBBS();
    }
    
    /**
     * Ve la solicitud especificada esta en el arbol.
     * 
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @return devuelve true si esta en el arbol
     */
    public boolean estaSolicitid(Date fechaInicial, Date fechaFinal, Auto auto)
    {
        return accederSolicitud(fechaInicial, fechaFinal, auto) != null;
    }
    
    /**
     * Retira una solicitud del arbol, quiere decir que busca la solcitud por
     * los parametros indicados, y si encuetra la solicitud la sacamos
     * del arbol y la devolvemos, de otro modo retorna nulo y no se hace
     * nada en el arbol.
     * 
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @return La solicitud especificada o nulo si no la encuentra
     */
    public Solicitud retirar(Date fechaInicial, Date fechaFinal, Auto auto){
        
        if(!estaVacia()){
            if(raiz.esIgual(fechaInicial, fechaFinal, auto)){
                Solicitud solicitud = raiz;
                if(super.eliminar(solicitud)){
                    return solicitud;
                }else{
                    throw new UnsupportedOperationException();
                }
            }else if(raiz.getFechaFinal().compareTo(fechaFinal) < 0){
                return getDer().retirar(fechaInicial, fechaFinal, auto);
            }else{
                return getIzq().retirar(fechaInicial, fechaFinal, auto);
            }
        }
        return null;
    }
    
    /**
     * Dado la fecha inicial, final y el auto del contrato, se busca una solicitud
     * si se la encuetra la devolvemos pero no la eliminamos.
     * 
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @return La solicitud especificada, sino nulo
     */
    public Solicitud accederSolicitud(Date fechaInicial, Date fechaFinal, 
            Auto auto){
        if(!estaVacia()){
            if(getRaiz().esIgual(fechaInicial, fechaFinal, auto)){
                return getRaiz();
            }else if(getRaiz().getFechaFinal().compareTo(fechaFinal) < 0){
                return getDer().accederSolicitud(fechaInicial, fechaFinal, auto);
            }else{
                return getIzq().accederSolicitud(fechaInicial, fechaFinal, auto);
            }
        }
        return null;
    }
}
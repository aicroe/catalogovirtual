
package org.example.catalogovirtual.modelo.cuerpo;

import org.example.catalogovirtual.modelo.cuerpo.excepciones.SolicitudNoValidaException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.TipoSolicitudInvalidaException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Buscador;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Fechas;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Registro de las solicitudes.
 *
 * @author empujesoft
 * @version 2015.08.02
 */
public class RegistroSolicitudes implements Serializable
{
    private ArbolBBS[] solicitudes;
    private ArrayList<Solicitud> historial;
    
    /**
     * Crea un registro de solicitudes vacio.
     */
    public RegistroSolicitudes()
    {
        solicitudes = new ArbolBBS[Solicitudes.CANT_TIPOS_SOLICITUDES];
        solicitudes[Solicitudes.SOLICITUD_RESERVA] = new ArbolBBS();
        solicitudes[Solicitudes.SOLICITUD_EN_CURSO] = new ArbolBBS();
        solicitudes[Solicitudes.SOLICITUD_VENCIDA] = new ArbolBBS();
        historial = new ArrayList<>();
    }
    
    /**
     * Actualiza el registro segun la fecha actual, elimina las reservas
     * que ya hayan pasado su fecha de inicio, pone en vencidos las 
     * solicitues en curso que ya haya pasado su fecha final.
     */
    public void actualizarRegistro()
    {
        ArbolBBS reservas = solicitudes[Solicitudes.SOLICITUD_RESERVA];
        ArbolBBS encurso = solicitudes[Solicitudes.SOLICITUD_EN_CURSO];
        ArbolBBS vencidos = solicitudes[Solicitudes.SOLICITUD_VENCIDA];
        Date fechaActual = Fechas.getFechaActual();
        actualizarReservas(reservas, fechaActual);
        actualizarEnCurso(encurso, vencidos, fechaActual);
    }
    
    /**
     * Actualiza las reservas, si ya paso su fecha de inicio se elimina.
     * @param reservas el arbol de reservas
     * @param fechaLimite fecha actual
     */
    private void actualizarReservas(ArbolBBS reservas, Date fechaLimite)
    {
        if(!reservas.estaVacia()){
            Solicitud solicitud = reservas.getRaiz();
            if(fechaLimite.compareTo(solicitud.getFechaInicial()) > 0 && 
                    Fechas.calcularDiasEntreFechas(
                            solicitud.getFechaInicial(), fechaLimite) > 0){
                solicitud.setEstado(Solicitudes.ESTADO_ELIMINADO);
                solicitud.getAuto().setDisponible(true);
                if(!reservas.eliminar(solicitud)){
                    throw new UnsupportedOperationException();
                }
            }
            if(reservas.getIzq() != null){
                actualizarReservas(reservas.getIzq(), fechaLimite);
            }
            if(reservas.getDer() != null){
                actualizarReservas(reservas.getDer(), fechaLimite);
            }
        }
    }
    
    /**
     * Actualiza las solicitudes en curso que ya haya pasado su fecha final, y
     * se las pone en el arbol de vencidos.
     * @param encurso arbol de solicitudes en curso
     * @param vencidos arbol de solicitudes vencidas
     * @param fechaActual fecha actual (referencia)
     */
    private void actualizarEnCurso(ArbolBBS encurso, ArbolBBS vencidos, Date fechaActual)
    {
        if(!encurso.estaVacia()){
            Solicitud solicitud = encurso.getRaiz();
            int comparacion = fechaActual.compareTo(solicitud.getFechaFinal());
            if(comparacion > 0 && Fechas.calcularDiasEntreFechas(
                    solicitud.getFechaFinal(), fechaActual) > 0){
                if(!encurso.eliminar(solicitud)){
                    throw new UnsupportedOperationException();
                }
                vencidos.insertar(solicitud);
            }
            if(encurso.getDer() != null){
                actualizarEnCurso(encurso.getDer(), vencidos, fechaActual);
            }
            if(encurso.getIzq() != null){
                actualizarEnCurso(encurso.getIzq(), vencidos, fechaActual);
            }
        }
    }
    
    /**
     * Historial de solicitudes terminadas.
     * @return Devuelve el historial
     */
    public ArrayList<Solicitud> getHistorial()
    {
        return historial;
    }
    
    /**
     * Enlista toadas las solicitudes que no hayan terminado.
     * @return Devulve la una lista con las solicitudes
     */
    public ArrayList<Solicitud> listarRegistro()
    {
        ArrayList<Solicitud> lista = new ArrayList<>();
        lista.addAll(listarTipoSolicitud(Solicitudes.SOLICITUD_RESERVA));
        lista.addAll(listarTipoSolicitud(Solicitudes.SOLICITUD_EN_CURSO));
        lista.addAll(listarTipoSolicitud(Solicitudes.SOLICITUD_VENCIDA));
        lista.addAll(historial);
        return lista;
    }
    
    /**
     * Lista un tipo especifico de solicitudes.
     * @param tipo tipo de solicitud, son validas los Tipos especificados en la
     *              clase Solicitudes como Solicitudes:
     *              Solicitudes,SOLICITUD_EN_RESERVA
     *              Solicitudes,SOLICITUD_EN_CURSO
     *              Solicitudes,SOLICITUD_VENCIDA
     * @return Devuelve la lista del tipo deseado sino una excepcion
     * @throws TipoSolicitudInvalidaException
     */
    public ArrayList<Solicitud> listarTipoSolicitud(int tipo)
    {
        verificarTipo(tipo);
        return solicitudes[tipo].elementosEnBFS();
    }
    
    /**
     * Verifica el tipo de solicitud que se requiere.
     * @param tipo tipo de solicitud, son validas los Tipos especificados en la
     *              clase Solicitudes como Solicitudes:
     *              Solicitudes,SOLICITUD_EN_RESERVA
     *              Solicitudes,SOLICITUD_EN_CURSO
     *              Solicitudes,SOLICITUD_VENCIDA
     * @throws TipoSolicitudInvalidaException
     */
    private void verificarTipo(int tipo)
    {
        if(tipo < 0 || tipo >= Solicitudes.CANT_TIPOS_SOLICITUDES){
            throw new TipoSolicitudInvalidaException();
        }
    }
    
    /**
     * Inserta una solicitud en el registro, si la solicitud no es valida lanza
     * una excepcion.
     * @param solicitud la solicitud a insertar
     * @throws SolicitudNoValidaException 
     */
    public void insertarSolicitud(Solicitud solicitud) throws SolicitudNoValidaException
    {
        Solicitudes.verificarSolParaInsertar(solicitud);
        solicitudes[Solicitudes.SOLICITUD_RESERVA].insertar(solicitud);
        solicitud.getAuto().setDisponible(false);
    }
        
    /**
     * Verifica si los datos para buscar una solicitud son validos.
     * @param fechaInicial
     * @param fechaFinal
     * @param auto 
     * @throws IllegalArgumentException
     */
    private void verificarDatosValidos(Date fechaInicial, Date fechaFinal, Auto auto)
    {
        if(fechaInicial == null || fechaFinal == null || auto == null ||
                (fechaInicial.compareTo(fechaFinal) >= 0)){
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Aprueba una solicitud en reserva, obtenemos la solicitud por su fecha
     * final, la fecha inicial y el auto que reservo.
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @return devuelve true si encontro la reserva y aprobo sin problemas
     * @throws IllegalArgumentException
     */
    public boolean aprobarReserva(Date fechaInicial, Date fechaFinal, Auto auto)
    {
        verificarDatosValidos(fechaInicial, fechaFinal, auto);
        Solicitud solicitud = solicitudes[Solicitudes.SOLICITUD_RESERVA].retirar(
                fechaInicial, fechaFinal, auto);
        if(solicitud != null && Fechas.calcularDiasEntreFechas(
                Fechas.getFechaActual(), fechaInicial) == 0){
            solicitud.setEstado(Solicitudes.ESTADO_EN_CURSO);
            solicitudes[Solicitudes.SOLICITUD_EN_CURSO].insertar(solicitud);
            return true;
        }
        return false;
    }
    
    /**
     * Elimina una solicitud en reserva dado su fecha inicial, fecha final, 
     * y el auto que se reservo, Si no se encontro el auto se devuelve false, y
     * si todo marcha bien devuelve true. Si el auto al que hace referencia esta
     * disponible se lanza una excepcion de operacion no soportada.
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @return Devuelve true si se encontro el auto, sino false
     * @throws UnsupportedOperationException
     * @throws IllegalArgumentException
     */
    public boolean eliminarReserva(Date fechaInicial, Date fechaFinal, Auto auto)
    {
        verificarDatosValidos(fechaInicial, fechaFinal, auto);
        Solicitud solicitud = 
                solicitudes[Solicitudes.SOLICITUD_RESERVA].retirar(
                        fechaInicial, fechaFinal, auto);
        if(solicitud != null){
            if(!solicitud.getAuto().estaDisponible()){
                solicitud.getAuto().setDisponible(true);
                solicitud.setEstado(Solicitudes.ESTADO_ELIMINADO);
                historial.add(solicitud);
                return true;
            }else{
                throw new UnsupportedOperationException();
            }
        }
        return false;
    }
    
    /**
     * Elimina forzadamente una solicitud y para asegurarse se necesita la
     * solicitud completa a ser eliminada.
     * @param solicitud La solicitud a ser eliminada del registro
     * @return devuelve true si se elimino
     */
    public boolean eliminacionForzada(Solicitud solicitud)
    {
        int tipo = Solicitudes.tipoPorEstado(solicitud.getEstado());
        if(tipo == Solicitudes.SOLICITUD_TERMINADA){
            return historial.remove(solicitud);
        }else{
            if(solicitudes[tipo].retirar(
                    solicitud.getFechaInicial(), 
                    solicitud.getFechaFinal(), solicitud.getAuto()) == solicitud){
                solicitud.getAuto().setDisponible(true);
                solicitud.setEstado(Solicitudes.ESTADO_ELIMINADO);
                historial.add(solicitud);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Termina una solicitud, primero obtenemos la solicitud dados la fecha inicial, 
     * la fecha final, el auto al que hace referencia y ademas el tipo de solicitud
     * que es, Por ejemplo puede ser una solicitud en curso(solicitud aprobada y
     * su fecha final aun no ha llegado), o vencida(solicitud aprobada pero su
     * fecha final ya ha pasado), Se lanza una excepcion si el tipo no es
     * uno valido, si no se encuentra la solicitud se devuelve false, si se la 
     * encuentra, entonces se cambia su estado a TERMIANDO se hace el auto al que
     * tiene referencia disponible y se lo pone al historial, luego devuelve true
     * porque todo fue bien.
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @param tipo
     * @return Devuelve true si se encontro la solicitud y se la termino, sino false
     * @throws IllegalArgumentException
     */
    public boolean terminarSolicitud(Date fechaInicial, Date fechaFinal, 
            Auto auto, int tipo){
        if(tipo != Solicitudes.SOLICITUD_EN_CURSO && tipo != Solicitudes.SOLICITUD_VENCIDA){
            throw new IllegalArgumentException();
        }
        verificarDatosValidos(fechaInicial, fechaFinal, auto);
        Solicitud solicitud = solicitudes[tipo].retirar(fechaInicial, fechaFinal, auto);
        if(solicitud != null){
            solicitud.setEstado(Solicitudes.ESTADO_TERMINADO);
            solicitud.getAuto().setDisponible(true);
            historial.add(solicitud);
            return true;
        }
        return false;
    }
    
    /**
     * Encuentra una solicitud del historial dado su fecha inicial, fecha final, 
     * y el auto que se reservo.
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @return Devuelve el auto que se encontro, sino nulo
     * @throws IllegalArgumentException
     */
    public Solicitud verDelHistorial(Date fechaInicial, Date fechaFinal, Auto auto)
    {
        verificarDatosValidos(fechaInicial, fechaFinal, auto);
        int i = 0;
        while(i < historial.size()){
            if(historial.get(i).esIgual(fechaInicial, fechaFinal, auto)){
                return historial.get(i);
            }else{
                i++;
            }
        }
        return null;
    }
    
    /**
     * Elimina una solicitud en del historial dado su fecha inicial, fecha final, 
     * y el auto que se reservo.
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @return Devuelve true si se encontro el auto, sino false
     * @throws IllegalArgumentException
     */
    public boolean eliminarDelHistorial(Date fechaInicial, Date fechaFinal, Auto auto)
    {
        verificarDatosValidos(fechaInicial, fechaFinal, auto);
        int i = 0;
        while(i < historial.size()){
            if(historial.get(i).esIgual(fechaInicial, fechaFinal, auto)){
                return historial.remove(i) != null;
            }else{
                i++;
            }
        }
        return false;
    }
    
    /**
     * Verifica si una solicitud esta en el registro dado su fecha inicial, fecha
     * final, el auto y su tipo.
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @param tipo
     * @return devuelve true si estaba en el registro
     * @throws IllegalArgumentException
     */
    public boolean estaSolicitud(Date fechaInicial, Date fechaFinal, 
            Auto auto, int tipo){
        verificarTipo(tipo);
        verificarDatosValidos(fechaInicial, fechaFinal, auto);
        return solicitudes[tipo].estaSolicitid(fechaInicial, fechaFinal, auto);
    }
    
    /**
     * Encuentra una solicitud por sus fechas y el auto, encuntra al auto
     * si no fue terminada o eliminada, si no existe tal auto devuelve nulo.
     * @param fechaInicial
     * @param fechaFinal
     * @param auto
     * @return la solicitud encontrada
     */
    public Solicitud verSolicitud(Date fechaInicial, Date fechaFinal, Auto auto){
        
        int tipo = 0;
        Solicitud solicitud = null;
        while(solicitud == null && tipo < Solicitudes.CANT_TIPOS_SOLICITUDES){
            solicitud = solicitudes[tipo].accederSolicitud(
                    fechaInicial, fechaFinal, auto);
            tipo++;
        }
        return solicitud;
    }
    
    /**
     * Proporciona una forma de actuar a los buscadores, y retorna las solicitudes
     * emparejadas segun los parametros de los buscadores, el metodo de busqueda
     * es 'O', osea que emparejara todos los autos que cada buscador independiente
     * empareje.
     * @param buscadores lista de buscadores
     * @return las solicitudes emparejadas
     */
    public ArrayList<Solicitud> buscar(ArrayList<Buscador> buscadores){
        
        ArrayList<Solicitud> registro = listarRegistro();
        HashSet<Solicitud> emparejados = new HashSet<>();
        for(Buscador buscador: buscadores){
            emparejados.addAll(buscador.buscar(registro));
        }
        return new ArrayList<>(emparejados);
    }
}

package org.example.catalogovirtual.modelo.cuerpo.utiles;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.SolicitudNoValidaException;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.Color;
import java.util.Date;

/**
 * Utiles de las solicitudes
 *
 * @author empujesoft
 * @version 2015.08.02
 */
public final class Solicitudes
{
    public static final int SOLICITUD_RESERVA = 0;
    public static final int SOLICITUD_EN_CURSO = 1;
    public static final int SOLICITUD_VENCIDA = 2;
    public static final int SOLICITUD_TERMINADA = 99;
    public static final int TODOS = -1;
    
    public static final String[] TIPOS_NOMBRES = 
            {"Reserva", "En Curso", "Vencida", "Terminado", "Todos"};
    
    public static final int CANT_TIPOS_SOLICITUDES = 3;
    public static final int LIMITE_SOLIC_CLIENTE = 5;
    
    public static final String ESTADO_RESERVA = "En Reserva";
    public static final String ESTADO_EN_CURSO = "En Curso";
    public static final String ESTADO_TERMINADO = "Terminado";
    public static final String ESTADO_VENCIDO = "Vencido";
    public static final String ESTADO_ELIMINADO = "Eliminado";
    
    public static final int MAX_NUM_DIAS_EN_RESERVA = 2;
    public static final int MAX_NUM_DIAS_ALQUILER = 30;

    
    private Solicitudes()
    {
    }
    
    public static int tipoPorNombre(String nombre){
        
        if(nombre.equals(TIPOS_NOMBRES[0])){
            return SOLICITUD_RESERVA;
        }else if(nombre.equals(TIPOS_NOMBRES[1])){
            return SOLICITUD_EN_CURSO;
        }else if(nombre.equals(TIPOS_NOMBRES[2])){
            return SOLICITUD_VENCIDA;
        }else if(nombre.equals(TIPOS_NOMBRES[3])){
            return SOLICITUD_TERMINADA;
        }else if(nombre.equals(TIPOS_NOMBRES[4])){
            return TODOS;
        }else{
            throw new UnsupportedOperationException();
        }
    }
    
    public static int tipoPorEstado(String estado){
        
        if(estado.equals(ESTADO_ELIMINADO) || 
                estado.equals(ESTADO_TERMINADO)){
            return SOLICITUD_TERMINADA;
        }else if(estado.equals(ESTADO_RESERVA)){
            return SOLICITUD_RESERVA;
        }else if(estado.equals(ESTADO_EN_CURSO)){
            return SOLICITUD_EN_CURSO;
        }else if(estado.equals(ESTADO_VENCIDO)){
            return SOLICITUD_VENCIDA;
        }else{
            throw new UnsupportedOperationException();
        }
    }
    
    public static void verificarSolParaInsertar(Solicitud solicitud) 
            throws SolicitudNoValidaException { 
        
        Date fechaInicial = solicitud.getFechaInicial();
        Date fechaFinal = solicitud.getFechaFinal();
        Cliente cliente = solicitud.getCliente();
        Auto auto = solicitud.getAuto();
        if (fechaInicial == null || fechaFinal == null ||
                cliente == null || auto == null) {
            throw new SolicitudNoValidaException("Campos no Validos");
        } else if (fechaInicial.compareTo(fechaFinal) >= 0 ||
                Fechas.calcularDiasEntreFechas(Fechas.getFechaActual(), fechaInicial) < 0) {
            throw new SolicitudNoValidaException("Fechas no Validas");
        } else if (!solicitud.getEstado().equals(Solicitudes.ESTADO_RESERVA)) {
            throw new SolicitudNoValidaException("Estado de Solicitud no Valido");
        } else if (!solicitud.getAuto().estaDisponible()) {
            throw new SolicitudNoValidaException("Auto no Disponible");
        } else if (solicitud.getPrecioTotal() <= 0) {
            throw new SolicitudNoValidaException("Precio calculado Incorrectamente");
        } else if (solicitud.getGarantia() <= 0) {
            throw new SolicitudNoValidaException("Garantia no Valida");
        } else if (solFueraDeRangoDeAprob(solicitud)){
            throw new SolicitudNoValidaException(
                    "La solicitud debe aprovarse en maximo: " + 
                            Preferencias.getMaxNumDiasSolEnReserva() + " dias");
        } else if (solSuperaMaxDiasAlquiler(solicitud)){
            throw new SolicitudNoValidaException("El alquiler es de maximo " + 
                    Preferencias.getMaxNumDiasAlquiler() + " dias");
        }
    }
    
    private static boolean solFueraDeRangoDeAprob(Solicitud solicitud){
        
        int diasSolEnReserva = Fechas.calcularDiasEntreFechas(
                Fechas.getFechaActual(), solicitud.getFechaInicial());
        return diasSolEnReserva > Preferencias.getMaxNumDiasSolEnReserva() && 
                diasSolEnReserva >= 0;
    }
    
    private static boolean solSuperaMaxDiasAlquiler(Solicitud solicitud){
        
        int diasAlquiler = Fechas.calcularDiasEntreFechas(
                solicitud.getFechaInicial(), solicitud.getFechaFinal());
        return diasAlquiler > Preferencias.getMaxNumDiasAlquiler();
    }
    
    public static Color colorDescriptivoDeEstado(String estado){
        
        if(estado.equals(ESTADO_ELIMINADO) || estado.equals(ESTADO_TERMINADO)){
            return Color.RED;
        }else if(estado.equals(ESTADO_VENCIDO)){
            return Color.ORANGE;
        }else if(estado.equals(ESTADO_EN_CURSO)){
            return Color.GREEN;
        }else{
            return UsuarioGUI.VERDE_OSCURO;
        }
    }
}

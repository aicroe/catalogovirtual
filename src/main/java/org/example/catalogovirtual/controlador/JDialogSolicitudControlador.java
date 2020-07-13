/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.controlador;

import org.example.catalogovirtual.modelo.cuerpo.RegistroSolicitudes;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import org.example.catalogovirtual.vista.UsuarioGUI;
import org.example.catalogovirtual.vista.admin.JDialogSolicitud;
import org.example.catalogovirtual.vista.admin.JDialogTerminar;
import org.example.catalogovirtual.vista.admin.JPanelSolicitud;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author garcia
 */
public class JDialogSolicitudControlador {
    
    private JDialogSolicitud dialogoSolicitud;
    private RegistroSolicitudes registro;
    private Solicitud solicitud;
    private JPanelSolicitud panelSolicitud;
    
    public JDialogSolicitudControlador(
            JDialogSolicitud dialogoSolicitud, 
            JPanelSolicitud panelSolicitud, 
            Solicitud solicitud,
            RegistroSolicitudes registro){
        this.dialogoSolicitud = dialogoSolicitud;
        this.registro = registro;
        this.solicitud = solicitud;
        this.panelSolicitud = panelSolicitud;
    }
    
    public class ListenerBotonAprobar implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            Date fechaIni = solicitud.getFechaInicial();
            Date fechaFin = solicitud.getFechaFinal();
            Auto auto = solicitud.getAuto();
            if(registro.aprobarReserva(fechaIni, fechaFin, auto)){
                dialogoSolicitud.actualizarComponetes(solicitud);
            }else{
                throw new UnsupportedOperationException();
            }
        }
    }
    
    public class ListenerBotonEliminar implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            String estado = solicitud.getEstado();
            String titulo;
            String mensaje;
            int tipo;
            if(estado.equals(Solicitudes.ESTADO_ELIMINADO) || 
                    estado.equals(Solicitudes.ESTADO_TERMINADO)){
                titulo = "Eliminar";
                mensaje = "¿Esta Seguro?";
                tipo = JOptionPane.INFORMATION_MESSAGE;
            }else{
                titulo = "Eliminacion Forsoza";
                mensaje = "¿Esta Seguro, La Solicitud esta en " + estado + "?";
                tipo = JOptionPane.WARNING_MESSAGE;
            }
            int opcion = JOptionPane.showConfirmDialog(
                    dialogoSolicitud, mensaje, titulo, 
                    JOptionPane.YES_NO_OPTION, tipo);
            if(opcion == JOptionPane.YES_OPTION){
                if(registro.eliminacionForzada(solicitud)){
                    dialogoSolicitud.cerrarDialogo();
                }else{
                    throw new UnsupportedOperationException();
                }
            }
        }
    }
    
    public class ListenerBotonTerminar implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            JDialogTerminar dialogoTerminar = 
                    new JDialogTerminar((UsuarioGUI) dialogoSolicitud.getOwner());
            dialogoTerminar.aniadirListenerTerminal(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    String observaciones = dialogoTerminar.getObservaciones();
                    boolean seDejaLaGarantia = dialogoTerminar.seDejaLaGarantia();
                    double recaudacion = 0;
                    if(seDejaLaGarantia){
                        recaudacion += solicitud.getGarantia();
                    }
                    recaudacion += solicitud.getPrecioTotal();
                    registro.terminarSolicitud(
                            solicitud.getFechaInicial(), 
                            solicitud.getFechaFinal(), 
                            solicitud.getAuto(), 
                            Solicitudes.tipoPorEstado(solicitud.getEstado()));
                    solicitud.setObservaciones(observaciones);
                    solicitud.setRecaudacionFinal(recaudacion);
                    dialogoTerminar.cerrarDialogo();
                    dialogoSolicitud.actualizarComponetes(solicitud);
                }
            });
            dialogoTerminar.abrirDialogo();
        }
    }
}

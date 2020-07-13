/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.controlador;

import org.example.catalogovirtual.modelo.cuerpo.Catalogo;
import org.example.catalogovirtual.modelo.cuerpo.RegistroSolicitudes;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Validador;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.vista.cliente.JDialogCliente;
import org.example.catalogovirtual.vista.cliente.JDialogModifPerfil;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Controlador del JDialog del Cliente.
 *
 * @author empujesoft
 * @version 2015.08.04
 */
public class JDialogClienteControlador
{
    private JDialogCliente dialogoCliente;
    private RegistroSolicitudes registro;
    private Cliente cliente;
    private Catalogo catalogo;
    
    public JDialogClienteControlador(
            JDialogCliente dialogo, RegistroSolicitudes registro, 
            Catalogo catalogo, Cliente cliente){
        this.dialogoCliente = dialogo;
        this.registro = registro;
        this.catalogo = catalogo;
        this.cliente = cliente;
    }
    
    public class ListenerCerrarSesion implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            UsuarioGUI duenio = (UsuarioGUI) dialogoCliente.getOwner();
            dialogoCliente.cerrarDialogo();
            Utiles.cerrarSesion(duenio);
        }
    }
    
    public class ListenerBotonEliminar implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            Date fechaInicialSelec = (Date) dialogoCliente.getDatoFilaSelec(JDialogCliente.COLUMNA_FECHA_INICIAL);
            Date fechaFinalSelec = (Date) dialogoCliente.getDatoFilaSelec(JDialogCliente.COLUMNA_FECHA_FINAL);
            Auto autoFilaSelec = (Auto) dialogoCliente.getDatoFilaSelec(JDialogCliente.COLUMNA_AUTO);
            String estado = (String) dialogoCliente.getDatoFilaSelec(JDialogCliente.COLUMNA_ESTADO);
            if(fechaInicialSelec != null && fechaFinalSelec != null && 
                    autoFilaSelec != null && estado != null){
                if(estado.equals(Solicitudes.ESTADO_RESERVA)){
                    int opcion = JOptionPane.showConfirmDialog(dialogoCliente,  "¿Eliminar Reserva?", 
                            "Confirmar", JOptionPane.YES_NO_OPTION);
                    if(opcion == JOptionPane.NO_OPTION){
                        return;
                    }else if(opcion == JOptionPane.YES_OPTION){
                        if(!registro.eliminarReserva(fechaInicialSelec, fechaFinalSelec, autoFilaSelec)){
                            throw new UnsupportedOperationException();
                        }
                    }else{
                        throw new UnsupportedOperationException();
                    }
                }else if(!estado.equals(Solicitudes.ESTADO_TERMINADO) && 
                        !estado.equals(Solicitudes.ESTADO_ELIMINADO)){
                    throw new UnsupportedOperationException();
                }
                Vector<Vector> solicitudes = dialogoCliente.getInfoSolicitudes();
                cliente.eliminarSolicitud(fechaInicialSelec, fechaFinalSelec, autoFilaSelec);
                if(Utiles.eliminarFilaSiEstaSolicitud(fechaInicialSelec, fechaFinalSelec, 
                        autoFilaSelec, solicitudes)){
                    dialogoCliente.setInfoSolicitudes(solicitudes);
                }else{
                    throw new UnsupportedOperationException();
                }
            }else{
                throw new UnsupportedOperationException();
            }
        }
    }
    
    public class ListenerModifPerfil implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JDialogModifPerfil dialogoModifPerfil = 
                    new JDialogModifPerfil((UsuarioGUI) dialogoCliente.getOwner());
            dialogoModifPerfil.aniadirListenerGuardarCambios(
                    new ListenerGuardarCambios(dialogoModifPerfil));
            dialogoModifPerfil.actualizarComponentes(cliente);
            dialogoModifPerfil.abrirDialogo();
        }
    }
    
    private class ListenerGuardarCambios implements ActionListener
    {
        private JDialogModifPerfil dialogo;
        
        public ListenerGuardarCambios(JDialogModifPerfil dialogo)
        {
            this.dialogo = dialogo;
        }
        
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JDialogModifPerfil.PerfilModificado perfil = dialogo.getPerfilModificado();
            try{
                Cliente.crearCliente(cliente.getCi(), 
                        (dialogo.cambiandoContrasenia()) ?
                                Validador.decodificarPassword(perfil.contraseniaNueva): 
                                "0000", 
                        perfil.nombre, perfil.tipoLicencia, 
                        perfil.telefono);
                 if(dialogo.cambiandoContrasenia() && 
                         cliente.setPassword(Validador.decodificarPassword(perfil.contraseniaActual), 
                        Validador.decodificarPassword(perfil.contraseniaNueva))){
                    throw new DatosInvalidosException("Contraseña Actual incorrecta");
                 }
                cliente.setNombre(perfil.nombre);
                cliente.setTelefono(perfil.telefono);
                cliente.setLicencia(perfil.tipoLicencia);
                cliente.setDireccion(perfil.direccion);
                cliente.setLugarTrabajo(perfil.lugarTrabajo);
                cliente.setOcupacion(perfil.ocupacion);
                
                dialogo.cerrarDialogo();
                dialogoCliente.actualizarComponentes(cliente);
            }catch(DatosInvalidosException ex){
                dialogo.cambiarTextoBarraMensaje(ex.getMessage(), Color.RED);
            }
        }
    }
}

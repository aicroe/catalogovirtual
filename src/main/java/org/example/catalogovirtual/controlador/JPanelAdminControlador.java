/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.controlador;

import org.example.catalogovirtual.modelo.Plataforma;
import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.Catalogo;
import org.example.catalogovirtual.modelo.cuerpo.RegistroSolicitudes;
import org.example.catalogovirtual.modelo.cuerpo.RegistroUsuarios;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.NoExisteTalAutoException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.PlacaRepetidaException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Buscador;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.nucleo.Administrador;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import org.example.catalogovirtual.vista.UsuarioGUI;
import org.example.catalogovirtual.vista.admin.JDialogAuto;
import org.example.catalogovirtual.vista.admin.JDialogInsertar;
import org.example.catalogovirtual.vista.admin.JDialogPreferencias;
import org.example.catalogovirtual.vista.admin.JDialogSolicitud;
import org.example.catalogovirtual.vista.admin.JPanelAdmin;
import org.example.catalogovirtual.vista.admin.JPanelLista;
import org.example.catalogovirtual.vista.admin.JPanelSolicitud;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author USER-UNO
 */
public class JPanelAdminControlador {
    
    private UsuarioGUI usuarioGui;
    private JPanelSolicitud panelSolicitud;
    private JPanelLista panelLista;
    private Catalogo catalogo;
    private RegistroUsuarios registroUs;
    private RegistroSolicitudes registroSols;
    private Administrador administrador;
    private ArrayList<Solicitud> ultimaAccion;
    
    public JPanelAdminControlador(JPanelAdmin panel, 
            Plataforma plataforma, Administrador administrador){
        
        this.administrador = administrador;
        usuarioGui = (UsuarioGUI) panel.getDuenio();
        panelSolicitud = panel.getPanelSolicitud();
        panelLista = panel.getPanelLista();
        catalogo = plataforma.getCatalogo();
        registroUs = plataforma.getRegUsuarios();
        registroSols = plataforma.getRegSolicitudes();
        ultimaAccion = new ArrayList<>();
    }
    
    public class ListenerBuscar implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            ArrayList<Buscador> buscadores = panelSolicitud.contruirBuscadores();
            ArrayList<Solicitud> emparejados = registroSols.buscar(buscadores);
            ultimaAccion = emparejados;
            panelSolicitud.actualizarInfoTabla(emparejados);
        }
    }
    
    public class ListenerVerLista implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            int tipo = panelSolicitud.getTipoSelect();
            ArrayList<Solicitud> lista = null;
            if(tipo >= 0 && tipo < Solicitudes.CANT_TIPOS_SOLICITUDES){
                lista = registroSols.listarTipoSolicitud(tipo);
            }else if(tipo == Solicitudes.SOLICITUD_TERMINADA){
                lista = registroSols.getHistorial();
            }else if(tipo == Solicitudes.TODOS){
                lista = registroSols.listarRegistro();
            }
            ultimaAccion = lista;
            panelSolicitud.actualizarInfoTabla(lista);
            UsuarioGUI.actualizarEstado(lista.isEmpty()?
                    " ":"Mostrando lista seleccionada.");
        }
    }
    
    public class ListenerAbrirSolicitud implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            Date fechaInicial = (Date) panelSolicitud.getDatoFilaSelec(
                    JPanelSolicitud.COLUMNA_FECHA_INICIO);
            Date fechaFinal = (Date) panelSolicitud.getDatoFilaSelec(
                    JPanelSolicitud.COLUMNA_FECHA_FINAL);
            Auto auto = (Auto) panelSolicitud.getDatoFilaSelec(
                    JPanelSolicitud.COLUMNA_AUTO);
            String estado = (String) panelSolicitud.getDatoFilaSelec(
                    JPanelSolicitud.COLUMNA_ESTADO);
            Solicitud solicitud;
            if(estado.equals(Solicitudes.ESTADO_ELIMINADO) || 
                    estado.equals(Solicitudes.ESTADO_TERMINADO)){
                solicitud = registroSols.verDelHistorial(fechaInicial, fechaFinal, auto);
            }else{
                solicitud = registroSols.verSolicitud(fechaInicial, fechaFinal, auto);
            }
            if(solicitud != null){
                JDialogSolicitud dialogoSolicitud = new JDialogSolicitud(usuarioGui);
                dialogoSolicitud.actualizarComponetes(solicitud);
                JDialogSolicitudControlador controlador = new
                        JDialogSolicitudControlador(dialogoSolicitud, panelSolicitud, 
                                solicitud, registroSols);
                dialogoSolicitud.setListeners(controlador);
                dialogoSolicitud.abrirDialogo();
                panelSolicitud.actualizarInfoTabla(ultimaAccion);
            }else{
                JOptionPane.showMessageDialog(
                        panelSolicitud, "Solicitud Eliminada del registro");
            }
            panelSolicitud.habilitarBotonAbrir(false);
        }
    }
    
    public class ListenerInsertarAuto implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            JDialogInsertar dialogoInsertar = new JDialogInsertar(usuarioGui);
            dialogoInsertar.aniadirListenerBotonInsertar(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event){
                    try{
                        Auto fabricado = dialogoInsertar.fabricarAutoInsertado();
                        catalogo.insertarNuevoAuto(fabricado);
                        panelLista.actualizarAutos(catalogo.listarCatalogo());
                        UsuarioGUI.actualizarEstado("Inserscion Exitosa!",
                                UsuarioGUI.VERDE_OSCURO);
                        dialogoInsertar.cerrarDialogo();
                    }catch(PlacaRepetidaException ex){
                        dialogoInsertar.cerrarDialogo();
                        UsuarioGUI.actualizarEstado("Error: " + ex.getMessage(), 
                                Color.RED);
                    }catch(DatosInvalidosException ex){
                        dialogoInsertar.actualizarBarraMensaje(ex.getMessage(), 
                                Color.RED);
                    }
                }
            });
            dialogoInsertar.abrirDialogo();
        }
    }
    
    public class ListenerListaAutos extends MouseAdapter{
        private JPanelAdminControlador controlador;
        public ListenerListaAutos(JPanelAdminControlador controlador){
            this.controlador = controlador;
        }
        @Override
        public void mouseReleased(MouseEvent event){
            
            if(event.getClickCount() == 2){
                Auto auto = panelLista.getAutoSelec();
                if(auto != null){
                    JDialogAuto dialogoAuto = new JDialogAuto(usuarioGui);
                    dialogoAuto.construir();
                    dialogoAuto.actualizar(auto);
                    JDialogAutoControlador control = new JDialogAutoControlador(dialogoAuto);
                    dialogoAuto.setListeners(control);
                    dialogoAuto.addListenerBotonAceptar(controlador);
                    dialogoAuto.addListenerBotonEliminar(controlador);
                    dialogoAuto.setVisible(true);
                }
            }
        }
    }
    
    public ActionListener addListenerAceptarDialogAuto(JDialogAuto dialogo){
        ListenerAceptarDialogAuto listener = new ListenerAceptarDialogAuto();
        listener.setDialogo(dialogo);
        listener.setCatalogo(catalogo);
        return listener;
    }
    
    public ActionListener addListenerEliminarDialogAuto(JDialogAuto dialogo){
        ListenerEliminarDialogAuto listener = new ListenerEliminarDialogAuto();
        listener.setDialogo(dialogo);
        listener.setCatalogo(catalogo);
        return listener;
    }    
    
    /**
     * Oyente que realiza los cambios de la información de los autos, si es que se hubiera
     * realizado algun cambio. Si se realizaron cambios, el oyente elimina el auto con la
     * placa inicial dada al dialogo para insertar el auto con la informacion modificada
     */
    public class ListenerAceptarDialogAuto implements ActionListener{
        private JDialogAuto dialogo;
        private Catalogo catalogo;
        private void setDialogo(JDialogAuto dialogo){
            this.dialogo = dialogo;
        }
        private void setCatalogo(Catalogo catalogo){
            this.catalogo = catalogo;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(dialogo.esModificable()){
                dialogo.actualizarBarraMensaje("Debe guardar los cambios antes de salir",
                        Color.RED);
                dialogo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
            else{
                if(dialogo.getAutoModificado() != null){
                    try {
                        String placaInicial = dialogo.getPlacaInicial();
                        Auto autoInicial = catalogo.seleccionarAuto(placaInicial);
                        if(catalogo.eliminarAutoPorPlaca(placaInicial))
                            try {
                                catalogo.insertarNuevoAuto(dialogo.getAutoModificado());
                                dialogo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                dialogo.cerrarDialogo();
                                panelLista.actualizarAutos(catalogo.listarCatalogo());
                            }catch (PlacaRepetidaException | DatosInvalidosException ex) {
                                dialogo.actualizarBarraMensaje(ex.getMessage(), Color.RED);
                                try {
                                    catalogo.insertarNuevoAuto(autoInicial);
                                } catch (PlacaRepetidaException | DatosInvalidosException ex1) {
                                    throw new UnsupportedOperationException();
                                }
                            }
                        else{ 
                            dialogo.actualizarBarraMensaje("Error auto en uso", Color.RED);
                            panelLista.actualizarAutos(catalogo.listarCatalogo());
                        }
                    } catch (NoExisteTalAutoException ex) {
                        throw new UnsupportedOperationException();
                    }
                }
            }
        } 
    }
    
    /*
        Oyente que ejecuta la eliminación de los autos del catalogo. Esta accion debe realizarse a partir
        del dialogo del auto, en el que no deben estar realizandose modificaciones. El dialogo eliminara 
        del catalogo el auto con la placa inicial (previa a los cambios) del auto desplegado, previa
        confirmacion.
    */
    public class ListenerEliminarDialogAuto implements ActionListener{
        private JDialogAuto dialogo;
        private Catalogo catalogo;
        private void setDialogo(JDialogAuto dialogo){
            this.dialogo = dialogo;
        }
        private void setCatalogo(Catalogo catalogo){
            this.catalogo = catalogo;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            dialogo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            if(dialogo.esModificable())
                dialogo.actualizarBarraMensaje("Debe guardar los cambios antes de salir",
                        Color.RED);
            else if(dialogo.getAutoModificado() != null)
                dialogo.actualizarBarraMensaje("Seleccione aceptar e ingrese nuevamente" + 
                        " para poder eliminar el auto", Color.RED);
            else{
                String titulo = new String ("Confirmar");
                String mensaje = "¿Esta seguro de eliminar el auto con placa ";
                mensaje += dialogo.getPlacaInicial();
                mensaje += "?\n";
                mensaje += " La informacion sera eliminada permanentemente";
                String[] opciones = {"Aceptar", "Cancelar"};
                int opcion = JOptionPane.showConfirmDialog(dialogo, mensaje, titulo, 
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
                if(opcion == JOptionPane.YES_OPTION){
                    if(catalogo.eliminarAutoPorPlaca(dialogo.getPlacaInicial())){
                        dialogo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        dialogo.cerrarDialogo();
                        panelLista.actualizarAutos(catalogo.listarCatalogo());
                        UsuarioGUI.actualizarEstado("Eliminación exitosa", Color.BLUE);
                    }
                    else dialogo.actualizarBarraMensaje("El auto esta en uso no puede ser eliminado", Color.RED);
                }
                else dialogo.actualizarBarraMensaje("Eliminacion cancelada", Color.GREEN);
            }
        }
    }
    
    public class MouseListenerListaClientes extends MouseAdapter{
        
        @Override
        public void mouseReleased(MouseEvent event){
            
            Cliente cliente = panelLista.getClienteSelec();
            if(cliente != null){
                ultimaAccion = cliente.getSolicitudes();
                panelSolicitud.actualizarInfoTabla(ultimaAccion);
            }
        }
    }
    
    public class KeyListenerListaClientes extends KeyAdapter{
        
        @Override
        public void keyReleased(KeyEvent event){
            
            int tecla = event.getExtendedKeyCode();
            if(tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_DOWN){
                Cliente cliente = panelLista.getClienteSelec();
                if(cliente != null){
                    ultimaAccion = cliente.getSolicitudes();
                    panelSolicitud.actualizarInfoTabla(ultimaAccion);
                }
            }
        }
    }
    
    public class ListenerMenuSalir implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            Utiles.salir(usuarioGui);
        }
    }
    
    public class ListenerCerrarSesion implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            Utiles.cerrarSesion(usuarioGui);
        }
    }
    
    public class ListenerPreferencias implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            JDialogPreferencias dialogoPreferencias = 
                    new JDialogPreferencias(usuarioGui);
            dialogoPreferencias.setListeners(
                    new JDialogPreferControlador(
                            dialogoPreferencias, registroUs, administrador));
            dialogoPreferencias.actualizarComponentes(
                    Preferencias.getRaiz(),
                    administrador, Garantia.getValores());
            dialogoPreferencias.abrirDialogo();
        }
    }
}

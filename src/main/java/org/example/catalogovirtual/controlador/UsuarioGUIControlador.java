package org.example.catalogovirtual.controlador;

import org.example.catalogovirtual.modelo.Plataforma;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.UsuarioNoEncontradoException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.ContraseniaIncorrectaException;
import org.example.catalogovirtual.modelo.cuerpo.RegistroUsuarios;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.IDYaExistente;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Validador;
import org.example.catalogovirtual.modelo.nucleo.Administrador;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.modelo.nucleo.Usuario;
import org.example.catalogovirtual.vista.JDialogIngresar;
import org.example.catalogovirtual.vista.JPanelIngresar;
import org.example.catalogovirtual.vista.JPanelPrincipal;
import org.example.catalogovirtual.vista.JPanelRecuperar;
import org.example.catalogovirtual.vista.JPanelRegistrar;
import org.example.catalogovirtual.vista.UsuarioGUI;
import org.example.catalogovirtual.vista.admin.JPanelAdmin;
import org.example.catalogovirtual.vista.cliente.JPanelCliente;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Controlador de la GUI del usuario, basado en el patron MVC.
 * 
 * @author empujesoft
 * @version 2015.06.16
 */
public class UsuarioGUIControlador
{
    private Plataforma plataforma;
    private RegistroUsuarios registroUsuarios;
    private UsuarioGUI usuarioGui;
    private Usuario usuarioActual;
    
    public UsuarioGUIControlador(UsuarioGUI usuarioGui, Plataforma plataforma)
    {
        this.plataforma = plataforma;
        this.usuarioGui = usuarioGui;
        registroUsuarios = plataforma.getRegUsuarios();
        usuarioActual = null;        
    }
    
    public void iniciarSesion()
    {
        JDialogIngresar dialogoIngresar = new JDialogIngresar(usuarioGui);
        dialogoIngresar.setListeneres(this);
        dialogoIngresar.abrirDialogo();
    }
    
    public class ListenerIngresar implements ActionListener
    {
        private JDialogIngresar dialogoIngresar;
        
        public ListenerIngresar(JDialogIngresar dialogoIngresar)
        {
            this.dialogoIngresar = dialogoIngresar;
        }
        
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JPanelIngresar panelIngresar = (JPanelIngresar) dialogoIngresar.getContenedor();
            String id = panelIngresar.getLogin();
            String password = Validador.decodificarPassword(panelIngresar.getPassword());
            try{
                usuarioActual = registroUsuarios.accederUsuario(id, password);
                JPanelPrincipal panelPrincipal;
                if(usuarioActual instanceof Cliente){
                    JPanelCliente panelCliente = new JPanelCliente(usuarioGui);
                    JPanelClienteControlador controlador = new JPanelClienteControlador(
                                    panelCliente, plataforma, (Cliente)usuarioActual);
                    panelCliente.setListeners(controlador);
                    panelPrincipal = panelCliente;
                }else if(usuarioActual instanceof Administrador){
                    JPanelAdmin panelAdmin = new JPanelAdmin(usuarioGui);
                    JPanelAdminControlador controlador =
                            new JPanelAdminControlador(panelAdmin, plataforma, 
                                    (Administrador) usuarioActual);
                    panelAdmin.setListeners(controlador);
                    panelAdmin.getPanelLista().actualizarAutos(plataforma.getCatalogo().listarCatalogo());
                    panelAdmin.getPanelLista().actualizarClientes(registroUsuarios.listarClientes());
                    panelPrincipal = panelAdmin;
                }else{
                    throw new UnsupportedOperationException();
                }
                usuarioGui.setPanelPrincipal(panelPrincipal);
                dialogoIngresar.cerrarDialogo();
                usuarioGui.abrirVentana();
            }catch(UsuarioNoEncontradoException | ContraseniaIncorrectaException ex){
                panelIngresar.cambiarTextoBarraMensaje(ex.getMessage(), Color.RED);
                usuarioActual = null;
            }
        }
    }
    
    public class ListenerRegistrar implements ActionListener
    {
        private JDialogIngresar dialogoIngresar;
        
        public ListenerRegistrar(JDialogIngresar dialogoIngresar)
        {
            this.dialogoIngresar = dialogoIngresar;
        }
        
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JPanelRegistrar panelRegistrar = (JPanelRegistrar) dialogoIngresar.getContenedor();
            try{
                Cliente nuevoCliente = panelRegistrar.crearClienteConDatos();
                registroUsuarios.insertarNuevoCliente(nuevoCliente);
                JPanelIngresar panelCambio = new JPanelIngresar(dialogoIngresar);
                panelCambio.actualizarComponentes(nuevoCliente.getLogin(), "");
                dialogoIngresar.cambiarPanel(panelCambio);
            }catch(IDYaExistente | DatosInvalidosException ex){
                panelRegistrar.cambiarTextoBarraMensaje(ex.getMessage(), Color.RED);
            }
        }
    }
    
    public class ListenerRecuperarContr implements ActionListener
    {
        private JDialogIngresar dialogoIngresar;
        
        public ListenerRecuperarContr(JDialogIngresar dialogoIngresar)
        {
            this.dialogoIngresar = dialogoIngresar;
        }
        
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JPanelRecuperar panelRecuperar = (JPanelRecuperar) dialogoIngresar.getContenedor();
            try{
                registroUsuarios.recuperarContrCliente(String.valueOf(panelRecuperar.getCi()),
                        panelRecuperar.getTelefono(),
                        Validador.decodificarPassword(panelRecuperar.getNuevaContr()));
                JPanelIngresar panelCambio = new JPanelIngresar(dialogoIngresar);
                panelCambio.actualizarComponentes(String.valueOf(panelRecuperar.getCi()), "");
                dialogoIngresar.cambiarPanel(panelCambio);
            }catch(UsuarioNoEncontradoException | DatosInvalidosException ex){
                panelRecuperar.cambiarTextoBarraMensaje(ex.getMessage(), Color.RED);
            }
        }
    }
    
    public class ListenerVentanaCerrando extends WindowAdapter
    {
        @Override 
        public void windowClosing(WindowEvent event)
        {
            plataforma.guardarDatos();
            usuarioGui.cerrarVentana();
        }
        
        @Override
        public void windowClosed(WindowEvent event)
        {
            System.out.println("TERMINADO...");
        }
    }
}

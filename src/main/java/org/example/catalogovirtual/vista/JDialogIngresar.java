package org.example.catalogovirtual.vista;

import org.example.catalogovirtual.controlador.UsuarioGUIControlador;
import org.example.catalogovirtual.controlador.Utiles;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;


/**
 * JDialog que se muestra cuando se iniciar la aplicacion y se pide una 
 * identificacion. Tambien se usa cuando el usuario termina la sesion.
 * 
 * @author empujesoft
 * @version 2015.07.02
 */
public class JDialogIngresar extends JDialogAbstracto
{
    private JPanelPrincipal contenedor;
    private JMenuItem salir;
    private ActionListener[] listeners;
    
    public static final int LISTENER_BOTON_INGRESAR     = 0;
    public static final int LISTENER_BOTON_REGISTRAR    = 1;
    public static final int LISTENER_BOTON_RECUPERAR    = 2;
    public static final int NUM_LISTENERS               = 3;
    
    public JDialogIngresar(JFrame duenio)
    {
        super(duenio, null);
        contenedor = new JPanelIngresar(this);
        salir = new JMenuItem("Salir");
        listeners = new ActionListener[NUM_LISTENERS];
        
        addWindowListener(new ListenerDialogoCerrando());
        salir.addActionListener(new ListenerMenuSalir());
    }
    
    @Override
    protected void construir()
    {
        contenedor.construir();
        setTitle(contenedor.toString());
        setJMenuBar(contenedor.getBarraMenu());
        setContentPane(contenedor);
        pack();
        Utiles.posicionarVentanaAlCentro(this);
    }
    
    public JMenuItem getMenuItemSalir()
    {
        return salir;
    }
    
    public JPanelPrincipal getContenedor()
    {
        return contenedor;
    }
    
    public ActionListener getListener(int boton)
    {
        return listeners[boton];
    }
    
    public void setListeneres(UsuarioGUIControlador controlador)
    {
        listeners[LISTENER_BOTON_INGRESAR] = controlador.new ListenerIngresar(this);
        listeners[LISTENER_BOTON_REGISTRAR] = controlador.new ListenerRegistrar(this);
        listeners[LISTENER_BOTON_RECUPERAR] = controlador.new ListenerRecuperarContr(this);
    }
    
    public void cambiarPanel(JPanelPrincipal panel)
    {
        contenedor = panel;
        construir();
    }
    
    private class ListenerMenuSalir implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            cerrarDialogo();
            WindowEvent evento = new WindowEvent(contenedor.getDuenio(), WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(evento);
        }
    }
    
    private class ListenerDialogoCerrando extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent event)
        {
            Utiles.salir((UsuarioGUI) getOwner());
        }
    }
}

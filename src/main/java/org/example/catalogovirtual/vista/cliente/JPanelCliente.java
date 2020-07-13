package org.example.catalogovirtual.vista.cliente;

import org.example.catalogovirtual.controlador.JPanelClienteControlador;
import org.example.catalogovirtual.controlador.Utiles;
import org.example.catalogovirtual.vista.JPanelPrincipal;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * JPanel del cliente.
 *
 * @author empujesoft
 * @version 2015.08.02
 */
public class JPanelCliente extends JPanelPrincipal
{
    private JPanelAuto panelAuto;
    private JPanelMostrador panelMostrador;
    private JMenuBarCliente barramenu;
    
    public JPanelCliente(UsuarioGUI duenio)
    {
        super(duenio);
        panelAuto = new JPanelAuto();
        panelMostrador = new JPanelMostrador();
        barramenu = new JMenuBarCliente();
    }
    
    public JPanelAuto getPanelAuto()
    {
        return panelAuto;
    }
    
    public JPanelMostrador getPanelMostrador()
    {
        return panelMostrador;
    }
    
    @Override
    public UsuarioGUI getDuenio()
    {
        return (UsuarioGUI) super.getDuenio();
    }
    
    @Override
    public JMenuBar getBarraMenu()
    {
        return barramenu;
    }
    
    @Override
    public void construir()
    {
        removeAll();
        setLayout(new BorderLayout());
        barramenu.construir();
        panelAuto.construir();
        panelMostrador.construir();
        
        add(panelAuto, BorderLayout.WEST);
        add(panelMostrador, BorderLayout.CENTER);
    }
    
    public void setListeners(JPanelClienteControlador controlador)
    {
        panelMostrador.setListeners(controlador);
        panelAuto.aniadirListenerAlquilar(controlador.new ListenerAlquilarAuto());
        barramenu.acercade.addActionListener(new Utiles.ListenerMenuAcercade());
        barramenu.infoUsuario.addActionListener(controlador.new ListenerMenuUsuario());
        barramenu.salir.addActionListener(controlador.new ListenerMenuSalir());
    }
    
    private class JMenuBarCliente extends JMenuBar
    {
        private JMenuItem salir;
        private JMenuItem acercade;
        private JMenuItem infoUsuario;
    
        public JMenuBarCliente()
        {
            salir = new JMenuItem("Salir");
            acercade = new JMenuItem("Acerca de ...");
            infoUsuario = new JMenuItem("Info Usuario ...");
        }
    
        public void construir()
        {
            removeAll();
        
            JMenu opciones = new JMenu("Opciones");
            opciones.add(infoUsuario);
            opciones.add(Utiles.crearMenuLookAndFeel(getDuenio()));
            opciones.add(salir);
        
            JMenu ayuda = new JMenu("Ayuda");
            ayuda.add(acercade);
            
            add(opciones);
            add(ayuda);
        }
    }
}

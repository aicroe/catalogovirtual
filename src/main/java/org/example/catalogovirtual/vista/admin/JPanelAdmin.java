package org.example.catalogovirtual.vista.admin;

import org.example.catalogovirtual.controlador.JPanelAdminControlador;
import org.example.catalogovirtual.controlador.Utiles;
import org.example.catalogovirtual.vista.JPanelPrincipal;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * JPanel del administrador.
 *
 * @author empujesoft
 * @version 2015.08.02
 */
public class JPanelAdmin extends JPanelPrincipal
{
    private JPanelLista panelEste;
    private JPanelSolicitud panelCentro;
    private JMenuBarAdmin barramenu;
    
    public JPanelAdmin(UsuarioGUI duenio)
    {
        super(duenio);
        barramenu = new JMenuBarAdmin();
        panelEste = new JPanelLista();
        panelCentro = new JPanelSolicitud();
    }
    
    public JPanelSolicitud getPanelSolicitud()
    {
        return panelCentro;
    }
    
    public JPanelLista getPanelLista()
    {
        return panelEste;
    }

    @Override
    public JMenuBar getBarraMenu()
    {
        return barramenu;
    }
    
    @Override
    public UsuarioGUI getDuenio()
    {
        return (UsuarioGUI) super.getDuenio();
    }
    
    @Override
    public void construir()
    {
        removeAll();
        setLayout(new BorderLayout(2, 0));
        setBorder(new EmptyBorder(2, 2, 2, 2));
        
        barramenu.construir();
        panelEste.construir();
        panelCentro.construir();
        
        add(panelCentro, BorderLayout.CENTER);
        add(panelEste, BorderLayout.EAST);
    }
    
    public void setListeners(JPanelAdminControlador controlador)
    {
        panelCentro.setListeners(controlador);
        panelEste.setListeners(controlador);
        barramenu.cerrarSesion.addActionListener(controlador.new ListenerCerrarSesion());
        barramenu.salir.addActionListener(controlador.new ListenerMenuSalir());
        barramenu.acercade.addActionListener(new Utiles.ListenerMenuAcercade());
        barramenu.preferencias.addActionListener(controlador.new ListenerPreferencias());
    }
    
    public class JMenuBarAdmin extends JMenuBar
    {
        private JMenuItem cerrarSesion;
        private JMenuItem preferencias;
        private JMenuItem salir;
        private JMenuItem acercade;
    
        public JMenuBarAdmin()
        {
            cerrarSesion = new JMenuItem("Cerrar Sesion");
            preferencias = new JMenuItem("Preferencias");
            salir = new JMenuItem("Salir");
            acercade = new JMenuItem("Acerca de...");
        }
    
        public void construir()
        {        
            removeAll();
        
            JMenu opciones = new JMenu("Opciones");
            opciones.add(Utiles.crearMenuLookAndFeel(getDuenio()));
            opciones.add(cerrarSesion);
            opciones.add(preferencias);
            opciones.add(salir);
        
            JMenu ayuda = new JMenu("Ayuda");
            ayuda.add(acercade);
        
            add(opciones);
            add(ayuda);
        }
    }
}

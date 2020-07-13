package org.example.catalogovirtual.vista;

import org.example.catalogovirtual.controlador.UsuarioGUIControlador;
import org.example.catalogovirtual.controlador.Utiles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * Interfaz de Usuario Grafica para el usuario.
 * 
 * @author empujesoft
 * @version 2015.06.13
 */
public class UsuarioGUI extends JFrame
{
    private JPanelPrincipal panelPrincipal;
    private static JLabel barraEstado;
    public static final Color NEGRO_ESTANDAR = new Color(51, 51, 51);
    public static final Color VERDE_OSCURO = new Color(0, 150, 60);
    
    public UsuarioGUI(String titulo)
    {
        super(titulo);
        panelPrincipal = null;
        barraEstado = new JLabel(titulo);
    }
    
    public void setPanelPrincipal(JPanelPrincipal panelPrincipal)
    {
        this.panelPrincipal = panelPrincipal;
    }
    
    public JPanelPrincipal getPanelPrincipal()
    {
        return panelPrincipal;
    }
    
    public void abrirVentana()
    {
        construir();
        setVisible(true);
    }
    
    public void cerrarVentana()
    {
        setVisible(false);
        dispose();
    }
    
    public static void actualizarEstado(String estado, Color color)
    {
        barraEstado.setForeground(color);
        barraEstado.setText(estado);
    }
    
    public static void actualizarEstado(String estado)
    {
        barraEstado.setForeground(NEGRO_ESTANDAR);
        barraEstado.setText(estado);
    }
    
    private void construir()
    {
        if(panelPrincipal == null) throw new UnsupportedOperationException();
        panelPrincipal.construir();
        setJMenuBar(panelPrincipal.getBarraMenu());
        
        JPanel contenedor = new JPanel(new BorderLayout(5, 0));
        contenedor.setBorder(new EmptyBorder(3, 3, 3, 3));
        JPanel panelBarraEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBarraEstado.add(barraEstado);
        
        contenedor.add(panelPrincipal, BorderLayout.CENTER);
        contenedor.add(panelBarraEstado, BorderLayout.SOUTH);
        
        setContentPane(contenedor);
        pack();
        Utiles.posicionarVentanaAlCentro(this);
    }
    
    public void setListeners(UsuarioGUIControlador controlador)
    {
        addWindowListener(controlador.new ListenerVentanaCerrando());
    }
}

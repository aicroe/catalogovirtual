package org.example.catalogovirtual.vista.cliente;


import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


/**
 * Popup menu para cuando se preciona el boton derecho del mouse sobre el mostrador.
 * 
 * @author empujesoft
 * @version 2015.07.10
 */
public class JPopupMostrador extends JPopupMenu
{
    private JMenuItem alquilar;
    
    public JPopupMostrador()
    {
        alquilar = new JMenuItem("Alquilar");
    }
    
    public void construir()
    {
        removeAll();
        add(alquilar);
        pack();
    }
    
    public void mostrarPopup(Component invocador, Point punto)
    {
        show(invocador, punto.x, punto.y);
    }
    
    public void aniadirListenerAlquilar(ActionListener listener)
    {
        alquilar.addActionListener(listener);
    }
}

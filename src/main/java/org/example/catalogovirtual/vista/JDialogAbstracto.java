package org.example.catalogovirtual.vista;

import java.awt.Point;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;


/**
 * Abstraccion de nuestros propios JDialog.
 * 
 * @author empujesoft
 * @version 2015.06.26
 */
public abstract class JDialogAbstracto extends JDialog
{
    
    public JDialogAbstracto(JFrame duenio, String titulo)
    {
        super(duenio, titulo, true);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    public void abrirDialogo()
    {
        construir();
        setVisible(true);
    }
    
    public void cerrarDialogo()
    {
        setVisible(false);
        dispose();
    }
    
    protected void posicionarDialogo()
    {
        Window duenio = getOwner();
        Point ubicacion = duenio.getLocation();
        ubicacion.x = ubicacion.x + duenio.getWidth() /2 - getWidth() /2;
        ubicacion.y = ubicacion.y + duenio.getHeight() /2 - getHeight() /2;
        setLocation(ubicacion);
    }
    
    protected abstract void construir();
}

package org.example.catalogovirtual.vista;

import java.awt.Window;
import javax.swing.JMenuBar;

/**
 * JPanel principal del gui. Representa los JPanel principales de una ventana.
 * 
 * @author empujesoft
 * @version 2015.08.02
 */
public abstract class JPanelPrincipal extends JPanelAbstracto
{
    private Window duenio;
    
    public JPanelPrincipal(Window duenio){
        
        this.duenio = duenio;
    }
    
    public Window getDuenio()
    {
        return duenio;
    }
    
    @Override
    public abstract void construir();
    public abstract JMenuBar getBarraMenu();
}

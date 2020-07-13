package org.example.catalogovirtual;

import org.example.catalogovirtual.controlador.UsuarioGUIControlador;
import org.example.catalogovirtual.modelo.Plataforma;
import org.example.catalogovirtual.vista.UsuarioGUI;

/**
 * Clase Main.
 *
 * @author empujesoft
 * @version 2015.08.01
 */
public class CatalogoVirtual
{
    public static final String TITULO = "Catalogo Virtual";
    
    public static void main(String[] args)
    {
        Plataforma plataforma = new Plataforma();
        plataforma.cargarDatos();
        UsuarioGUI usuarioGui = new UsuarioGUI(TITULO);
        UsuarioGUIControlador controlador = 
                new UsuarioGUIControlador(usuarioGui, plataforma);
        usuarioGui.setListeners(controlador);
        controlador.iniciarSesion();
    }
}

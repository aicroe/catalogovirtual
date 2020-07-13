package org.example.catalogovirtual.controlador;

import org.example.catalogovirtual.CatalogoVirtual;
import org.example.catalogovirtual.modelo.Plataforma;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.vista.UsuarioGUI;
import org.example.catalogovirtual.vista.cliente.JDialogCliente;
import org.example.catalogovirtual.vista.cliente.JPanelMostrador;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Vector;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Utiles.
 * 
 * @author empujesoft
 * @version 2015.06.25
 */
public final class Utiles
{
    
    private Utiles()
    {
    }
    
    public static boolean eliminarFilaSiEstaPlaca(
            String placa, Vector<Vector<String>> matriz){
        boolean eliminado = false;
        int i = 0;
        while(!eliminado && i< matriz.size()){
            Vector<String> fila = matriz.get(i);
            if(fila.get(JPanelMostrador.COLUMNA_PLACAS).equals(placa)){
                matriz.remove(i);
                eliminado = true;
            }else{
                i++;
            }
        }
        return eliminado;
    }

    public static boolean eliminarFilaSiEstaSolicitud(Date fechaInicial,
            Date fechaFinal, Auto auto, Vector<Vector> solicitudes) {
        int i = 0;
        while (i < solicitudes.size()) {
            Vector<Object> actual = solicitudes.get(i);
            if (actual.get(JDialogCliente.COLUMNA_FECHA_INICIAL).equals(fechaInicial) && 
                    actual.get(JDialogCliente.COLUMNA_FECHA_FINAL).equals(fechaFinal) && 
                    actual.get(JDialogCliente.COLUMNA_AUTO).equals(auto)) {
                return solicitudes.remove(i) != null;
            }
            i++;
        }
        return false;
    }
        
    public static void posicionarVentanaAlCentro(Window ventana)
    {
        Dimension tamPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        double x = tamPantalla.getWidth()/ 2 - ventana.getWidth()/2;
        double y = tamPantalla.getHeight()/ 2 - ventana.getHeight()/2;
        ventana.setLocation((int)x, (int)y);
    }
    
    public static JMenu crearMenuLookAndFeel(final UsuarioGUI duenio)
    {
        JMenu apariencia = new JMenu("Apariencia");
        UIManager.LookAndFeelInfo[] looksAndFeels = UIManager.getInstalledLookAndFeels();
        for(int i = 0; i< looksAndFeels.length; i++){
            final UIManager.LookAndFeelInfo actual = looksAndFeels[i];
            JMenuItem itemLookAndFeel = new JMenuItem(actual.getName());
            class ListenerItemLookAndFeel implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    try{
                        UIManager.setLookAndFeel(actual.getClassName());
                        SwingUtilities.updateComponentTreeUI(duenio);
                    }catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
            itemLookAndFeel.addActionListener(new ListenerItemLookAndFeel());
            apariencia.add(itemLookAndFeel);
        }
        return apariencia;
    }
    
    public static class ListenerMenuAcercade implements ActionListener
    {
         @Override
         public void actionPerformed(ActionEvent event)
         {
             String titulo = "Acerca de...";
             String mensaje = "Catalogo Virtual 3.0";
             JOptionPane.showMessageDialog(null, mensaje, titulo, 
                     JOptionPane.INFORMATION_MESSAGE);
         }
    }
    
    public static void salir(UsuarioGUI usuarioGui){
        
        WindowEvent evento = new WindowEvent(usuarioGui, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(evento);
    }
    
    public static void cerrarSesion(UsuarioGUI usuarioGui){
        
        salir(usuarioGui);
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(3000);
                    Plataforma plataforma = new Plataforma();
                    plataforma.cargarDatos();
                    UsuarioGUI usuarioGui = new UsuarioGUI(CatalogoVirtual.TITULO);
                    UsuarioGUIControlador controlador = 
                            new UsuarioGUIControlador(usuarioGui, plataforma);
                    usuarioGui.setListeners(controlador);
                    controlador.iniciarSesion();
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }
        });
        hilo.start();
    }
}

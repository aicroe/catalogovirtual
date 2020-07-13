package org.example.catalogovirtual.controlador;

import org.example.catalogovirtual.modelo.Plataforma;
import org.example.catalogovirtual.modelo.cuerpo.Catalogo;
import org.example.catalogovirtual.modelo.cuerpo.RegistroSolicitudes;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.NoExisteTalAutoException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.SolicitudNoValidaException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Filtro;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Ordenador;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import org.example.catalogovirtual.vista.cliente.JDialogAlquilar;
import org.example.catalogovirtual.vista.cliente.JDialogCliente;
import org.example.catalogovirtual.vista.cliente.JDialogConfirmar;
import org.example.catalogovirtual.vista.cliente.JPanelAuto;
import org.example.catalogovirtual.vista.cliente.JPanelCliente;
import org.example.catalogovirtual.vista.cliente.JPanelMostrador;
import org.example.catalogovirtual.vista.cliente.JPopupMostrador;
import org.example.catalogovirtual.vista.UsuarioGUI;
import org.example.catalogovirtual.vista.cliente.JDialogFiltrar;
import org.example.catalogovirtual.vista.cliente.JDialogOrdenar;
import org.example.catalogovirtual.vista.cliente.JDialogVerLista;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 * Controlador del JPanel Cliente.
 *
 * @author empujesoft
 * @version 2015.08.02
 */
public class JPanelClienteControlador
{
    private JPanelCliente panelCliente;
    private JPanelAuto panelAuto;
    private JPanelMostrador panelMostrador;
    private Catalogo catalogo;
    private Cliente cliente;
    private RegistroSolicitudes registro;
    
    public JPanelClienteControlador(
            JPanelCliente panelCliente, Plataforma plataforma, Cliente cliente){
        this.panelCliente = panelCliente;
        this.cliente = cliente;
        panelAuto = panelCliente.getPanelAuto();
        panelMostrador = panelCliente.getPanelMostrador();
        catalogo = plataforma.getCatalogo();
        registro = plataforma.getRegSolicitudes();
    }
    
    public class ListenerVerLista implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JDialogVerLista dialogoVerLista = new JDialogVerLista(panelCliente.getDuenio());
            dialogoVerLista.aniadirListenerVerLista(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event){
                    
                    int categoria = dialogoVerLista.getCategoSelec();
                    dialogoVerLista.cerrarDialogo();
                    ArrayList<Auto> lista = (categoria >= 0)? 
                                 catalogo.listarCategoria(categoria) : catalogo.listarCatalogo();
                    panelMostrador.actualizarInfoTabla(lista);
                    UsuarioGUI.actualizarEstado("Mostrando lista seleccionada.");
                }
            });
            dialogoVerLista.abrirDialogo();
        }
    }
    
    public class ListenerFiltrar implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JDialogFiltrar dialogoFiltrar = new JDialogFiltrar(panelCliente.getDuenio());
            dialogoFiltrar.aniadirListenerBotonFiltrar(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    int categoria = dialogoFiltrar.getCategoSelec();
                    ArrayList<Filtro> filtros = dialogoFiltrar.crearFiltrosSelec();
                    ArrayList<Auto> autos = catalogo.filtrarListaDeCatalogo(filtros, categoria);
                    dialogoFiltrar.cerrarDialogo();
                    panelMostrador.actualizarInfoTabla(autos);
                    UsuarioGUI.actualizarEstado(autos.isEmpty()? 
                            "No se emparejo ningun auto con esos parametros.":
                            "Mostrando autos emparejados.");
                }
            });
            dialogoFiltrar.abrirDialogo();
        }
    }
    
    public class ListenerOrdenar implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JDialogOrdenar dialogoOrdenar = new JDialogOrdenar(panelCliente.getDuenio());
            dialogoOrdenar.aniadirListenerBotonOrdenar(new ActionListener(){       
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    Ordenador ordenador = dialogoOrdenar.getOrdenadorSelec();
                    ArrayList<Auto> ordenados = catalogo.ordenarUltimaLista(ordenador);
                    panelMostrador.actualizarInfoTabla(ordenados);
                    dialogoOrdenar.cerrarDialogo();
                    UsuarioGUI.actualizarEstado(ordenados.isEmpty()? " ": " Lista ordenada.");
                }
            });
            dialogoOrdenar.abrirDialogo();
        }
    }
    
    
    public class KeyListenerMostrador extends KeyAdapter
    {
        @Override
        public void keyReleased(KeyEvent event)
        {
            int tecla = event.getExtendedKeyCode();
            if(tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_ENTER){
                actualizarDetallesSelec();
            }
        }
    }
    
    public class MouseListenerMostrador extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent event)
        {
            if(SwingUtilities.isLeftMouseButton(event)){
                actualizarDetallesSelec();
            }else if(SwingUtilities.isRightMouseButton(event)){
                Point punto = event.getPoint();
                JTable mostrador = panelMostrador.getMostrador();
                int columna = mostrador.columnAtPoint(punto);
                int fila = mostrador.rowAtPoint(punto);
                
                if(columna >= 0 && fila >= 0){
                    mostrador.changeSelection(fila, columna, false, true);
                    if(panelMostrador.getPlacaFilaSelec() != null){
                        JPopupMostrador popupMostrador = new JPopupMostrador();
                        popupMostrador.construir();
                        popupMostrador.aniadirListenerAlquilar(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e)
                            {
                                String placa = panelMostrador.getPlacaFilaSelec();
                                if(placa != null){
                                    alquilarAuto(placa);
                                }
                            }
                        });
                        popupMostrador.mostrarPopup(mostrador, punto);
                    }
                }
            }
        }
    }
    
    private void actualizarDetallesSelec()
    {
         String placa = panelMostrador.getPlacaFilaSelec();
         if(placa != null){
             try{
                 Auto seleccionado = catalogo.seleccionarAuto(placa);
                 panelAuto.actualizarComponentes(seleccionado);
             }catch(NoExisteTalAutoException ex){
                 throw new UnsupportedOperationException();
             }
         }else{
             panelAuto.limpiarComponentes();
         }
         UsuarioGUI.actualizarEstado(" ");
    }
    
    public class ListenerAlquilarAuto implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            String placa = panelAuto.getPlacaAutoActual();
            if(placa != null){
                alquilarAuto(placa);
            }
        }
    }
    
    private void alquilarAuto(String placa)
    {
        UsuarioGUI usuarioGui = panelCliente.getDuenio();
        boolean puedeAlquilarAuto = cliente.puedeAniadirSolicitud();
        Auto seleccionado;
        String mensaje;
        try{
            seleccionado = catalogo.seleccionarAuto(placa);
        }catch(NoExisteTalAutoException ex){
            throw new UnsupportedOperationException();
        }
        if(puedeAlquilarAuto && seleccionado.estaDisponible()){
            JDialogAlquilar dialogoAlquilar = new JDialogAlquilar(usuarioGui);
            dialogoAlquilar.construir();
            dialogoAlquilar.actualizarComponentes(cliente, seleccionado);
            dialogoAlquilar.aniadirListenerAceptar(
                    new ListenerAceptarAlquiler(dialogoAlquilar));
            dialogoAlquilar.setVisible(true);
            
            return;
        }else if(!puedeAlquilarAuto){
            mensaje = "No puede alquilar mas Autos";
        }else{
            mensaje = "Auto no Disponible";
        }
        JOptionPane.showMessageDialog(usuarioGui, 
                                        mensaje, 
                                        "Alquilar", 
                                        JOptionPane.INFORMATION_MESSAGE);
    }
    
    private class ListenerAceptarAlquiler implements ActionListener
    {
        private JDialogAlquilar dialogoAlquilar;
        
        private ListenerAceptarAlquiler(JDialogAlquilar dialogoAlquilar)
        {
            this.dialogoAlquilar = dialogoAlquilar;
        }
        
        @Override
        public void actionPerformed(ActionEvent event)
        {
            try{
                Auto seleccionado = catalogo.seleccionarAuto(dialogoAlquilar.getPlacaAuto());
                Solicitud solicitud = new Solicitud(seleccionado, cliente);
                dialogoAlquilar.completarSolicitud(solicitud);
                Solicitudes.verificarSolParaInsertar(solicitud);
                dialogoAlquilar.cerrarDialogo();
                
                JDialogConfirmar dialogoConfirmar = new JDialogConfirmar(panelCliente.getDuenio());
                dialogoConfirmar.actualizarComponentes(solicitud);
                dialogoConfirmar.aniadirListenerConfirmar(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        try{
                            registro.insertarSolicitud(solicitud);
                            cliente.aniadirSolicitud(solicitud);
                            dialogoConfirmar.cerrarDialogo();
                            panelAuto.actualizarComponentes(seleccionado);
                            panelMostrador.actualizarInfoTabla(catalogo.getUltimaLista());
                        }catch(SolicitudNoValidaException ex){
                            throw new UnsupportedOperationException(ex);
                        }
                    }
                });
                dialogoConfirmar.abrirDialogo();
            }catch(SolicitudNoValidaException ex){
                dialogoAlquilar.mostrarMensaje(ex.getMessage(), Color.RED);
            }catch(NoExisteTalAutoException ex){
                throw new UnsupportedOperationException();
            }
        }
    }
    
    public class ListenerMenuUsuario implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            JDialogCliente dialogoCliente = new JDialogCliente(panelCliente.getDuenio());
            JDialogClienteControlador controlador = new JDialogClienteControlador(
                    dialogoCliente, registro, catalogo, cliente);
            dialogoCliente.setListeners(controlador);
            dialogoCliente.addWindowListener(new ListenerCerrandoDialogo());
            dialogoCliente.actualizarComponentes(cliente);
            dialogoCliente.abrirDialogo();
        }
    }
    
    private class ListenerCerrandoDialogo extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent event)
        {
            String placaEnPanel = panelAuto.getPlacaAutoActual();
            try{
                Auto autoEnPanel = catalogo.seleccionarAuto(placaEnPanel);
                panelAuto.actualizarComponentes(autoEnPanel);
            }catch(NoExisteTalAutoException ex){
            }
            panelMostrador.actualizarInfoTabla(catalogo.getUltimaLista());
        }
    }
    
    public class ListenerMenuSalir implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            Utiles.salir(panelCliente.getDuenio());
        }
    }
}

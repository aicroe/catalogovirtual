package org.example.catalogovirtual.vista.cliente;

import org.example.catalogovirtual.controlador.JDialogClienteControlador;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import org.example.catalogovirtual.vista.ImageView;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import org.example.catalogovirtual.vista.ManejadorImagen;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


/**
 * JDialog dialogo que muestra la informacion basica del usuario.
 * 
 * @author empujesoft
 * @version 2015.06.22
 */
public class JDialogCliente extends JDialogAbstracto implements ActionListener,
        KeyListener, MouseListener{
    
    private ImageView imagen;
    private JLabel nombre;
    private JLabel ci;
    private JLabel telefono;
    private JLabel licencia;
    private JLabel direccion;
    private JLabel lugarTrabajo;
    private JLabel ocupacion;
    private JButton modificarPerfil;
    private JButton cerrarSesion;
    private JTable solicitudes;
    private JButton eliminar;
    private JButton aceptar;
    
    public static final int ALTO_CELDA_ESTANDAR = 6;
    public static final int COLUMNA_FECHA_INICIAL = 0;
    public static final int COLUMNA_FECHA_FINAL = 1;
    public static final int COLUMNA_AUTO = 2;
    public static final int COLUMNA_ESTADO = 4;
    
    public static final String[] CABECERAS_SOLICITUDES = new String[] 
            {"Fecha Inicial", "Fecha Final", "Auto", "Precio Total", "Estado"};
    
    public JDialogCliente(UsuarioGUI duenio)
    {
        super(duenio, "Cliente");
        
        imagen = new ImageView(ManejadorImagen.MAX_TAMANIO_IMG_USUARIO, 
                ManejadorImagen.MAX_TAMANIO_IMG_USUARIO);
        ci = new JLabel(" ", JLabel.LEFT);
        nombre = new JLabel(" ", JLabel.CENTER);
        telefono = new JLabel(" ", JLabel.CENTER);
        licencia = new JLabel(" ", JLabel.CENTER);
        direccion = new JLabel(" ", JLabel.CENTER);
        lugarTrabajo = new JLabel(" ", JLabel.CENTER);
        ocupacion = new JLabel(" ", JLabel.CENTER);
        modificarPerfil = new JButton("Modificar Perfil");
        cerrarSesion = new JButton("Cerrar Sesion");
        solicitudes = new JTable(new JTableModelNoEditable());
        eliminar = new JButton("Eliminar");
        aceptar = new JButton("Aceptar");
    }
    
    @Override
    protected void construir()
    {
        JPanel contenedor = new JPanel(new BorderLayout(5, 2));
        contenedor.setBorder(new EmptyBorder(18, 15, 15, 15));
        
        JPanel panelNorte = new JPanel();
        JPanel panelCentral = new JPanel();
        JPanel panelSur = new JPanel();
        
        construirPanelNorte(panelNorte);
        construirPanelCentral(panelCentral);
        
        panelSur.add(aceptar);
        contenedor.add(panelNorte, BorderLayout.NORTH);
        contenedor.add(panelCentral, BorderLayout.CENTER);
        contenedor.add(panelSur, BorderLayout.SOUTH);
        
        setContentPane(contenedor);
        pack();
        configurarComponentes();
        posicionarDialogo();
    }
    
    private void construirPanelNorte(JPanel panelNorte)
    {
        panelNorte.setLayout(new BorderLayout(30, 0));
        
        JPanel panelSupIzq = new JPanel();
        JPanel panelInterior = new JPanel();
        panelInterior.setLayout(new BoxLayout(panelInterior, BoxLayout.Y_AXIS));
        JPanel panelImagenExt = new JPanel();
        JPanel panelImagen = new JPanel();
        panelImagen.setBorder(new CompoundBorder(
                new EtchedBorder(), new EmptyBorder(5, 5, 5, 5)));
        JPanel panelCi = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        panelCi.setBorder(new CompoundBorder(
                new EmptyBorder(0, 5, 0, 5), new EtchedBorder()));
        
        panelCi.add(crearLabel("CI : ", 25, 25));
        panelCi.add(ci);
        panelImagen.add(imagen);
        panelImagenExt.add(panelImagen);
        panelInterior.add(panelCi);
        panelInterior.add(panelImagenExt);
        panelInterior.add(crearPanelInterior(cerrarSesion));
        panelSupIzq.add(panelInterior);
        
        JPanel panelDatos = new JPanel(new BorderLayout());
        panelDatos.setBorder(new CompoundBorder(
                new TitledBorder(new EtchedBorder(), "Datos"), 
                new EmptyBorder(5, 5, 5, 5)));
        construirPanelDatos(panelDatos);
        
        panelNorte.add(panelSupIzq, BorderLayout.WEST);
        panelNorte.add(panelDatos, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelInterior(JComponent componente)
    {
        JPanel panelInterior = new JPanel();
        panelInterior.add(componente);
        return panelInterior;
    }
    
    private void construirPanelDatos(JPanel panelDatos)
    {
        JPanel panelMarcadores = new JPanel(new GridLayout(6, 1, 2, 2));
        panelMarcadores.add(crearLabel("Nombre", 170, 25));
        panelMarcadores.add(crearLabel("Telefono", 170, 25));
        panelMarcadores.add(crearLabel("Tipo de Licencia", 170, 25));
        panelMarcadores.add(crearLabel("Direccion", 170, 25));
        panelMarcadores.add(crearLabel("Lugar de Trabajo", 170, 25));
        panelMarcadores.add(crearLabel("Ocupacion", 170, 25));
        
        JPanel panelCampos = new JPanel(new GridLayout(6, 1, 2, 2));
        panelCampos.add(nombre);
        panelCampos.add(telefono);
        panelCampos.add(licencia);
        panelCampos.add(direccion);
        panelCampos.add(lugarTrabajo);
        panelCampos.add(ocupacion);
        
        JPanel panelModifPerfil = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelModifPerfil.add(modificarPerfil);
        
        panelDatos.add(panelMarcadores, BorderLayout.WEST);
        panelDatos.add(panelCampos, BorderLayout.CENTER);
        panelDatos.add(panelModifPerfil, BorderLayout.SOUTH);
        
    }
    
    private JLabel crearLabel(String texto, int ancho, int alto)
    {
        JLabel label = new JLabel(texto);
        label.setPreferredSize(new Dimension(ancho, alto));
        return label;
    }
    
    private void construirPanelCentral(JPanel panelSur)
    {
        panelSur.setLayout(new BorderLayout(0, 2));
        panelSur.setBorder(new CompoundBorder(
                new TitledBorder(new EtchedBorder(), "Solicitudes"), 
                new EmptyBorder(5, 5, 5, 5)));
        
        DefaultTableModel modeloTabla = (DefaultTableModel) solicitudes.getModel();
        modeloTabla.setColumnIdentifiers(CABECERAS_SOLICITUDES);
        modeloTabla.setRowCount(ALTO_CELDA_ESTANDAR);
        solicitudes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        solicitudes.setRowHeight(20);
        solicitudes.setDragEnabled(false);
        solicitudes.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane panelTabla = new JScrollPane(solicitudes);
        panelTabla.getViewport().setPreferredSize(new Dimension(550, 120));
        Box panelEliminar = Box.createHorizontalBox();
        panelEliminar.add(eliminar);
        
        panelSur.add(panelTabla, BorderLayout.CENTER);
        panelSur.add(panelEliminar, BorderLayout.SOUTH);
    }
    
    private void configurarComponentes()
    {
        eliminar.setEnabled(false);
        ci.setPreferredSize(new Dimension(70, 25));
        
        nombre.setOpaque(true);
        telefono.setOpaque(true);
        licencia.setOpaque(true);
        direccion.setOpaque(true);
        lugarTrabajo.setOpaque(true);
        ocupacion.setOpaque(true);
        
        nombre.setBackground(Color.WHITE);
        telefono.setBackground(Color.WHITE);
        licencia.setBackground(Color.WHITE);
        direccion.setBackground(Color.WHITE);
        lugarTrabajo.setBackground(Color.WHITE);
        ocupacion.setBackground(Color.WHITE);
        
        nombre.setBorder(new EtchedBorder());
        telefono.setBorder(new EtchedBorder());
        licencia.setBorder(new EtchedBorder());
        direccion.setBorder(new EtchedBorder());
        lugarTrabajo.setBorder(new EtchedBorder());
        ocupacion.setBorder(new EtchedBorder());
        
        aceptar.addActionListener(this);
        solicitudes.addMouseListener(this);
        solicitudes.addKeyListener(this);
    }
    
    public void actualizarComponentes(Cliente cliente)
    {
        ci.setText(String.valueOf(cliente.getCi()));
        imagen.setImage(ManejadorImagen.leerImagen(ManejadorImagen.FOLDER_USUARIOS,
                cliente.getLogin(), ManejadorImagen.MAX_TAMANIO_IMG_USUARIO, 
                ManejadorImagen.MAX_TAMANIO_IMG_USUARIO, 
                ManejadorImagen.IMAGEN_USUARIO_DEFECTO));
        nombre.setText(cliente.getNombre());
        telefono.setText(String.valueOf(cliente.getTelefono()));
        licencia.setText(String.valueOf(cliente.getTipoLicencia()));
        direccion.setText(cliente.getDireccion());
        lugarTrabajo.setText(cliente.getLugarTrabajo());
        ocupacion.setText(cliente.getOcupacion());
        actualizarSolicitudes(cliente.getSolicitudes());
    }
    
    private void actualizarSolicitudes(ArrayList<Solicitud> solicitudes)
    {
        Object[][] informacion = new Object[solicitudes.size()][0];
        for(int i = 0; i< solicitudes.size(); i++){
            Solicitud solicitud = solicitudes.get(i);
            Object[] infoSol = new Object[CABECERAS_SOLICITUDES.length];
            infoSol[0] = solicitud.getFechaInicial();
            infoSol[1] = solicitud.getFechaFinal();
            infoSol[2] = solicitud.getAuto();
            infoSol[3] = solicitud.getPrecioTotal();
            infoSol[4] = solicitud.getEstado();
            informacion[i] = infoSol;
        }
        DefaultTableModel modelo = (DefaultTableModel) this.solicitudes.getModel();
        modelo.setDataVector(informacion, CABECERAS_SOLICITUDES);
        modelo.setRowCount((informacion.length > ALTO_CELDA_ESTANDAR)? 
                                    informacion.length : ALTO_CELDA_ESTANDAR);
    }
    
    public void setInfoSolicitudes(Vector<Vector> informacion)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) solicitudes.getModel();
        List cabeceras = Arrays.asList(CABECERAS_SOLICITUDES);
        modeloTabla.setDataVector(informacion, new Vector(cabeceras));
        modeloTabla.setRowCount((informacion.size() > ALTO_CELDA_ESTANDAR)? 
                                    informacion.size() : ALTO_CELDA_ESTANDAR);
    }
    
    public Vector<Vector> getInfoSolicitudes()
    {
        return ((DefaultTableModel)solicitudes.getModel()).getDataVector();
    }
    
    public void setListeners(JDialogClienteControlador controlador)
    {
        cerrarSesion.addActionListener(controlador.new ListenerCerrarSesion());
        eliminar.addActionListener(controlador.new ListenerBotonEliminar());
        modificarPerfil.addActionListener(controlador.new ListenerModifPerfil());
    }
    
    public Object getDatoFilaSelec(int columna)
    {
        int filaSelec = solicitudes.getSelectedRow();
        if(filaSelec >= 0){
            return solicitudes.getModel().getValueAt(filaSelec, columna);
        }
        return null;
    }
    
    private class JTableModelNoEditable extends DefaultTableModel
    {
        @Override
        public boolean isCellEditable(int fila, int columna)
        {
            return false;
        }
    }
    
    private void verSiHabilitarEliminar()
    {
        int filaSelec = solicitudes.getSelectedRow();
        if(filaSelec >= 0){
            String estado = (String) solicitudes.getModel().getValueAt(
                    filaSelec, COLUMNA_ESTADO);
            if(estado != null && ((estado.equals(Solicitudes.ESTADO_RESERVA) ||
                    estado.equals(Solicitudes.ESTADO_TERMINADO)) || 
                    estado.equals(Solicitudes.ESTADO_ELIMINADO))){
                eliminar.setEnabled(true);
                return;
            }
        }
        eliminar.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        cerrarDialogo();
        WindowEvent eventoCierre = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(eventoCierre);
    }

    @Override
    public void mouseReleased(MouseEvent event)
    {
        if(SwingUtilities.isLeftMouseButton(event)){
            verSiHabilitarEliminar();
        }
    }

    @Override
    public void keyReleased(KeyEvent event)
    {
        int tecla = event.getExtendedKeyCode();
        if(tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_DOWN || 
                tecla == KeyEvent.VK_ENTER){
            verSiHabilitarEliminar();
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
}

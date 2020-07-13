/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.vista.admin;

import org.example.catalogovirtual.controlador.JPanelAdminControlador;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Buscador;
import org.example.catalogovirtual.modelo.cuerpo.utiles.BuscadorCi;
import org.example.catalogovirtual.modelo.cuerpo.utiles.BuscadorPlaca;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER-UNO
 */
public class JPanelSolicitud extends JPanel implements 
        MouseListener, KeyListener, FocusListener{
    
    private JPanel buscador;
    private JButton buscar;
    private JTable tabla;
    private JPanel opciones;
    private JButton abrir;
    private JComboBox verLista;
    private JTextField ci;
    private JTextField placa;
    
    public static final int TAMANIO_ESTANDAR_TABLA = 25;
    public static final int COLUMNA_ESTADO = 0;
    public static final int COLUMNA_AUTO = 2;
    public static final int COLUMNA_FECHA_INICIO = 3;
    public static final int COLUMNA_FECHA_FINAL = 4;
    public static final String[] NOMBRES_COLUMNAS = 
                {"Estado", "Cliente", "Auto", "Fecha de Inicio", "Fecha Final"}; 
    
    public JPanelSolicitud(){
        buscador = new JPanel();
        opciones = new JPanel();
        tabla = new JTable();
        ci = new JTextField(18);
        placa = new JTextField(18);
        buscar = new JButton("Buscar");
        abrir = new JButton("Abrir");
        verLista = new JComboBox(Solicitudes.TIPOS_NOMBRES);
    }
    
    public void construir(){
        removeAll();
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5 ,5), new EtchedBorder()));
        construirBuscador(this);
        construirTabla(this);
        construirOpciones(this);
        configurarComponentes();
    }
    
    private void construirBuscador(JPanel contenedor){
        buscador.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel titulo1 = new JLabel("C.I. :");
        buscador.add(titulo1);
        buscador.add(ci);
        JLabel titulo2 = new JLabel("Placa :");
        buscador.add(titulo2);
        buscador.add(placa);
        buscador.add(buscar);
        contenedor.add(buscador, BorderLayout.NORTH);
    }
    
    private void construirTabla(JPanel contenedor)
    {
        JPanel panelMostrador = new JPanel(new GridLayout(1, 1));
        
        DefaultTableModel tablaModelo = new TableModelNoEditable();
        tablaModelo.setColumnIdentifiers(NOMBRES_COLUMNAS);
        tablaModelo.setRowCount(TAMANIO_ESTANDAR_TABLA);
        tabla.setModel(tablaModelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(25);
        tabla.setDragEnabled(false);
        tabla.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane panelCorrediso = new JScrollPane(tabla);
        panelMostrador.add(panelCorrediso);
        contenedor.add(panelMostrador, BorderLayout.CENTER);
    }
    
    private void construirOpciones(JPanel contenedor){
        opciones.setLayout(new FlowLayout(FlowLayout.LEFT));
        opciones.add(abrir);
        opciones.add(verLista);
        contenedor.add(opciones, BorderLayout.SOUTH);
    }
    
    private void configurarComponentes(){
        abrir.setEnabled(false);
        tabla.addMouseListener(this);
        tabla.addFocusListener(this);
        tabla.addKeyListener(this);
    }
    
    public void actualizarInfoTabla(ArrayList<Solicitud> solicitudes){
        Object[][] informacion = new Object[solicitudes.size()][5];
        for(int i = 0; i< solicitudes.size(); i++){
            Solicitud solicitud = solicitudes.get(i);
            Object[] infoSol = new Object[5];
            infoSol[0] = solicitud.getEstado();
            infoSol[1] = solicitud.getCliente().getNombre();
            infoSol[2] = solicitud.getAuto();
            infoSol[3] = solicitud.getFechaInicial();
            infoSol[4] = solicitud.getFechaFinal();
            informacion[i] = infoSol;
        }
        DefaultTableModel modelo = ((DefaultTableModel)tabla.getModel());
        modelo.setDataVector(informacion, NOMBRES_COLUMNAS);
        modelo.setRowCount((informacion.length > TAMANIO_ESTANDAR_TABLA)? 
                                    informacion.length : TAMANIO_ESTANDAR_TABLA);
    }
    
    public ArrayList<Buscador> contruirBuscadores(){
        
        ArrayList<Buscador> buscadores = new ArrayList<>();
        buscadores.add(new BuscadorCi(ci.getText()));
        buscadores.add(new BuscadorPlaca(placa.getText()));
        return buscadores;
    }
    
    public void limpiarCampos(){
        
        ci.setText(" ");
        placa.setText(" ");
    }
    
    public int getTipoSelect(){
        
        return Solicitudes.tipoPorNombre((String) verLista.getSelectedItem());
    }
    
    public void setListeners(JPanelAdminControlador controlador){
        
        verLista.addActionListener(controlador.new ListenerVerLista());
        abrir.addActionListener(controlador.new ListenerAbrirSolicitud());
        buscar.addActionListener(controlador.new ListenerBuscar());
    }
    
    private class TableModelNoEditable extends DefaultTableModel
    {
        @Override
        public boolean isCellEditable(int fil, int col)
        {
            return false;
        }
    }
    
    public Object getDatoFilaSelec(int columna)
    {
        int fila = tabla.getSelectedRow();
        if(fila >= 0){
            return tabla.getValueAt(fila, columna);
        }
        return null;
    }
    
    public void habilitarBotonAbrir(boolean habilitar){
        
        abrir.setEnabled(habilitar);
    }

    @Override
    public void focusLost(FocusEvent e){
        
        Object nuevoEnfocado = e.getOppositeComponent();
        if(nuevoEnfocado != abrir){
            abrir.setEnabled(false);
        }
    }
    
    @Override
    public void focusGained(FocusEvent e){
    }

    @Override
    public void mouseReleased(MouseEvent event){
        
        if(SwingUtilities.isLeftMouseButton(event)){
            Point punto = event.getPoint();
            int columna = tabla.columnAtPoint(punto);
            int fila = tabla.rowAtPoint(punto);
                
            if(columna >= 0 && fila >= 0){
                abrir.setEnabled(getDatoFilaSelec(0) != null);
            }
        }
        UsuarioGUI.actualizarEstado(" ");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int tecla = e.getExtendedKeyCode();
        if(tecla == KeyEvent.VK_ENTER || tecla == KeyEvent.VK_UP ||
                tecla == KeyEvent.VK_DOWN){
            abrir.setEnabled(getDatoFilaSelec(0) != null);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
    }

    @Override
    public void mousePressed(MouseEvent e){
    }

    @Override
    public void mouseEntered(MouseEvent e){
    }

    @Override
    public void mouseExited(MouseEvent e){
    }

    @Override
    public void keyTyped(KeyEvent e){
    }

    @Override
    public void keyPressed(KeyEvent e){
    }
}

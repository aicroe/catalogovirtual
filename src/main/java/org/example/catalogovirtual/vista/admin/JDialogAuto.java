/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.vista.admin;

import org.example.catalogovirtual.controlador.JDialogAutoControlador;
import org.example.catalogovirtual.controlador.JPanelAdminControlador;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.vista.ImageView;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import org.example.catalogovirtual.vista.JTextNumber;
import org.example.catalogovirtual.vista.ManejadorImagen;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author USER-UNO
 */
public class JDialogAuto extends JDialogAbstracto implements ActionListener, KeyListener{
    private JPanel panelNorte;
    private JPanel panelSur;
    private JPanel panelEste;
    private JPanel panelOeste;
    private JButton modificar;
    private JButton confirmar;
    private JButton eliminar;
    private JButton aceptar;
    private JTextField nombre;
    private JTextField placa;
    private JTextNumber modelo;
    private JTextNumber precio;
    private JTextNumber nroPasajeros;
    private JComboBox tipoDeCaja;
    private JTextNumber garantia;
    private JTextField dirImagen;
    private JButton buscar;
    private JLabel barraMensaje;
    private ImageView imagenAuto;
    private JLabel categoria;
    private Auto autoModificado;
    private String placaInicial;
    
    private final String[] TIPO_DE_CAJA = {"Automatico", "Manual"};
    
    public JDialogAuto(JFrame duenio){
        super(duenio, "Datos del vehiculo");
        panelNorte = new JPanel();
        panelSur = new JPanel();
        panelEste = new JPanel();
        panelOeste = new JPanel();
        categoria = new JLabel();
        dirImagen = new JTextField(8);
        buscar = new JButton("...");
        imagenAuto = new ImageView(ManejadorImagen.MAX_TAMANIO_IMG_AUTO, 
                ManejadorImagen.MAX_TAMANIO_IMG_AUTO);
        barraMensaje = new JLabel();
    }
    
    @Override
    public void construir(){
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(15, 15, 10, 15));
        construirPanelNorte(contenedor);
        construirPanelSur(contenedor);
        setContentPane(contenedor);
        pack();
        posicionarDialogo();
        configurarComponentes();
        ListenerCerrarVentana listener = new ListenerCerrarVentana();
        listener.setDialogo(this);
        this.addWindowListener(listener);
    }
    
    private void construirPanelNorte(JPanel contenedor){
        panelNorte.setLayout(new BorderLayout());
        construirPanelEste(panelNorte);
        construirPanelOeste(panelNorte);
        contenedor.add(panelNorte, BorderLayout.NORTH);
    }
    
    private void construirPanelSur(JPanel contenedor){
        panelSur.setLayout(new GridLayout(2,1));
        
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
        modificar = new JButton("Modificar");
        confirmar = new JButton("Confirmar");
        eliminar = new JButton("Eliminar");
        aceptar = new JButton("Aceptar");
        panelBoton.add(modificar);
        panelBoton.add(confirmar);
        panelBoton.add(aceptar);
        panelBoton.add(eliminar);
        
        JPanel panelMensaje = new JPanel();
        panelMensaje.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelMensaje.add(barraMensaje);
        
        panelSur.add(panelBoton);
        panelSur.add(panelMensaje);
        contenedor.add(panelSur);
    }
    
    private void construirPanelEste(JPanel contenedor){
        panelEste.setLayout(new BorderLayout());
        panelEste.setBorder(new EtchedBorder());
        Box panelVisorImagen = Box.createVerticalBox();
        
        JPanel panelNomCat = new JPanel();
        ((FlowLayout)panelNomCat.getLayout()).setAlignment(FlowLayout.LEFT);
        panelNomCat.add(new JLabel("Categoria: "));
        panelNomCat.add(categoria);
        
        JPanel panelImagen = new JPanel();
        panelImagen.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panelImagen.add(imagenAuto);
                
        panelVisorImagen.add(panelNomCat);
        panelVisorImagen.add(panelImagen);
        
        JPanel buscador = new JPanel(new FlowLayout());
        buscador.add(dirImagen);
        buscador.add(buscar);
        
        panelEste.add(panelVisorImagen, BorderLayout.CENTER);
        panelEste.add(buscador, BorderLayout.SOUTH);
        contenedor.add(panelEste, BorderLayout.EAST);
        
    }
    
    private void construirPanelOeste(JPanel contenedor){
        panelOeste.setLayout(new GridLayout(8, 2, 5, 2));
        nombre = new JTextField();
        placa = new JTextField();
        modelo = new JTextNumber(10);
        precio = new JTextNumber(10, 8, true);
        nroPasajeros = new JTextNumber(10);
        tipoDeCaja = new JComboBox(TIPO_DE_CAJA);
        garantia = new JTextNumber(10, 8, true);
        completarPanelOeste();
        contenedor.add(panelOeste);
    }
    
    private void completarPanelOeste(){
        aniadirComponente(nombre, "Nombre:");
        aniadirComponente(placa, "Placa:");
        aniadirComponente(modelo, "Modelo:");
        aniadirComponente(precio, "Precio p/dia:");
        aniadirComponente(nroPasajeros, "Nro. Pasajeros:");
        aniadirComponente(tipoDeCaja, "Tipo de Caja:");
        aniadirComponente(garantia, "Garantia:");
    }
    
    private void aniadirComponente(Component comp, String titulo){
        JLabel etiqueta = new JLabel(titulo);
        panelOeste.add(etiqueta);
        panelOeste.add(comp);
        comp.setBackground(Color.WHITE);
        if(comp instanceof JTextField) ((JTextField)comp).setOpaque(true);
        if(comp instanceof JComboBox) ((JComboBox)comp).setOpaque(true);
    }
    
    private void configurarComponentes(){
        setModificable(false);
        garantia.setEditable(false);
        confirmar.setEnabled(false);
        
        nombre.addKeyListener(this);
        placa.addKeyListener(this);
        modelo.addKeyListener(this);
        precio.addKeyListener(this);
        nroPasajeros.addKeyListener(this);
        buscar.addActionListener(this);
    }
    
    public void actualizar(Auto auto){
        nombre.setText(auto.getNombre());
        placa.setText(auto.getPlaca());
        modelo.setText(((Integer)auto.getModelo()).toString());
        precio.setText(((Double)auto.getPrecioPorDia()).toString());
        nroPasajeros.setText(((Integer)auto.getNumeroDePasajeros()).toString());
        int i = (auto.getTipoDeCaja())? 0 : 1;
        tipoDeCaja.setSelectedIndex(i);
        garantia.setText(((Double)auto.getGarantia()).toString());
        categoria.setText(auto.getNombreCategoria());
        placaInicial = auto.getPlaca();
        imagenAuto.setImage(ManejadorImagen.leerImagen(
                ManejadorImagen.FOLDER_AUTOS, auto.getPlaca(), 
                ManejadorImagen.MAX_TAMANIO_IMG_AUTO, 
                ManejadorImagen.MAX_TAMANIO_IMG_AUTO, 
                ManejadorImagen.IMAGEN_AUTO_DEFECTO));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object boton = ae.getSource();
        if(boton == buscar){
            BufferedImage unaImagen = ManejadorImagen.buscarImagen(new Buscador());
            imagenAuto.setImage(unaImagen);}
    }
    
    public class PerfilAuto{
        public String nombre;
        public String placa;
        public int modelo;
        public double precioPorDia;
        public int nroPasajeros;
        public boolean tipoDeCaja;
        public Garantia garantia;
        public BufferedImage imagen; 
    }
    
    public PerfilAuto getPerfil(){
        if(confirmar.isEnabled()){
            PerfilAuto perfil = new PerfilAuto();
            perfil.nombre = nombre.getText();
            perfil.placa = placa.getText();
            perfil.modelo = modelo.getInt();
            perfil.precioPorDia = (double) precio.getDouble();
            perfil.nroPasajeros = nroPasajeros.getInt();
            perfil.imagen = (BufferedImage) imagenAuto.getImage();
            return perfil;
        }
        throw new UnsupportedOperationException();
    }

    private class Buscador implements ManejadorImagen.BuscarImagenListener
    {
        @Override
        public void mostrarMensaje(String mensaje, Color color)
        {
            actualizarBarraMensaje(mensaje, color);
        }
        
        @Override
        public void mostrarNombreImagen(String nombre)
        {
            dirImagen.setText(nombre);
            actualizarBarraMensaje("", null);
        }
        
        @Override
        public int getAltoMaximo()
        {
            return ManejadorImagen.MAX_TAMANIO_IMG_AUTO;
        }
        
        @Override
        public int getAnchoMaximo()
        {
            return ManejadorImagen.MAX_TAMANIO_IMG_AUTO;
        }
    }
    
    public boolean sonCamposValidos()
    {
        return nombre.getText().length() >= 4 &&
               placa.getText().length() >= 4 &&
               modelo.getText().length() >= 4 &&
               precio.getText().length() >= 1 &&
               nroPasajeros.getText().length() >= 1;
    }
    
    public void actualizarBarraMensaje(String mensaje, Color color)
    {
        barraMensaje.setForeground(color);
        barraMensaje.setText(mensaje);
        pack();
    }
    
    /**
     * Método que permite habilitar los campos de datos para su modificación
     * @param b true si se quiere modificar los campos, false si no.
     */
    public void setModificable(boolean b){
        nombre.setEditable(b);
        placa.setEditable(b);
        modelo.setEditable(b);
        precio.setEditable(b);
        nroPasajeros.setEditable(b);
        tipoDeCaja.setEditable(b);
        dirImagen.setEditable(b);
        modificar.setEnabled(!b);
        confirmar.setEnabled(b);
        aceptar.setEnabled(!b);
        buscar.setEnabled(b);
    }
    
    public boolean esModificable(){
        return !modificar.isEnabled();
    }
    
    public String getCategoria(){
        return categoria.getText();
    }
    
    public Auto getAutoModificado(){
        return autoModificado;
    }
    
    public void setAutoModificado(Auto auto){
        autoModificado = auto;
    }
    
    public String getPlacaInicial(){
        return placaInicial;
    }
    
    public void setListeners(JDialogAutoControlador controlador){
        modificar.addActionListener(controlador.new ListenerBotonModificar());
        confirmar.addActionListener(controlador.new ListenerBotonConfirmar());
    }
    
    public void addListenerBotonAceptar(JPanelAdminControlador controlador){
        aceptar.addActionListener(controlador.addListenerAceptarDialogAuto(this));
    }
    
    public void addListenerBotonEliminar(JPanelAdminControlador controlador){
        eliminar.addActionListener(controlador.addListenerEliminarDialogAuto(this));
    }
    
    public void addListenerConfirmarEliminacion(ActionListener listener){
        eliminar.addActionListener(listener);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        aceptar.setEnabled(sonCamposValidos());
        actualizarBarraMensaje("", null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    public class ListenerCerrarVentana extends WindowAdapter{
        private JDialogAuto dialogo;
        private void setDialogo(JDialogAuto dialogo){
            this.dialogo = dialogo;
        }

        @Override
        public void windowClosing(WindowEvent we) {
            if(dialogo.esModificable()){
                dialogo.actualizarBarraMensaje("Debe guardar los cambios antes de salir",
                        Color.RED);
                dialogo.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
            else dialogo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }
}
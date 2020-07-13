package org.example.catalogovirtual.vista.admin;

import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import org.example.catalogovirtual.vista.JTextNumber;
import org.example.catalogovirtual.vista.ManejadorImagen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;


/**
 * JDialog que se muestra cunado se quiere insertar un nuevo auto al catalogo.
 * 
 * @author empujesoft
 * @version 2015.06.24
 */
public class JDialogInsertar extends JDialogAbstracto implements ActionListener
{
    private JComboBox categoria;
    private JTextField nombre;
    private JTextField placa;
    private JTextNumber modelo;
    private JTextNumber precio;
    private JTextNumber nroPasajeros;
    private JComboBox tipoDeCaja;
    private JTextField dirImagen;
    private JButton botonBuscar;
    private JButton insertar;
    private JLabel barraMensaje;
    private BufferedImage imagenAuto;
    
    private final String[] CATEGORIAS = {"Automovil", "Camioneta", "Vagoneta", "Limosina"};
    private final String[] TIPO_DE_CAJA = {"Automatico", "Manual"};
    
    public JDialogInsertar(JFrame duenio)
    {
        super(duenio, "Insertar");
        
        categoria = new JComboBox(CATEGORIAS);
        nombre = new JTextField();
        placa = new JTextField();
        modelo = new JTextNumber(5, 4, false);
        precio = new JTextNumber(5, JTextNumber.CANT_CARACTERES_DEFECTO, true);
        nroPasajeros = new JTextNumber(5, 2, false);
        tipoDeCaja = new JComboBox(TIPO_DE_CAJA);
        dirImagen = new JTextField();
        botonBuscar = new JButton("...");
        insertar = new JButton("Insertar");
        barraMensaje = new JLabel("");
        imagenAuto = null;
    }
    
    @Override
    protected void construir()
    {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(15, 15, 10, 15));
        
        JPanel panelCentral = new JPanel();
        JPanel panelSur = new JPanel();
        construirPanelCentral(panelCentral);
        construirPanelSur(panelSur);
        
        contenedor.add(panelCentral, BorderLayout.CENTER);
        contenedor.add(panelSur, BorderLayout.SOUTH);
        
        setContentPane(contenedor);
        pack();
        posicionarDialogo();
        configurarComponentes();
    }
    
    public Auto fabricarAutoInsertado()
    {
        Auto auto = Autos.autoPorCategoria((String)categoria.getSelectedItem());
        auto.setNombre(nombre.getText());
        auto.setPlaca(placa.getText().trim().toUpperCase());
        auto.setModelo(modelo.getInt());
        auto.setPrecioPorDia(precio.getDouble());
        auto.setNumeroDePasajeros(nroPasajeros.getInt());
        auto.setTipoDeCaja(tipoDeCaja.getSelectedItem().equals("Automatico"));
        if(imagenAuto != null)
            ManejadorImagen.escribirImagen(imagenAuto, 
                    ManejadorImagen.FOLDER_AUTOS, auto.getPlaca());
        return auto;
    }
    
    public void actualizarBarraMensaje(String mensaje, Color color)
    {
        barraMensaje.setForeground(color);
        barraMensaje.setText(mensaje);
        pack();
    }
    
    public void aniadirListenerBotonInsertar(ActionListener listener)
    {
        insertar.addActionListener(listener);
    }
    
    private void construirPanelCentral(JPanel panelCentral)
    {
        panelCentral.setLayout(new BorderLayout(10, 0));
        panelCentral.setBorder(new CompoundBorder(new EtchedBorder(), new EmptyBorder(5, 5, 5, 5)));
        JPanel panelMarcadores = new JPanel(new GridLayout(8, 1, 5, 2));
        JPanel panelCampos = new JPanel(new GridLayout(8, 1, 5, 2));
        
        panelMarcadores.add(new JLabel("Categoria"));
        panelMarcadores.add(new JLabel("Nombre"));
        panelMarcadores.add(new JLabel("Placa"));
        panelMarcadores.add(new JLabel("Modelo"));
        panelMarcadores.add(new JLabel("Precio por Dia"));
        panelMarcadores.add(new JLabel("Nro de Pasajeros"));
        panelMarcadores.add(new JLabel("Tipo de Caja"));
        panelMarcadores.add(new JLabel("Imagen"));
        
        panelCampos.add(categoria);
        panelCampos.add(nombre);
        panelCampos.add(placa);
        panelCampos.add(modelo);
        Box panelPrecio = Box.createHorizontalBox();
        panelPrecio.add(precio);
        panelPrecio.add(new JLabel("Bs."));
        panelCampos.add(panelPrecio);
        panelCampos.add(nroPasajeros);
        panelCampos.add(tipoDeCaja);
        Box panelDirImagen = Box.createHorizontalBox();
        panelDirImagen.add(dirImagen);
        panelDirImagen.add(botonBuscar);
        panelCampos.add(panelDirImagen);
        
        panelCentral.add(panelMarcadores, BorderLayout.WEST);
        panelCentral.add(panelCampos, BorderLayout.CENTER);
    }
    
    private void construirPanelSur(JPanel panelSur)
    {
        panelSur.setLayout(new BorderLayout());
        
        JPanel panelExteriorInsertar = new JPanel(new GridLayout(1, 1));
        panelExteriorInsertar.setBorder(new EtchedBorder());
        JPanel panelInteriorInsertar = new JPanel();
        panelInteriorInsertar.add(insertar);
        panelExteriorInsertar.add(panelInteriorInsertar);
        
        JPanel panelBarraMensaje = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBarraMensaje.add(barraMensaje);
        
        panelSur.add(panelExteriorInsertar, BorderLayout.CENTER);
        panelSur.add(panelBarraMensaje, BorderLayout.SOUTH);
    }
    
    private void configurarComponentes()
    {
        insertar.setEnabled(true);
        dirImagen.setEditable(false);
        botonBuscar.addActionListener(this);
        
        botonBuscar.setPreferredSize(new Dimension(15, dirImagen.getHeight()));
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        imagenAuto = ManejadorImagen.buscarImagen(new Buscador());
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
}

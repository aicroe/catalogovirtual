package org.example.catalogovirtual.vista.cliente;


import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.vista.ImageView;
import org.example.catalogovirtual.vista.JPanelAbstracto;
import org.example.catalogovirtual.vista.ManejadorImagen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;


/**
 * JPanel izquierdo de la interfaz grafica.
 * 
 * @author empujesoft
 * @version 2015.06.22
 */
public class JPanelAuto extends JPanelAbstracto
{
    private JLabel nombreCategoria;
    private ImageView imagenAuto;
    private JLabel marcadorDisponible;
    private JLabel nombreAuto;
    private JLabel placaAuto;
    private JLabel modeloAuto;
    private JLabel precioAuto;
    private JLabel nroPasajerosAuto;
    private JLabel tipoCajaAuto;
    private JButton alquilar;
    
    public JPanelAuto()
    {
        nombreCategoria = new JLabel("...");
        imagenAuto = new ImageView(200, 200);
        marcadorDisponible = new JLabel("...");
        nombreAuto = new JLabel("", JLabel.CENTER);
        placaAuto = new JLabel("", JLabel.CENTER);
        modeloAuto = new JLabel("", JLabel.CENTER);
        precioAuto = new JLabel("", JLabel.CENTER);
        nroPasajerosAuto = new JLabel("", JLabel.CENTER);
        tipoCajaAuto = new JLabel("", JLabel.CENTER);
        alquilar = new JButton("Alquilar");
    }
    
    @Override
    public void construir()
    {
        removeAll();
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        construirImagenAuto(this);
        construirCaractsAuto(this);
        construirPanelAlquilar(this);
        configurarMarcadores();
        alquilar.setEnabled(false);
    }
    
    public void limpiarComponentes()
    {
        nombreCategoria.setText("...");
        imagenAuto.limpiarVista();
        marcadorDisponible.setText("...");
        nombreAuto.setText("");
        placaAuto.setText("");
        modeloAuto.setText("");
        precioAuto.setText("");
        nroPasajerosAuto.setText("");
        tipoCajaAuto.setText("");
        alquilar.setEnabled(false);
    }
    
    public void actualizarComponentes(Auto auto)
    {
        nombreCategoria.setText(auto.getNombreCategoria());
        imagenAuto.setImage(ManejadorImagen.leerImagen(ManejadorImagen.FOLDER_AUTOS,
                auto.getPlaca(), ManejadorImagen.MAX_TAMANIO_IMG_AUTO, 
                ManejadorImagen.MAX_TAMANIO_IMG_AUTO, ManejadorImagen.IMAGEN_AUTO_DEFECTO));
        marcadorDisponible.setText(auto.estaDisponible()? "Disponible" : "No Disponible");
        nombreAuto.setText(auto.getNombre());
        placaAuto.setText(auto.getPlaca());
        modeloAuto.setText(String.valueOf(auto.getModelo()));
        precioAuto.setText(auto.getPrecioPorDia() + " Bs.");
        nroPasajerosAuto.setText(String.valueOf(auto.getNumeroDePasajeros()));
        tipoCajaAuto.setText(auto.getTipoDeCaja()? "Automatico" : "Manual");
        alquilar.setEnabled(auto.estaDisponible());
    }
    
    public String getPlacaAutoActual()
    {
        return placaAuto.getText();
    }
    
    public void aniadirListenerAlquilar(ActionListener listener)
    {
        alquilar.addActionListener(listener);
    }
    
    private void construirImagenAuto(JPanel contenedor)
    {
        Box panelVisorImagen = Box.createVerticalBox();
        
        JPanel panelNomCat = new JPanel();
        ((FlowLayout)panelNomCat.getLayout()).setAlignment(FlowLayout.LEFT);
        panelNomCat.add(new JLabel("Categoria: "));
        panelNomCat.add(nombreCategoria);
        
        JPanel panelImagen = new JPanel();
        panelImagen.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panelImagen.add(imagenAuto);
        
        JPanel panelMarDisp = new JPanel();
        panelMarDisp.add(marcadorDisponible);
        
        panelVisorImagen.add(panelNomCat);
        panelVisorImagen.add(panelImagen);
        panelVisorImagen.add(panelMarDisp);
        
        
        JPanel fijados = new JPanel();
        fijados.setBorder(new EtchedBorder());
        fijados.add(panelVisorImagen);
        contenedor.add(fijados, BorderLayout.NORTH);
    }
    
    private void construirCaractsAuto(JPanel contenedor)
    {
        JPanel panelCaractsAuto = new JPanel(new GridLayout(6, 2, 2, 2));
        panelCaractsAuto.setBorder( new EmptyBorder(2, 10, 2, 2));
        aniadirCaracts(panelCaractsAuto);
        Box panelFijado = Box.createVerticalBox();
        panelFijado.setBorder(new EtchedBorder());
        panelFijado.add(panelCaractsAuto);
        panelFijado.add(Box.createVerticalGlue());
        
        contenedor.add(panelFijado, BorderLayout.CENTER);
    }
    
    private void aniadirCaracts(JPanel panel)
    {
        panel.add(new JLabel("Nombre: "));
        panel.add(nombreAuto);
        panel.add(new JLabel("Placa: "));
        panel.add(placaAuto);
        panel.add(new JLabel("Modelo: "));
        panel.add(modeloAuto);
        panel.add(new JLabel("Precio por Dia: "));
        panel.add(precioAuto);
        panel.add(new JLabel("Nro de Pasajeros: "));
        panel.add(nroPasajerosAuto);
        panel.add(new JLabel("Tipo de caja: "));
        panel.add(tipoCajaAuto);
    }
    
    private void construirPanelAlquilar(JPanel contenedor)
    {
        JPanel panelCompAlquilar = new JPanel();
        panelCompAlquilar.setBorder(new EtchedBorder());
        
        panelCompAlquilar.add(alquilar);
        
        contenedor.add(panelCompAlquilar, BorderLayout.SOUTH);
    }
    
    private void configurarMarcadores()
    {
        nombreAuto.setOpaque(true);
        placaAuto.setOpaque(true);
        modeloAuto.setOpaque(true);
        precioAuto.setOpaque(true);
        nroPasajerosAuto.setOpaque(true);
        tipoCajaAuto.setOpaque(true);
        
        nombreAuto.setBackground(Color.WHITE);
        placaAuto.setBackground(Color.WHITE);
        modeloAuto.setBackground(Color.WHITE);
        precioAuto.setBackground(Color.WHITE);
        nroPasajerosAuto.setBackground(Color.WHITE);
        tipoCajaAuto.setBackground(Color.WHITE);
        
        nombreAuto.setBorder(new EtchedBorder());
        placaAuto.setBorder(new EtchedBorder());
        modeloAuto.setBorder(new EtchedBorder());
        precioAuto.setBorder(new EtchedBorder());
        nroPasajerosAuto.setBorder(new EtchedBorder());
        tipoCajaAuto.setBorder(new EtchedBorder());
        
        nombreAuto.setPreferredSize(new Dimension(100, 25));
        placaAuto.setPreferredSize(new Dimension(100, 25));
        modeloAuto.setPreferredSize(new Dimension(100, 25));
        precioAuto.setPreferredSize(new Dimension(100, 25));
        nroPasajerosAuto.setPreferredSize(new Dimension(100, 25));
        tipoCajaAuto.setPreferredSize(new Dimension(100, 25));
    }
}

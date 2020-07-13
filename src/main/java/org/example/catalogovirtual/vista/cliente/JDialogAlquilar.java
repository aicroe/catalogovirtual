/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.example.catalogovirtual.vista.cliente;

import org.example.catalogovirtual.modelo.cuerpo.utiles.*;
import org.example.catalogovirtual.modelo.nucleo.*;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author empujesoft
 * @version 2015.08.08
 */
public class JDialogAlquilar extends JDialogAbstracto {

    private JPanel marcadores;
    private JPanel panelFechas;
    private SelectorFecha selectorFechaInicio;
    private SelectorFecha selectorFechaFinal;
    private JPanel opciones;
    private JLabel cliente;
    private JLabel placa;
    private JLabel garantia;
    private JLabel precio;
    private Boton aceptar;
    private Boton cancelar;
    private JLabel barraMensaje;
    
    public JDialogAlquilar(JFrame duenio) {
        super(duenio, "Alquilar");
    }

    @Override
    public void construir() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 15));
        contenedor.setBorder(new CompoundBorder(new EmptyBorder(15, 15, 15, 15), 
                new CompoundBorder(new EtchedBorder(), new EmptyBorder(10, 10, 10, 10))));
        
        construirMarcadores();
        construirPanelFechas();
        construirOpciones();
        
        contenedor.add(marcadores, BorderLayout.NORTH);
        contenedor.add(panelFechas, BorderLayout.CENTER);
        contenedor.add(opciones, BorderLayout.SOUTH);
        
        configurarComponentes();
        setContentPane(contenedor);
        pack();
        posicionarDialogo();
    }
    
    private void construirMarcadores(){
        
        marcadores = new JPanel(new GridLayout(4, 2, 2, 10));
        
        marcadores.add(new JLabel("Solicitante"));
        cliente = new JLabel(" ", JLabel.CENTER);
        marcadores.add(cliente);
        marcadores.add(new JLabel("Placa del Auto"));
        placa = new JLabel(" ", JLabel.CENTER);
        marcadores.add(placa);
        marcadores.add(new JLabel("Precio Por Dia"));
        precio = new JLabel(" ", JLabel.CENTER);
        marcadores.add(precio);
        marcadores.add(new JLabel("Garantia"));
        garantia = new JLabel(" ", JLabel.CENTER);
        marcadores.add(garantia);
    }
    
    private void construirPanelFechas(){
        
        panelFechas = new JPanel();
        panelFechas.setLayout(new BoxLayout(panelFechas, BoxLayout.Y_AXIS));
        selectorFechaInicio = new SelectorFecha(Fechas.getAnioActual());
        selectorFechaFinal = new SelectorFecha(Fechas.getAnioActual());
        
        JPanel panelFechaInicio = new JPanel(new BorderLayout(10, 0));
        panelFechaInicio.add(new JLabel("Fecha Inicio"), BorderLayout.WEST);
        panelFechaInicio.add(selectorFechaInicio, BorderLayout.EAST);
        JPanel panelFechaFinal = new JPanel(new BorderLayout(10, 0));
        panelFechaFinal.add(new JLabel("Fecha Final"), BorderLayout.WEST);
        panelFechaFinal.add(selectorFechaFinal, BorderLayout.EAST);
        panelFechas.add(panelFechaInicio);
        panelFechas.add(panelFechaFinal);
    }
    
    private void construirOpciones(){
        opciones = new JPanel(new BorderLayout());
        
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        aceptar = new Boton("Aceptar");
        cancelar = new Boton("Cancelar");
        botones.add(aceptar);
        botones.add(cancelar);
        
        JPanel panelBarraMensaje = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barraMensaje = new JLabel("");
        panelBarraMensaje.add(barraMensaje);
        
        opciones.add(botones, BorderLayout.NORTH);
        opciones.add(panelBarraMensaje, BorderLayout.SOUTH);
    }
    
    private void configurarComponentes(){
        
        cliente.setOpaque(true);
        placa.setOpaque(true);
        precio.setOpaque(true);
        garantia.setOpaque(true);
        
        cliente.setBorder(new EtchedBorder());
        cliente.setBackground(Color.WHITE);
        placa.setBorder(new EtchedBorder());
        placa.setBackground(Color.WHITE);
        precio.setBorder(new EtchedBorder());
        precio.setBackground(Color.WHITE);
        garantia.setBorder(new EtchedBorder());
        garantia.setBackground(Color.WHITE);
        
        cliente.setPreferredSize(new Dimension(100, 25));
        placa.setPreferredSize(new Dimension(100, 25));
        precio.setPreferredSize(new Dimension(100, 25));
        garantia.setPreferredSize(new Dimension(100, 25));
    }
    
    public void actualizarComponentes(Cliente cliente, Auto auto){
        selectorFechaInicio.setFecha(Fechas.getFechaActual());
        selectorFechaFinal.setFecha(Fechas.getFechaActual());
        this.cliente.setText(cliente.getNombre());
        this.placa.setText(auto.getPlaca());
        this.precio.setText(String.valueOf(auto.getPrecioPorDia()));
        this.garantia.setText(String.valueOf(auto.getGarantia()));
    }
    
    public void completarSolicitud(Solicitud solicitud){
        
        Date fechaInicial = selectorFechaInicio.getFecha();
        Date fechaFinal = selectorFechaFinal.getFecha();
        Double precioPorDia = Double.parseDouble(precio.getText());
        solicitud.setFechaInicio(fechaInicial);
        solicitud.setFechaFinal(fechaFinal);
        solicitud.setPrecioTotal(Fechas.calcularPrecioTotal(
                fechaInicial, fechaFinal, precioPorDia));
    }
    
    public String getPlacaAuto(){
        
        return placa.getText();
    }
    
    public void aniadirListenerAceptar(ActionListener litener){
        
        aceptar.addActionListener(litener);
    }
    
    public void mostrarMensaje(String texto, Color color){
        
        barraMensaje.setForeground(color);
        barraMensaje.setText(texto);
        pack();
    }
    
    private class Boton extends JButton implements ActionListener{
        private Boton(String titulo) {
            super(titulo);
            addActionListener(this);
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            String titulo = getText();
            switch (titulo) {
                case "Confirmar":
                    //para el controlador...
                    break;
                case "Cancelar":
                    cerrarDialogo();
                    break;
            }
        }
    }
    
    private class SelectorFecha extends JPanel{
        JComboBox dia;
        JComboBox mes;
        JComboBox anio;
        final int RANGO_ANIOS = 10;
        final int ANIO_ACTUAL;
        private SelectorFecha(int anioActual){
            super();
            this.ANIO_ACTUAL = anioActual;
            setLayout(new GridLayout(1, 3, 2, 0));
            construirSelFecha();
        }
        
        private void construirSelFecha(){
            dia = new JComboBox(Fechas.DIAS_DEL_MES);
            mes = new JComboBox(Fechas.Mes.values());
            anio = new JComboBox();
            crearAnios(ANIO_ACTUAL, RANGO_ANIOS);
            add(dia);
            add(mes);
            add(anio);
        }
    
        private void crearAnios(int anioActual, int cantAnios){
            for(int i = 0; i< cantAnios; i++){
                anio.addItem(anioActual);
                anioActual++;
            }
        }
        
        public Date getFecha(){
            int dias = Integer.parseInt(((String)dia.getSelectedItem()));
            int meses = ((Fechas.Mes)mes.getSelectedItem()).getValor();
            int anios = (int) anio.getSelectedItem();
            return Fechas.crearFecha(dias, meses, anios);
        }
        
        public void setFecha(Date fecha){
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fecha);
            dia.setSelectedIndex(calendario.get(Calendar.DAY_OF_MONTH) - 1);
            mes.setSelectedIndex(calendario.get(Calendar.MONTH));
            int anioDeFecha = calendario.get(Calendar.YEAR);
            if(anioDeFecha - ANIO_ACTUAL <= 0){
                anio.setSelectedIndex(0);
            }else if(anioDeFecha - ANIO_ACTUAL <= RANGO_ANIOS){
                anio.setSelectedIndex(anioDeFecha - ANIO_ACTUAL);
            }else{
                anio.setSelectedIndex(RANGO_ANIOS - 1);
            }
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.vista.admin;

import org.example.catalogovirtual.controlador.JDialogSolicitudControlador;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Fechas;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import org.example.catalogovirtual.vista.ImageView;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import org.example.catalogovirtual.vista.JPanelAbstracto;
import org.example.catalogovirtual.vista.ManejadorImagen;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Date;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author garcia
 */
public class JDialogSolicitud extends JDialogAbstracto{
    
    private JPanelSolicitante panelSolicitante;
    private JPanelReserva panelReserva;
    private JPanelDatos panelDatos;
    private JPanelBarraHerrmts barraHerrmts;
    
    public JDialogSolicitud(UsuarioGUI duenio){
        
        super(duenio, "Solicitud");
        panelSolicitante = new JPanelSolicitante();
        panelReserva = new JPanelReserva();
        panelDatos = new JPanelDatos();
        barraHerrmts = new JPanelBarraHerrmts();
    }

    @Override
    protected void construir(){
        
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JPanel panelOeste = new JPanel(new BorderLayout());
        panelSolicitante.construir();
        panelReserva.construir();
        panelDatos.construir();
        barraHerrmts.construir();
        
        panelOeste.add(panelSolicitante, BorderLayout.NORTH);
        panelOeste.add(panelDatos, BorderLayout.SOUTH);
        contenedor.add(panelOeste, BorderLayout.WEST);
        contenedor.add(panelReserva, BorderLayout.EAST);
        contenedor.add(barraHerrmts, BorderLayout.SOUTH);
        setContentPane(contenedor);
        pack();
        posicionarDialogo();
    }
    
    public void setListeners(JDialogSolicitudControlador controlador){
        
        barraHerrmts.aprobar.addActionListener(controlador.new ListenerBotonAprobar());
        barraHerrmts.eliminar.addActionListener(controlador.new ListenerBotonEliminar());
        barraHerrmts.terminar.addActionListener(controlador.new ListenerBotonTerminar());
    }
    
    public void actualizarComponetes(Solicitud solicitud){
        
        Cliente solicitante = solicitud.getCliente();
        Auto reserva = solicitud.getAuto();
        panelSolicitante.ci.setText(String.valueOf(solicitante.getCi()));
        panelSolicitante.imagen.setImage(
                ManejadorImagen.leerImagen(
                        ManejadorImagen.FOLDER_USUARIOS, 
                        solicitante.getLogin(), 
                        ManejadorImagen.MAX_TAMANIO_IMG_USUARIO, 
                        ManejadorImagen.MAX_TAMANIO_IMG_USUARIO, 
                        ManejadorImagen.IMAGEN_USUARIO_DEFECTO));
        panelSolicitante.nombre.setText(solicitante.getNombre());
        panelSolicitante.telefono.setText(String.valueOf(solicitante.getTelefono()));
        panelSolicitante.tipoLicencia.setText(String.valueOf(solicitante.getTipoLicencia()));
        panelReserva.categoria.setText(reserva.getNombreCategoria());
        panelReserva.imagen.setImage(
                ManejadorImagen.leerImagen(
                        ManejadorImagen.FOLDER_AUTOS, 
                        reserva.getPlaca(), 
                        ManejadorImagen.MAX_TAMANIO_IMG_AUTO, 
                        ManejadorImagen.MAX_TAMANIO_IMG_AUTO, 
                        ManejadorImagen.IMAGEN_AUTO_DEFECTO));
        panelReserva.modelo.setText(String.valueOf(reserva.getModelo()));
        panelReserva.nombre.setText(reserva.getNombre());
        panelReserva.placa.setText(reserva.getPlaca());
        panelDatos.estado.setForeground(Solicitudes.colorDescriptivoDeEstado(solicitud.getEstado()));
        panelDatos.estado.setText(solicitud.getEstado());
        panelDatos.fechaInicial.setText(solicitud.getFechaInicial().toString());
        panelDatos.fechaFinal.setText(solicitud.getFechaFinal().toString());
        panelDatos.garantia.setText(String.valueOf(solicitud.getGarantia()));
        panelDatos.observaciones.append(solicitud.getObservaciones());
        panelDatos.precioTotal.setText(String.valueOf(solicitud.getPrecioTotal()));
        panelDatos.recaudacion.setText(String.valueOf(solicitud.getRecaudacionFinal()));
        habilitarBotonesPorEstado(solicitud.getEstado(), solicitud.getFechaInicial());
    }
    
    private void habilitarBotonesPorEstado(String estado, Date fechaInicial){
        
        if(estado.equals(Solicitudes.ESTADO_ELIMINADO) ||
                estado.equals(Solicitudes.ESTADO_TERMINADO)){
            barraHerrmts.eliminar.setEnabled(true);
            barraHerrmts.aprobar.setEnabled(false);
            barraHerrmts.terminar.setEnabled(false);
        }else if(estado.equals(Solicitudes.ESTADO_RESERVA)){
            barraHerrmts.aprobar.setEnabled(
                    Fechas.calcularDiasEntreFechas(
                            Fechas.getFechaActual(), fechaInicial) == 0);
            barraHerrmts.eliminar.setEnabled(true);
            barraHerrmts.terminar.setEnabled(false);
        }else if(estado.equals(Solicitudes.ESTADO_EN_CURSO) ||
                estado.equals(Solicitudes.ESTADO_VENCIDO)){
            barraHerrmts.aprobar.setEnabled(false);
            barraHerrmts.eliminar.setEnabled(true);
            barraHerrmts.terminar.setEnabled(true);
        }
    }
    
    private class JPanelSolicitante extends JPanelAbstracto{
        
        private ImageView imagen;
        private JLabel ci;
        private JLabel nombre;
        private JLabel telefono;
        private JLabel tipoLicencia;
        
        public JPanelSolicitante(){
            
            imagen = new ImageView(
                    ManejadorImagen.MAX_TAMANIO_IMG_USUARIO, 
                    ManejadorImagen.MAX_TAMANIO_IMG_USUARIO);
            ci = new JLabel(" ", JLabel.CENTER);
            nombre = new JLabel(" ", JLabel.CENTER);
            telefono = new JLabel(" ", JLabel.CENTER);
            tipoLicencia = new JLabel(" ", JLabel.CENTER);
        }

        @Override
        public void construir(){
            
            removeAll();
            setLayout(new BorderLayout(20, 0));
            setBorder(new CompoundBorder(
                    new TitledBorder(new EtchedBorder(), "Solicitante "), 
                    new EmptyBorder(5, 5, 5, 5)));
            
            JPanel panelOeste = new JPanel(new GridLayout(1, 1));
            panelOeste.setBorder(new CompoundBorder(
                    new EtchedBorder(), 
                    new EmptyBorder(5, 5, 5, 5)));
            panelOeste.add(imagen);
            JPanel panelCentral = new JPanel(new GridLayout(4, 2, 5, 2));
            panelCentral.setBorder(new EmptyBorder(5, 5, 5, 5));
            panelCentral.add(new JLabel("CI "));
            panelCentral.add(ci);
            panelCentral.add(new JLabel("Nombre "));
            panelCentral.add(nombre);
            panelCentral.add(new JLabel("Telefono "));
            panelCentral.add(telefono);
            panelCentral.add(new JLabel("Tipo Licencia "));
            panelCentral.add(tipoLicencia);
            
            configurarComponentes();
            add(panelOeste, BorderLayout.WEST);
            add(panelCentral, BorderLayout.CENTER);
        }
        
        private void configurarComponentes(){
            
            ci.setOpaque(true);
            nombre.setOpaque(true);
            telefono.setOpaque(true);
            tipoLicencia.setOpaque(true);
            
            ci.setBorder(new EtchedBorder());
            nombre.setBorder(new EtchedBorder());
            telefono.setBorder(new EtchedBorder());
            tipoLicencia.setBorder(new EtchedBorder());
            
            ci.setBackground(Color.WHITE);
            nombre.setBackground(Color.WHITE);
            telefono.setBackground(Color.WHITE);
            tipoLicencia.setBackground(Color.WHITE);
        }
    }
    
    private class JPanelReserva extends JPanelAbstracto{
        
        private ImageView imagen;
        private JLabel placa;
        private JLabel nombre;
        private JLabel categoria;
        private JLabel modelo;
        
        public JPanelReserva(){
            
            imagen = new ImageView(
                    ManejadorImagen.MAX_TAMANIO_IMG_AUTO, 
                    ManejadorImagen.MAX_TAMANIO_IMG_AUTO);
            placa = new JLabel(" ", JLabel.CENTER);
            nombre = new JLabel(" ", JLabel.CENTER);
            categoria = new JLabel(" ", JLabel.CENTER);
            modelo = new JLabel(" ", JLabel.CENTER);
        }
        
        @Override
        public void construir(){
            
            removeAll();
            setLayout(new FlowLayout());
            
            Box contenedor = Box.createVerticalBox();
            JPanel panelImagen = new JPanel(new GridLayout(1, 1));
            panelImagen.setBorder(new CompoundBorder(
                    new EtchedBorder(), 
                    new EmptyBorder(5, 5, 5, 5)));
            panelImagen.add(imagen);
            
            JPanel panelDatos = new JPanel(new GridLayout(4, 2, 5, 2));
            panelDatos.setBorder(new CompoundBorder(
                    new TitledBorder(new EtchedBorder(), "Reserva "), 
                    new EmptyBorder(5, 5, 5, 5)));
            panelDatos.add(new JLabel("Placa "));
            panelDatos.add(placa);
            panelDatos.add(new JLabel("Nombre "));
            panelDatos.add(nombre);
            panelDatos.add(new JLabel("Categoria "));
            panelDatos.add(categoria);
            panelDatos.add(new JLabel("Modelo "));
            panelDatos.add(modelo);
            
            contenedor.add(panelImagen);
            contenedor.add(panelDatos);
            
            configurarComponentes();
            add(contenedor);
        }
        
        private void configurarComponentes(){
            
            placa.setOpaque(true);
            nombre.setOpaque(true);
            categoria.setOpaque(true);
            modelo.setOpaque(true);
            
            placa.setBorder(new EtchedBorder());
            nombre.setBorder(new EtchedBorder());
            categoria.setBorder(new EtchedBorder());
            modelo.setBorder(new EtchedBorder());
            
            placa.setBackground(Color.WHITE);
            nombre.setBackground(Color.WHITE);
            categoria.setBackground(Color.WHITE);
            modelo.setBackground(Color.WHITE);
        }
    }
    
    private class JPanelDatos extends JPanelAbstracto{
        
        private JLabel estado;
        private JLabel precioTotal;
        private JLabel garantia;
        private JLabel fechaInicial;
        private JLabel fechaFinal;
        private JLabel recaudacion;
        private JTextArea observaciones;
        
        public JPanelDatos(){
            
            estado = new JLabel(" ", JLabel.CENTER);
            precioTotal = new JLabel(" ", JLabel.CENTER);
            garantia = new JLabel(" ", JLabel.CENTER);
            fechaInicial = new JLabel(" ", JLabel.CENTER);
            fechaFinal = new JLabel(" ", JLabel.CENTER);
            recaudacion = new JLabel(" ", JLabel.CENTER);
            observaciones = new JTextArea(3, 5);
        }
        
        @Override
        public void construir(){
            
            removeAll();
            setLayout(new BorderLayout());
            setBorder(new CompoundBorder(
                    new CompoundBorder(
                            new EmptyBorder(0, 2, 0, 2), new EtchedBorder()), 
                    new EmptyBorder(5, 15, 5, 5)));
            
            JPanel panelOeste = new JPanel(new GridLayout(6, 1, 0, 2));
            JPanel panelEste = new JPanel(new GridLayout(6, 1, 0, 2));
            panelOeste.add(new JLabel("Estado "));
            panelOeste.add(new JLabel("Precio Total "));
            panelOeste.add(new JLabel("Garantia "));
            panelOeste.add(new JLabel("Fecha Inicio "));
            panelOeste.add(new JLabel("Fecha Final "));
            panelOeste.add(new JLabel("Recaudacion Final "));
            panelEste.add(estado);
            panelEste.add(precioTotal);
            panelEste.add(garantia);
            panelEste.add(fechaInicial);
            panelEste.add(fechaFinal);
            panelEste.add(recaudacion);
            JPanel panelExterior = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel panelInterior = new JPanel(new BorderLayout(10, 0));
            panelInterior.add(panelOeste, BorderLayout.WEST);
            panelInterior.add(panelEste, BorderLayout.EAST);
            panelExterior.add(panelInterior);
            
            JPanel panelSur = new JPanel(new BorderLayout());
            JPanel panelMarcador = new JPanel();
            panelMarcador.add(new JLabel("Observaciones "));
            panelSur.add(panelMarcador, BorderLayout.WEST);
            panelSur.add(observaciones);
            
            configurarComponentes();
            add(panelExterior, BorderLayout.NORTH);
            add(panelSur, BorderLayout.SOUTH);
        }
        
        private void configurarComponentes(){
            
            estado.setOpaque(true);
            precioTotal.setOpaque(true);
            garantia.setOpaque(true);
            fechaInicial.setOpaque(true);
            fechaFinal.setOpaque(true);
            recaudacion.setOpaque(true);
            
            estado.setBorder(new EtchedBorder());
            precioTotal.setBorder(new EtchedBorder());
            garantia.setBorder(new EtchedBorder());
            fechaInicial.setBorder(new EtchedBorder());
            fechaFinal.setBorder(new EtchedBorder());
            recaudacion.setBorder(new EtchedBorder());
            
            estado.setBackground(Color.WHITE);
            precioTotal.setBackground(Color.WHITE);
            garantia.setBackground(Color.WHITE);
            fechaInicial.setBackground(Color.WHITE);
            fechaFinal.setBackground(Color.WHITE);
            recaudacion.setBackground(Color.WHITE);
            
            observaciones.setBorder(new EtchedBorder());
            observaciones.setEditable(false);
            
            estado.setPreferredSize(new Dimension(120, 20));
            precioTotal.setPreferredSize(new Dimension(120, 20));
            garantia.setPreferredSize(new Dimension(120, 20));
            fechaInicial.setPreferredSize(new Dimension(120, 20));
            fechaFinal.setPreferredSize(new Dimension(120, 20));
            recaudacion.setPreferredSize(new Dimension(120, 20));
        }
    }
    
    private class JPanelBarraHerrmts extends JPanelAbstracto{
        
        private JButton aprobar;
        private JButton terminar;
        private JButton eliminar;
        
        public JPanelBarraHerrmts(){
            
            aprobar = new JButton("Aprobar");
            terminar = new JButton("Terminar");
            eliminar = new JButton("Eliminar");
        }
        
        @Override
        public void construir(){
            
            removeAll();
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBorder(new EmptyBorder(5, 5, 5, 5));
            add(aprobar);
            add(terminar);
            add(eliminar);
        }
    }
}

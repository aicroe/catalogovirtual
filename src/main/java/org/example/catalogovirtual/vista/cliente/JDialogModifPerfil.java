/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.vista.cliente;

import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.vista.ImageView;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import org.example.catalogovirtual.vista.JTextChar;
import org.example.catalogovirtual.vista.JTextNumber;
import org.example.catalogovirtual.vista.ManejadorImagen;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;


/**
 * JDialogo para modificar el perfil del cliente.
 *
 * @author empujesoft
 * @version 2015.08.05
 */
public class JDialogModifPerfil extends JDialogAbstracto implements ActionListener,
        KeyListener{
    
    private JLabel ci;
    private JTextField nombre;
    private JTextNumber telefono;
    private JTextChar tipoLicencia;
    private JTextField direccion;
    private JTextField lugarTrabajo;
    private JTextField ocupacion;
    private ImageView imagen;
    private JButton cambiarImagen;
    private JButton cambiarContrasenia;
    private JButton guardarCambios;
    private JButton cancelar;
    private JPasswordField contraseniaActual;
    private JPasswordField contraseniaNueva;
    private JLabel barraMensaje;
    
    public JDialogModifPerfil(UsuarioGUI duenio)
    {
        super(duenio, "Modificar Perfil");
        
        nombre = new JTextField(8);
        ci = new JLabel(" ");
        telefono = new JTextNumber(8, 8, false);
        tipoLicencia = new JTextChar(8, 'A', 'B', 'C', 'P');
        direccion = new JTextField(8);
        lugarTrabajo = new JTextField(8);
        ocupacion = new JTextField(8);
        imagen = new ImageView(ManejadorImagen.MAX_TAMANIO_IMG_USUARIO, 
                ManejadorImagen.MAX_TAMANIO_IMG_USUARIO);
        cambiarImagen = new JButton("Cambiar Imagen");
        cambiarContrasenia = new JButton("Cambiar Contraseña");
        guardarCambios = new JButton("Guardar Cambios");
        cancelar = new JButton("Cancelar");
        contraseniaActual = new JPasswordField(8);
        contraseniaNueva = new JPasswordField(8);
        barraMensaje = new JLabel("");
    }

    @Override
    public void construir()
    {
        JPanel contenedor = new JPanel(new BorderLayout(20, 0));
        contenedor.setBorder(new CompoundBorder(new EmptyBorder(15, 15, 15, 15), 
                new CompoundBorder(new EtchedBorder(), new EmptyBorder(10, 10, 10, 10))));
        
        JPanel panelOeste = new JPanel();
        JPanel panelEste = new JPanel();
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        construirPanelOeste(panelOeste);
        construirPanelEste(panelEste);
        panelSur.add(barraMensaje);
        
        contenedor.add(panelOeste, BorderLayout.WEST);
        contenedor.add(panelEste, BorderLayout.EAST);
        contenedor.add(panelSur, BorderLayout.SOUTH);
        
        setContentPane(contenedor);
        configurarComponentes();
        pack();
        posicionarDialogo();
    }
    
    public void aniadirListenerGuardarCambios(ActionListener listener)
    {
        guardarCambios.addActionListener(listener);
    }
    
    public void actualizarComponentes(Cliente cliente)
    {
        nombre.setText(cliente.getNombre());
        ci.setText(String.valueOf(cliente.getCi()));
        telefono.setText(String.valueOf(cliente.getTelefono()));
        tipoLicencia.setText(String.valueOf(cliente.getTipoLicencia()));
        direccion.setText(cliente.getDireccion());
        lugarTrabajo.setText(cliente.getLugarTrabajo());
        ocupacion.setText(cliente.getOcupacion());
        imagen.setImage(ManejadorImagen.leerImagen(ManejadorImagen.FOLDER_USUARIOS, 
                cliente.getLogin(), ManejadorImagen.MAX_TAMANIO_IMG_USUARIO, 
                ManejadorImagen.MAX_TAMANIO_IMG_USUARIO, ManejadorImagen.IMAGEN_USUARIO_DEFECTO));
    }
    
    public void cambiarTextoBarraMensaje(String mensaje, Color color)
    {
        barraMensaje.setForeground(color);
        barraMensaje.setText(mensaje);
        pack();
    }
    
    public static class PerfilModificado
    {
        public String nombre;
        public int telefono;
        public char tipoLicencia;
        public String direccion;
        public String lugarTrabajo;
        public String ocupacion;
        public char[] contraseniaActual;
        public char[] contraseniaNueva;
        public BufferedImage imagen;
    }
    
    public PerfilModificado getPerfilModificado()
    {
        if(guardarCambios.isEnabled()){
            PerfilModificado perfil = new PerfilModificado();
            perfil.nombre = nombre.getText();
            perfil.telefono = telefono.getInt();
            perfil.tipoLicencia = tipoLicencia.getChar();
            perfil.direccion = direccion.getText();
            perfil.lugarTrabajo = lugarTrabajo.getText();
            perfil.ocupacion = ocupacion.getText();
            perfil.contraseniaActual = contraseniaActual.getPassword();
            perfil.contraseniaNueva = contraseniaNueva.getPassword();
            perfil.imagen = (BufferedImage) imagen.getImage();
            return perfil;
        }
        throw new UnsupportedOperationException();
    }
    
    public boolean cambiandoContrasenia()
    {
        return contraseniaActual.isEnabled() && contraseniaNueva.isEnabled();
    }
    
    private void construirPanelOeste(JPanel panelOeste)
    {
        panelOeste.setLayout(new BoxLayout(panelOeste, BoxLayout.Y_AXIS));
        JPanel celdas = new JPanel();
        celdas.setLayout(new GridLayout(7, 2, 20, 5));
        
        celdas.add(new JLabel("CI"));
        celdas.add(ci);
        celdas.add(new JLabel("Nombre"));
        celdas.add(nombre);
        celdas.add(new JLabel("Telefono"));
        celdas.add(telefono);
        celdas.add(new JLabel("Tipo de Licencia"));
        celdas.add(tipoLicencia);
        celdas.add(new JLabel("Direccion"));
        celdas.add(direccion);
        celdas.add(new JLabel("Lugar de Trabajo"));
        celdas.add(lugarTrabajo);
        celdas.add(new JLabel("Ocupacion"));
        celdas.add(ocupacion);
        
        JPanel panelCeldas = new JPanel();
        panelCeldas.add(celdas);
        JPanel panelBotones = new JPanel();
        panelBotones.add(guardarCambios);
        panelBotones.add(cancelar);
        
        panelOeste.add(panelCeldas);
        panelOeste.add(panelBotones);
    }
    
    private void construirPanelEste(JPanel panelEste)
    {
        panelEste.setLayout(new FlowLayout());
        JPanel panelInterior = new JPanel();
        panelInterior.setLayout(new BoxLayout(panelInterior, BoxLayout.Y_AXIS));
        
        JPanel panelImagenExt = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelImagenInt = new JPanel();
        panelImagenInt.setBorder(new CompoundBorder(
                new EtchedBorder(), new EmptyBorder(2, 2, 2, 2)));
        JPanel panelCambiarImagen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelCambiarCont = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        panelImagenInt.add(imagen);
        panelImagenExt.add(panelImagenInt);
        panelCambiarImagen.add(cambiarImagen);
        panelCambiarCont.add(cambiarContrasenia);
        
        panelInterior.add(panelImagenExt);
        panelInterior.add(panelCambiarImagen);
        panelInterior.add(panelCambiarCont);
        panelEste.add(panelInterior);
        
        construirPanelContrasenia(panelInterior, panelCambiarCont, 
                panelImagenExt, panelCambiarImagen);
    }
    
    private void construirPanelContrasenia(JPanel panelEste, JPanel panelCambiarCont,
            JPanel panelImagenExt, JPanel panelCambiarImagen){
        cambiarContrasenia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panelEste.remove(panelCambiarCont);
                ((FlowLayout)panelImagenExt.getLayout()).setAlignment(FlowLayout.CENTER);
                ((FlowLayout)panelCambiarImagen.getLayout()).setAlignment(FlowLayout.CENTER);
                JPanel panelMarcadores = new JPanel(new GridLayout(2, 1, 0, 5));
                JPanel panelCampos = new JPanel(new GridLayout(2, 1, 0, 5));
                panelMarcadores.add(new JLabel("Contraseña Actual"));
                panelMarcadores.add(new JLabel("Contraseña Nueva"));
                panelCampos.add(contraseniaActual);
                panelCampos.add(contraseniaNueva);
                JPanel panelContrasenia = new JPanel(new BorderLayout(8, 0));
                panelContrasenia.add(panelMarcadores, BorderLayout.WEST);
                panelContrasenia.add(panelCampos, BorderLayout.EAST);
                panelEste.add(panelContrasenia);
                contraseniaActual.setEnabled(true);
                contraseniaNueva.setEnabled(true);
                
                pack();
            }
        });
    }
    
    private void configurarComponentes()
    {
        guardarCambios.setEnabled(false);
        contraseniaActual.setEnabled(false);
        contraseniaNueva.setEnabled(false);
        
        ci.setOpaque(true);
        ci.setBorder(new EtchedBorder());
        ci.setBackground(Color.WHITE);
        
        ci.setPreferredSize(new Dimension(100, 25));
        nombre.setPreferredSize(new Dimension(100, 25));
        telefono.setPreferredSize(new Dimension(100, 25));
        direccion.setPreferredSize(new Dimension(100, 25));
        lugarTrabajo.setPreferredSize(new Dimension(100, 25));
        ocupacion.setPreferredSize(new Dimension(100, 25));
        tipoLicencia.setPreferredSize(new Dimension(100, 25));
        contraseniaActual.setPreferredSize(new Dimension(100, 25));
        contraseniaNueva.setPreferredSize(new Dimension(100, 25));
        
        cancelar.addActionListener(this);
        cambiarContrasenia.addActionListener(this);
        cambiarImagen.addActionListener(this);
        nombre.addKeyListener(this);
        ci.addKeyListener(this);
        telefono.addKeyListener(this);
        tipoLicencia.addKeyListener(this);
        direccion.addKeyListener(this);
        lugarTrabajo.addKeyListener(this);
        ocupacion.addKeyListener(this);
    }
    
    private class BuscadorImagen implements ManejadorImagen.BuscarImagenListener
    {
        @Override
        public int getAnchoMaximo()
        {
            return ManejadorImagen.MAX_TAMANIO_IMG_USUARIO;
        }

        @Override
        public int getAltoMaximo()
        {
            return ManejadorImagen.MAX_TAMANIO_IMG_USUARIO;
        }

        @Override
        public void mostrarNombreImagen(String nombre)
        {
        }

        @Override
        public void mostrarMensaje(String mensaje, Color color)
        {
            cambiarTextoBarraMensaje(mensaje, color);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object boton = e.getSource();
        if(boton == cancelar){
            cerrarDialogo();
        }else if(boton == cambiarContrasenia){
            guardarCambios.setEnabled(true);
        }else if(boton == cambiarImagen){
            BufferedImage unaImagen = ManejadorImagen.buscarImagen(new BuscadorImagen());
            imagen.setImage(unaImagen);
            guardarCambios.setEnabled(unaImagen != null);
        }else{
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        guardarCambios.setEnabled(true);
        cambiarTextoBarraMensaje("", null);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}

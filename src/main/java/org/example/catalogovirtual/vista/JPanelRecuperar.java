/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author empuejsof
 * @vesion 2015.08.18
 */
public class JPanelRecuperar extends JPanelPrincipal implements ActionListener,
        KeyListener{
    
    private JTextNumber ci;
    private JTextNumber telefono;
    private JPasswordField nuevaContrasenia;
    private JButton recuperar;
    private JMenuBar barramenu;
    private JMenuItem iniciarSesion;
    private JMenuItem registrarse;
    private JLabel barraMensaje;
    
    public JPanelRecuperar(JDialogIngresar duenio){
        
        super(duenio);
        ci = new JTextNumber(8, 8, false);
        telefono = new JTextNumber(8, 8, false);
        nuevaContrasenia = new JPasswordField(8);
        recuperar = new JButton("Recuperar");
        iniciarSesion = new JMenuItem("Iniciar Sesion");
        registrarse = new JMenuItem("Registrarse");
        barramenu = new JMenuBar();
        barraMensaje = new JLabel("", JLabel.LEFT);
    }
    
    @Override
    public void construir(){
        
        removeAll();
        configurarComponentes();
        JMenu opciones = new JMenu("Opciones");
        opciones.add(iniciarSesion);
        opciones.add(registrarse);
        opciones.add(getDuenio().getMenuItemSalir());
        barramenu.add(opciones);
        
        setLayout(new BorderLayout(0, 5));
        setBorder(new EmptyBorder(15, 20, 15, 20));
        JPanel panelNorte = new JPanel(new GridLayout(3, 2, 5, 5));
        panelNorte.add(new JLabel("CI "));
        panelNorte.add(ci);
        panelNorte.add(new JLabel("Telefono "));
        panelNorte.add(telefono);
        panelNorte.add(new JLabel("Nueva Contraseña "));
        panelNorte.add(nuevaContrasenia);
        
        JPanel panelSur = new JPanel(new BorderLayout());
        JPanel panelRecuperar = new JPanel();
        panelRecuperar.add(recuperar);
        panelSur.add(panelRecuperar, BorderLayout.NORTH);
        panelSur.add(barraMensaje, BorderLayout.SOUTH);
        
        add(panelNorte, BorderLayout.NORTH);
        add(panelSur, BorderLayout.SOUTH);
    }
    
    @Override
    public JDialogIngresar getDuenio(){
        
        return (JDialogIngresar) super.getDuenio();
    }

    @Override
    public JMenuBar getBarraMenu(){
        
        return barramenu;
    }
    
    @Override
    public String toString(){
        
        return "Recuperar Contraseñia";
    }
    
    public void cambiarTextoBarraMensaje(String mensaje, Color color){
        
        barraMensaje.setForeground(color);
        barraMensaje.setText(mensaje);
        getDuenio().pack();
    }
    
    public int getCi(){
        
        return ci.getInt();
    }
    
    public char[] getNuevaContr(){
        
        return nuevaContrasenia.getPassword();
    }
    
    public int getTelefono(){
        
        return telefono.getInt();
    }
    
    private void configurarComponentes(){
        
        ci.setPreferredSize(new Dimension(100, 25));
        telefono.setPreferredSize(new Dimension(100, 25));
        nuevaContrasenia.setPreferredSize(new Dimension(100, 25));
        
        recuperar.setEnabled(false);
        iniciarSesion.addActionListener(this);
        registrarse.addActionListener(this);
        ci.addKeyListener(this);
        telefono.addKeyListener(this);
        nuevaContrasenia.addKeyListener(this);
        recuperar.addActionListener(
                getDuenio().getListener(JDialogIngresar.LISTENER_BOTON_RECUPERAR));
    }

    @Override
    public void actionPerformed(ActionEvent e){
        
        JMenuItem fuente = (JMenuItem) e.getSource();
        if(fuente == iniciarSesion){
            getDuenio().cambiarPanel(new JPanelIngresar(getDuenio()));
        }else if (fuente == registrarse){
            getDuenio().cambiarPanel(new JPanelRegistrar(getDuenio()));
        }else{
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        
        recuperar.setEnabled(ci.getText().length() >= 6 && 
                telefono.getText().length() >= 6 &&
                nuevaContrasenia.getPassword().length >= 4);
        cambiarTextoBarraMensaje("", null);
    }

    @Override
    public void keyTyped(KeyEvent e){
    }

    @Override
    public void keyPressed(KeyEvent e){
    }
}

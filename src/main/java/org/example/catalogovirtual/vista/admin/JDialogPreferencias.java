/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.vista.admin;

import org.example.catalogovirtual.controlador.JDialogPreferControlador;
import org.example.catalogovirtual.modelo.Preferencias.Raiz;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.nucleo.Administrador;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import org.example.catalogovirtual.vista.JTextNumber;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author garcia
 */
public class JDialogPreferencias extends JDialogAbstracto implements 
        ActionListener, KeyListener{
    
    private JTextNumber cantMaxSolCli;
    private JTextNumber maxNumDiasAlq;
    private JTextNumber maxNumDiasSolRese;
    private JTextField loginAdmin;
    private JPasswordField antPassword;
    private JPasswordField nuevaPassword;
    private JButton cambiarPassword;
    private JComboBox garantias;
    private JTextNumber garantia;
    private JButton guardarCambios;
    private JButton resetear;
    private JButton cancelar;
    private JLabel barraMensaje;
    
    public JDialogPreferencias(UsuarioGUI usuarioGui){
        
        super(usuarioGui, "Preferencias");
        cantMaxSolCli = new JTextNumber(8, 2, false);
        maxNumDiasAlq = new JTextNumber(8, 3, false);
        maxNumDiasSolRese = new JTextNumber(8, 2, false);
        loginAdmin = new JTextField(8);
        antPassword = new JPasswordField(8);
        nuevaPassword = new JPasswordField(8);
        cambiarPassword = new JButton("Cambiar Password");
        garantias = new JComboBox();
        garantia = new JTextNumber(8, 8, true);
        guardarCambios = new JButton("Guardar Cambios");
        resetear = new JButton("Resetear");
        cancelar = new JButton("Cancelar");
        barraMensaje = new JLabel("");
    }
    
    @Override
    protected void construir(){
        
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(20, 15, 15, 15));
        
        JPanel panelNorte = new JPanel();
        construirPanelNorte(panelNorte);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setBorder(new CompoundBorder(
                new EmptyBorder(5, 5, 5, 5), 
                new TitledBorder(new EtchedBorder(), "Garantias ")));
        panelCentral.add(new JLabel("Categoria:"));
        panelCentral.add(garantias);
        panelCentral.add(garantia);
        
        JPanel panelSur = new JPanel(new BorderLayout());
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(new JLabel(" "));
        botones.add(guardarCambios);
        botones.add(resetear);
        botones.add(cancelar);
        JPanel panelBarraMensaje = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBarraMensaje.add(barraMensaje);
        panelSur.add(botones, BorderLayout.CENTER);
        panelSur.add(panelBarraMensaje, BorderLayout.SOUTH);
        
        contenedor.add(panelNorte, BorderLayout.NORTH);
        contenedor.add(panelCentral, BorderLayout.CENTER);
        contenedor.add(panelSur, BorderLayout.SOUTH);
        setContentPane(contenedor);
        pack();
        configurarComponentes();
        posicionarDialogo();
    }
    
    private void construirPanelNorte(JPanel panelNorte){
        
        panelNorte.setLayout(new BorderLayout(10, 10));
        panelNorte.setBorder(new CompoundBorder(
                new EmptyBorder(0, 8, 0, 8), 
                new CompoundBorder(
                        new EtchedBorder(), new EmptyBorder(5, 5, 5, 5))));
        JPanel etiquetas = new JPanel(new GridLayout(3, 1, 0, 5));
        JPanel textos = new JPanel(new GridLayout(3, 1, 0, 5));
        etiquetas.add(new JLabel("Maximo numero de solicitudes por Cliente "));
        etiquetas.add(new JLabel("Maximo numero de dias de alquiler "));
        etiquetas.add(new JLabel("Maximo numero de dias para aprobacion "));
        textos.add(cantMaxSolCli);
        textos.add(maxNumDiasAlq);
        textos.add(maxNumDiasSolRese);
        
        JPanel datos = new JPanel(new BorderLayout());
        JPanel celdas = new JPanel(new GridLayout(1, 2, 0, 5));
        celdas.add(new JLabel("Login Administrador "));
        celdas.add(loginAdmin);
        JPanel panelCambPass = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCambPass.add(cambiarPassword);
        datos.add(celdas, BorderLayout.CENTER);
        datos.add(panelCambPass, BorderLayout.SOUTH);
        cambiarPassword.addActionListener((event)->{
            datos.remove(celdas);
            JPanel nuevaCeldas = new JPanel(new GridLayout(3, 2, 0, 5));
            nuevaCeldas.add(new JLabel("Login Administrador "));
            nuevaCeldas.add(loginAdmin);
            nuevaCeldas.add(new JLabel("Anterior Password "));
            nuevaCeldas.add(antPassword);
            nuevaCeldas.add(new JLabel("Nuevo Password "));
            nuevaCeldas.add(nuevaPassword);
            cambiarPassword.setEnabled(false);
            guardarCambios.setEnabled(true);
            datos.add(nuevaCeldas, BorderLayout.CENTER);
            datos.remove(panelCambPass);
            pack();
        });
        
        panelNorte.add(etiquetas, BorderLayout.WEST);
        panelNorte.add(textos, BorderLayout.EAST);
        panelNorte.add(datos, BorderLayout.SOUTH);
    }
    
    private void configurarComponentes(){
        
        guardarCambios.setEnabled(false);
        cancelar.addActionListener(this);
    }
    
    public void cambiarTextoBarraMensaje(String texto, Color color){
        
        barraMensaje.setForeground(color);
        barraMensaje.setText(texto);
        pack();
    }
    
    public class Cambios{
        
        public int cantMaxSolCli;
        public int maxNumDiasAlq;
        public int maxNumDiasSolRese;
        public String loginAdmin;
        public boolean cambiarPassword;
        public char[] antPassword;
        public char[] nuevoPassword;
    }
    
    public Cambios getCambios(){
        
        Cambios cambios = new Cambios();
        cambios.cantMaxSolCli = cantMaxSolCli.getInt();
        cambios.maxNumDiasAlq = maxNumDiasAlq.getInt();
        cambios.maxNumDiasSolRese = maxNumDiasSolRese.getInt();
        cambios.loginAdmin = loginAdmin.getText();
        cambios.cambiarPassword = !cambiarPassword.isEnabled();
        cambios.antPassword = antPassword.getPassword();
        cambios.nuevoPassword = nuevaPassword.getPassword();
        return cambios;
    }
    
    public void actualizarComponentes(
            Raiz raiz, Administrador admin, Garantia[] valores){
        
        cantMaxSolCli.setText(String.valueOf(raiz.getCantMaxSolCliente()));
        maxNumDiasAlq.setText(String.valueOf(raiz.getMaxNumDiasAlquiler()));
        maxNumDiasSolRese.setText(String.valueOf(
                raiz.getMaxNumDiasSolEnReserva()));
        loginAdmin.setText(admin.getLogin());
        garantias.setModel(new DefaultComboBoxModel(valores));
        garantia.setText(String.valueOf(
                ((Garantia)garantias.getSelectedItem()).getGarantia()));
    }
    
    public void setListeners(JDialogPreferControlador controlador){
        
        garantia.addFocusListener(
                controlador.crearListenerCampoGarantia(garantias, garantia));
        garantias.addActionListener(
                controlador.crearListenerGarantia(garantias, garantia));
        resetear.addActionListener(controlador.new ListenerBotonResetear());
        guardarCambios.addActionListener(controlador.new ListenerGuradarCambios());
        cantMaxSolCli.addKeyListener(this);
        maxNumDiasAlq.addKeyListener(this);
        maxNumDiasSolRese.addKeyListener(this);
        loginAdmin.addKeyListener(this);
        antPassword.addKeyListener(this);
        nuevaPassword.addKeyListener(this);
        garantia.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        
        cerrarDialogo();
    }
    
    @Override
    public void keyTyped(KeyEvent event){
        
        guardarCambios.setEnabled(true);
        cambiarTextoBarraMensaje("", null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

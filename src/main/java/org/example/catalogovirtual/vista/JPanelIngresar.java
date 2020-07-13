package org.example.catalogovirtual.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


/**
 *JPanel del dialogo ingresar.
 * 
 * @author empujesoft
 * @version 2015.07.02
 */
public class JPanelIngresar extends JPanelPrincipal implements KeyListener, ActionListener
{
    private JTextField login;
    private JPasswordField password;
    private JButton ingresar;
    private JLabel barraMensaje;
    private JMenuBar barramenu;
    private JMenuItem registrarse;
    private JMenuItem recuperarContrasenia;
    
    public JPanelIngresar(JDialogIngresar duenio)
    {
        super(duenio);
        login = new JTextField(8);
        password = new JPasswordField(8);
        ingresar = new JButton("Ingresar");
        barraMensaje = new JLabel("");
        barramenu = new JMenuBar();
        registrarse = new JMenuItem("Registrarse");
        recuperarContrasenia = new JMenuItem("Recuperar Contraseña");
    }

    @Override
    public JMenuBar getBarraMenu()
    {
        return barramenu;
    }

    @Override
    public JDialogIngresar getDuenio()
    {
        return (JDialogIngresar) super.getDuenio();
    }
    
    @Override
    public void construir()
    {
        removeAll();
        barramenu.removeAll();
        JMenu opciones = new JMenu("Opciones");
        opciones.add(registrarse);
        opciones.add(recuperarContrasenia);
        opciones.add(getDuenio().getMenuItemSalir());
        barramenu.add(opciones);
        
        setLayout(new BorderLayout(10, 5));
        setBorder(new EmptyBorder(10, 20, 5, 20));
        JPanel panelMarcadores = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel panelCampos = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel panelSur = new JPanel(new BorderLayout());
        JPanel panelIngresar = new JPanel();
        JPanel panelBarraMensaje = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        panelMarcadores.add(new JLabel("Login / CI"));
        panelMarcadores.add(new JLabel("Password"));
        panelCampos.add(login);
        panelCampos.add(password);
        panelIngresar.add(ingresar);
        panelBarraMensaje.add(barraMensaje);
        panelSur.add(panelIngresar, BorderLayout.NORTH);
        panelSur.add(panelBarraMensaje, BorderLayout.SOUTH);
        
        add(panelMarcadores, BorderLayout.WEST);
        add(panelCampos, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
        configurarComponentes();
    }
    
    public void actualizarComponentes(String login, String password)
    {
        this.login.setText(login);
        this.password.setText(password);
    }
    
    public void cambiarTextoBarraMensaje(String texto, Color color)
    {
        barraMensaje.setForeground(color);
        barraMensaje.setText(texto);
        getDuenio().pack();
    }
    
    public String getLogin()
    {
        return login.getText();
    }
    
    public char[] getPassword()
    {
        return password.getPassword();
    }
    
    private void configurarComponentes()
    {
        ingresar.setEnabled(false);
        login.addKeyListener(this);
        password.addKeyListener(this);
        login.setPreferredSize(new Dimension(login.getWidth(), 25));
        password.setPreferredSize(new Dimension(password.getWidth(), 25));
        registrarse.addActionListener(this);
        recuperarContrasenia.addActionListener(this);
        ingresar.addActionListener(
                getDuenio().getListener(JDialogIngresar.LISTENER_BOTON_INGRESAR));
    }
    
    @Override
    public String toString()
    {
        return "Ingresar";
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JMenuItem fuente = (JMenuItem) e.getSource();
        if(fuente == registrarse){
            getDuenio().cambiarPanel(new JPanelRegistrar(getDuenio()));
        }else if(fuente == recuperarContrasenia){
            getDuenio().cambiarPanel(new JPanelRecuperar(getDuenio()));
        }else{
            throw new UnsupportedOperationException();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent event)
    {
        ingresar.setEnabled(getLogin().length() > 0 && getPassword().length > 0);
        cambiarTextoBarraMensaje("", null);
    }
    
    @Override
    public void keyPressed(KeyEvent event)
    {
    }
    
    @Override
    public void keyTyped(KeyEvent event)
    {
    }
}

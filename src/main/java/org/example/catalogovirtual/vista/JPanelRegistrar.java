package org.example.catalogovirtual.vista;

import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Validador;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;


/**
 * JPanel del dialogo ingresar para registrar un nuevo cliente.
 * 
 * @author empujesoft
 * @version 2015.07.02
 */
public class JPanelRegistrar extends JPanelPrincipal implements ActionListener, 
        KeyListener{
    
    private JTextNumber ci;
    private JPasswordField password;
    private JTextField nombre;
    private JTextChar licencia;
    private JTextNumber telefono;
    private JTextField direccion;
    private JTextField lugarTrabajo;
    private JTextField ocupacion;
    private JButton registrar;
    private JTextField dirImagen;
    private JButton buscar;
    private JLabel barraMensaje;
    private BufferedImage imagenUsuario;
    private JMenuBar barramenu;
    private JMenuItem iniciarSesion;
    
    public static final String CAMPOS_OBLIGATORIOS = "* Campos Obligatorios";
    
    public JPanelRegistrar(JDialogIngresar duenio)
    {
        super(duenio);
        ci = new JTextNumber(8, 8, false);
        password = new JPasswordField(8);
        nombre = new JTextField(8);
        licencia = new JTextChar(8, 'A', 'B', 'C', 'P');
        telefono = new JTextNumber(8, 8, false);
        direccion = new JTextField(8);
        lugarTrabajo = new JTextField(8);
        ocupacion = new JTextField(8);
        registrar = new JButton("Registrar");
        dirImagen = new JTextField(5);
        buscar = new JButton("...");
        imagenUsuario = null;
        barraMensaje = new JLabel(CAMPOS_OBLIGATORIOS);
        barramenu = new JMenuBar();
        iniciarSesion = new JMenuItem("Iniciar Sesion");
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
        opciones.add(iniciarSesion);
        opciones.add(getDuenio().getMenuItemSalir());
        barramenu.add(opciones);
        
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JPanel panelCampos = new JPanel(new GridLayout(9, 2, 10, 5));
        JPanel panelSur = new JPanel(new BorderLayout());
        JPanel panelRegistrar = new JPanel();
        
        panelCampos.add(new JLabel("* CI"));
        panelCampos.add(ci);
        panelCampos.add(new JLabel("* Password"));
        panelCampos.add(password);
        panelCampos.add(new JLabel("* Nombre"));
        panelCampos.add(nombre);
        panelCampos.add(new JLabel("* Licencia"));
        panelCampos.add(licencia);
        panelCampos.add(new JLabel("* Telefono"));
        panelCampos.add(telefono);
        panelCampos.add(new JLabel("  Direccion"));
        panelCampos.add(direccion);
        panelCampos.add(new JLabel("  Lugar de Trabajo"));
        panelCampos.add(lugarTrabajo);
        panelCampos.add(new JLabel("  Ocupacion"));
        panelCampos.add(ocupacion);
        panelCampos.add(new JLabel("  Imagen "));
        Box caja = Box.createHorizontalBox();
        caja.add(dirImagen);
        caja.add(buscar);
        panelCampos.add(caja);
        
        panelRegistrar.add(registrar);
        panelSur.add(panelRegistrar, BorderLayout.NORTH);
        panelSur.add(barraMensaje, BorderLayout.SOUTH);
        
        add(panelCampos, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
        configurarComponentes();
    }
    
    public void cambiarTextoBarraMensaje(String texto, Color color)
    {
        barraMensaje.setForeground(color);
        barraMensaje.setText(texto);
        getDuenio().pack();
    }
    
    public Cliente crearClienteConDatos() throws DatosInvalidosException
    {
        Cliente cliente = Cliente.crearCliente(ci.getInt(), 
                Validador.decodificarPassword(password.getPassword()), 
                nombre.getText(), licencia.getChar(),
                telefono.getInt());
        cliente.setDireccion(direccion.getText());
        cliente.setLugarTrabajo(lugarTrabajo.getText());
        cliente.setOcupacion(ocupacion.getText());
        if(imagenUsuario != null)
            ManejadorImagen.escribirImagen(imagenUsuario, 
                    ManejadorImagen.FOLDER_USUARIOS, cliente.getLogin());
        return cliente;
    }
    
    private void configurarComponentes()
    {
        registrar.setEnabled(true);
        dirImagen.setEditable(false);
        dirImagen.setBorder(new EtchedBorder());
        
        ci.addKeyListener(this);
        password.addKeyListener(this);
        nombre.addKeyListener(this);
        licencia.addKeyListener(this);
        telefono.addKeyListener(this);
        buscar.addActionListener(this);
        buscar.setPreferredSize(new Dimension(15, 25));
        iniciarSesion.addActionListener(this);
        registrar.addActionListener(
                getDuenio().getListener(JDialogIngresar.LISTENER_BOTON_REGISTRAR));
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        Object fuente = event.getSource();
        if(fuente == buscar){
            cambiarTextoBarraMensaje(CAMPOS_OBLIGATORIOS, UsuarioGUI.NEGRO_ESTANDAR);
            imagenUsuario = ManejadorImagen.buscarImagen(new Buscador());
        }else if(fuente == iniciarSesion){
            getDuenio().cambiarPanel(new JPanelIngresar(getDuenio()));
        }else{
            throw new UnsupportedOperationException();
        }
    }
    
    private class Buscador implements ManejadorImagen.BuscarImagenListener
    {
        @Override
        public void mostrarMensaje(String mensaje, Color color)
        {
            cambiarTextoBarraMensaje(mensaje, color);
        }
        
        @Override
        public void mostrarNombreImagen(String nombre)
        {
            dirImagen.setText(nombre);
        }
        
        @Override
        public int getAltoMaximo()
        {
            return ManejadorImagen.MAX_TAMANIO_IMG_USUARIO;
        }
        
        @Override
        public int getAnchoMaximo()
        {
            return ManejadorImagen.MAX_TAMANIO_IMG_USUARIO;
        }
    }
    
    @Override
    public String toString()
    {
        return "Registrar";
    }
    
    @Override
    public void keyReleased(KeyEvent event)
    {
        cambiarTextoBarraMensaje(JPanelRegistrar.CAMPOS_OBLIGATORIOS, 
                UsuarioGUI.NEGRO_ESTANDAR);
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

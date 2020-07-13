package org.example.catalogovirtual.vista.cliente;

import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.cuerpo.utiles.FiltroNombre;
import org.example.catalogovirtual.modelo.cuerpo.utiles.FiltroDisponible;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Filtro;
import org.example.catalogovirtual.modelo.cuerpo.utiles.FiltroNroPasajeros;
import org.example.catalogovirtual.modelo.cuerpo.utiles.FiltroPrecio;
import org.example.catalogovirtual.modelo.cuerpo.utiles.FiltroModelo;
import org.example.catalogovirtual.modelo.cuerpo.utiles.FiltroPlaca;
import org.example.catalogovirtual.modelo.cuerpo.utiles.FiltroTipoDeCaja;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import org.example.catalogovirtual.vista.JTextNumber;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * JDialog que se muestra para filtrar los autos del catalogo.
 * 
 * @author empujesoft
 * @version 2015.06.29
 */
public class JDialogFiltrar extends JDialogAbstracto implements ActionListener, KeyListener
{
    private JComboBox categorias;
    private JTextField nombre;
    private JTextField placa;
    private JTextNumber modelo;
    private JTextNumber precio;
    private JTextNumber nroPasajeros;
    private JComboBox tipoDeCaja;
    private JComboBox disponible;
    private JButton filtrar;
    
    private JRadioButton radioNombre;
    private JRadioButton radioPlaca;
    private JRadioButton radioModelo;
    private JRadioButton radioPrecio;
    private JRadioButton radioNroPasajeros;
    private JRadioButton radioTipoDeCaja;
    private JRadioButton radioDisponible;
    
    public static final String[] TIPOS_DE_CAJAS = {"Automatico", "Manual"};
    public static final String[] TIPOS_DE_DISPONIBLES = {"Disponible", "No Disponible"};
    
    public JDialogFiltrar(JFrame duenio)
    {
        super(duenio, "Filtrar");
        
        categorias = new JComboBox(Autos.NOMBRES);
        nombre = new JTextField();
        placa = new JTextField();
        modelo = new JTextNumber(8, 4, false);
        precio = new JTextNumber(8, JTextNumber.CANT_CARACTERES_DEFECTO, true);
        nroPasajeros = new JTextNumber(8, 2, true);
        tipoDeCaja = new JComboBox(TIPOS_DE_CAJAS);
        disponible = new JComboBox(TIPOS_DE_DISPONIBLES);
        filtrar = new JButton("Filtrar");
        
        radioNombre = new JRadioButton();
        radioPlaca = new JRadioButton();
        radioModelo = new JRadioButton();
        radioPrecio = new JRadioButton();
        radioNroPasajeros = new JRadioButton();
        radioTipoDeCaja = new JRadioButton();
        radioDisponible = new JRadioButton();
    }
    
    @Override
    protected void construir()
    {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JPanel panelCategoria = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelFiltros = new JPanel();
        JPanel panelFiltrar = new JPanel();
        panelFiltrar.setBorder(new CompoundBorder(new EmptyBorder(0, 2, 2, 2), new EtchedBorder()));
        
        panelCategoria.add(new JLabel("Categoria"));
        panelCategoria.add(categorias);
        contruirCompFiltros(panelFiltros);
        panelFiltrar.add(filtrar);
        
        contenedor.add(panelCategoria, BorderLayout.NORTH);
        contenedor.add(panelFiltros, BorderLayout.CENTER);
        contenedor.add(panelFiltrar, BorderLayout.SOUTH);
        
        setContentPane(contenedor);
        pack();
        posicionarDialogo();
        configurarCompenentes();
    }
    
    private void configurarCompenentes()
    {        
        nombre.setEditable(false);
        placa.setEditable(false);
        modelo.setEditable(false);
        precio.setEditable(false);
        nroPasajeros.setEditable(false);
        tipoDeCaja.setEnabled(false);
        disponible.setEnabled(false);
        
        nombre.setBorder(new EtchedBorder());
        placa.setBorder(new EtchedBorder());
        modelo.setBorder(new EtchedBorder());
        precio.setBorder(new EtchedBorder());
        nroPasajeros.setBorder(new EtchedBorder());
        
        radioNombre.addActionListener(this);
        radioPlaca.addActionListener(this);
        radioModelo.addActionListener(this);
        radioPrecio.addActionListener(this);
        radioNroPasajeros.addActionListener(this);
        radioTipoDeCaja.addActionListener(this);
        radioDisponible.addActionListener(this);
        
        nombre.addKeyListener(this);
        placa.addKeyListener(this);
        modelo.addKeyListener(this);
        precio.addKeyListener(this);
        nroPasajeros.addKeyListener(this);
        
        categorias.setSelectedIndex(Autos.NUMERO_DE_CATEGORIAS);
        filtrar.setEnabled(false);
    }
    
    public void aniadirListenerBotonFiltrar(ActionListener listener)
    {
        filtrar.addActionListener(listener);
    }
    
    public void habilitarBotonFiltrar(boolean habilitar)
    {
        filtrar.setEnabled(habilitar);
    }
    
    public boolean sonCamposValidos()
    {
        return ((nombre.getText().length() > 0 || !esFiltroSelec(radioNombre))&&
               (placa.getText().length() > 0 || !esFiltroSelec(radioPlaca))&&
               (modelo.getText().length() > 0 || !esFiltroSelec(radioModelo))&&
               (precio.getText().length() > 0 || !esFiltroSelec(radioPrecio))&&
               (nroPasajeros.getText().length() > 0 || !esFiltroSelec(radioNroPasajeros))) &&
                hayFiltroSelec();
    }
    
    private boolean hayFiltroSelec()
    {
        return esFiltroSelec(radioNombre) || esFiltroSelec(radioPlaca) || esFiltroSelec(radioModelo) ||
                esFiltroSelec(radioPrecio) || esFiltroSelec(radioNroPasajeros) || 
                esFiltroSelec(radioTipoDeCaja) || esFiltroSelec(radioDisponible);
    }
    
    public int getCategoSelec()
    {
        return Autos.categoriaPorNombre((String)categorias.getSelectedItem());
    }
    
    public ArrayList<Filtro> crearFiltrosSelec()
    {
        ArrayList<Filtro> filtros = new ArrayList<>();
        if(esFiltroSelec(radioNombre)) filtros.add(new FiltroNombre(nombre.getText()));
        if(esFiltroSelec(radioPlaca)) filtros.add(new FiltroPlaca(placa.getText()));
        if(esFiltroSelec(radioModelo)) filtros.add(new FiltroModelo(modelo.getInt()));
        if(esFiltroSelec(radioPrecio)) filtros.add(new FiltroPrecio(precio.getDouble()));
        if(esFiltroSelec(radioNroPasajeros)) 
            filtros.add(new FiltroNroPasajeros(nroPasajeros.getInt()));
        if(esFiltroSelec(radioTipoDeCaja)) 
            filtros.add(new FiltroTipoDeCaja(getTextoComboBox(tipoDeCaja).equals("Automatico")));
        if(esFiltroSelec(radioDisponible)) 
            filtros.add(new FiltroDisponible(getTextoComboBox(disponible).equals("Disponible")));
        return filtros;
    }
    
    private boolean esFiltroSelec(JRadioButton filtro)
    {
        return filtro.isSelected();
    }
    
    private String getTextoComboBox(JComboBox combobox)
    {
        return ((String)combobox.getSelectedItem());
    }
    
    private void contruirCompFiltros(JPanel panelCentral)
    {
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setBorder(
            new CompoundBorder(new TitledBorder(new EtchedBorder(), " filtros "), 
                    new EmptyBorder(5, 5, 5, 5)));
        
        JPanel panelRadios = new JPanel(new GridLayout(7, 1, 2, 2));
        panelRadios.add(radioNombre);
        panelRadios.add(radioPlaca);
        panelRadios.add(radioModelo);
        panelRadios.add(radioPrecio);
        panelRadios.add(radioNroPasajeros);
        panelRadios.add(radioTipoDeCaja);
        panelRadios.add(radioDisponible);
        
        JPanel panelFiltros = new JPanel(new GridLayout(7, 2, 2, 2));
        panelFiltros.add(new JLabel("Nombre "));
        panelFiltros.add(nombre);
        panelFiltros.add(new JLabel("Placa "));
        panelFiltros.add(placa);
        panelFiltros.add(new JLabel("Modelo "));
        panelFiltros.add(modelo);
        panelFiltros.add(new JLabel("Precio "));
        panelFiltros.add(precio);
        panelFiltros.add(new JLabel("Nro de Pasajeros "));
        panelFiltros.add(nroPasajeros);
        panelFiltros.add(new JLabel("Tipo de Caja "));
        panelFiltros.add(tipoDeCaja);
        panelFiltros.add(new JLabel("Disponible "));
        panelFiltros.add(disponible);
        
        panelCentral.add(panelRadios, BorderLayout.WEST);
        panelCentral.add(panelFiltros, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        habilitarBotonFiltrar(sonCamposValidos());
        
        nombre.setEditable(radioNombre.isSelected());
        if(!radioNombre.isSelected()) nombre.setText("");
        placa.setEditable(radioPlaca.isSelected());
        if(!radioPlaca.isSelected()) placa.setText("");
        modelo.setEditable(radioModelo.isSelected());
        if(!radioModelo.isSelected()) modelo.setText("");
        precio.setEditable(radioPrecio.isSelected());
        if(!radioPrecio.isSelected()) precio.setText("");
        nroPasajeros.setEditable(radioNroPasajeros.isSelected());
        if(!radioNroPasajeros.isSelected()) nroPasajeros.setText("");
        tipoDeCaja.setEnabled(radioTipoDeCaja.isSelected());
        if(radioTipoDeCaja.isSelected()) tipoDeCaja.setSelectedIndex(0);
        disponible.setEnabled(radioDisponible.isSelected());
        if(radioDisponible.isSelected()) disponible.setSelectedIndex(0);
    }
    
    @Override
    public void keyReleased(KeyEvent event)
    {
        habilitarBotonFiltrar(sonCamposValidos());
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

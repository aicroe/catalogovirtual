package org.example.catalogovirtual.vista.cliente;

import org.example.catalogovirtual.controlador.JPanelClienteControlador;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.vista.JPanelAbstracto;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;


/**
 * JPanel mostrador contiene la tabla que muestra una vista general de los autos.
 * 
 * @author empujesoft
 * @version 2015.06.22
 */
public class JPanelMostrador extends JPanelAbstracto
{
    private JTable mostrador;
    private JButton verLista;
    private JButton filtrar;
    private JButton ordenar;
    
    public static final int TAMANIO_ESTANDAR_TABLA = 25;
    public static final int COLUMNA_PLACAS = 2;
    public static final String[] NOMBRES_COLUMNAS = 
            {"Categoria", "Nombre", "Placa", "Precio por Dia", "Disponible"};
    
    public JPanelMostrador()
    {
        mostrador = new JTable();
        verLista = new JButton("Ver Lista");
        filtrar = new JButton("Filtrar");
        ordenar = new JButton("Ordenar");
    }
    
    public JTable getMostrador()
    {
        return mostrador;
    }
    
    @Override
    public void construir()
    {
        removeAll();
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5 ,5), new EtchedBorder()));
        construirBarraHerramientas(this);
        construirTabla(this);
    }
    
    public void actualizarInfoTabla(ArrayList<Auto> autos)
    {
        String[][] informacion = new String[autos.size()][5];
        for(int i = 0; i< autos.size(); i++){
            Auto auto = autos.get(i);
            String[] infoAuto = new String[5];
            infoAuto[0] = auto.getNombreCategoria();
            infoAuto[1] = auto.getNombre();
            infoAuto[2] = auto.getPlaca();
            infoAuto[3] = String.valueOf(auto.getPrecioPorDia());
            infoAuto[4] = auto.estaDisponible()? "Disponible" : "No Disponible";
            informacion[i] = infoAuto;
        }
        DefaultTableModel modelo = ((DefaultTableModel)mostrador.getModel());
        modelo.setDataVector(informacion, NOMBRES_COLUMNAS);
        modelo.setRowCount((informacion.length > TAMANIO_ESTANDAR_TABLA)? 
                                    informacion.length : TAMANIO_ESTANDAR_TABLA);
    }
    
    public void actualizarInfoTabla(Vector<Vector<String>> informacion)
    {
        DefaultTableModel modelo = ((DefaultTableModel)mostrador.getModel());
        Vector<String> nombres = new Vector(Arrays.asList(NOMBRES_COLUMNAS));
            
        modelo.setDataVector(informacion, nombres);
        modelo.setRowCount((informacion.size() > TAMANIO_ESTANDAR_TABLA)? 
                                    informacion.size() : TAMANIO_ESTANDAR_TABLA);
    }
    
    public void setListeners(JPanelClienteControlador controlador)
    {
        verLista.addActionListener(controlador.new ListenerVerLista());
        filtrar.addActionListener(controlador.new ListenerFiltrar());
        ordenar.addActionListener(controlador.new ListenerOrdenar());
        mostrador.addKeyListener(controlador.new KeyListenerMostrador());
        mostrador.addMouseListener(controlador.new MouseListenerMostrador());
    }
    
    public Vector<Vector> getInfoTabla()
    {
        return ((DefaultTableModel)mostrador.getModel()).getDataVector();
    }
    
    public String getPlacaFilaSelec()
    {
        int fila = mostrador.getSelectedRow();
        if(fila >= 0){
            return (String)mostrador.getValueAt(fila, COLUMNA_PLACAS);
        }
        return null;
    }
    
    private void construirBarraHerramientas(JPanel contenedor)
    {
        JPanel barraHerramientas = new JPanel();
        ((FlowLayout)barraHerramientas.getLayout()).setAlignment(FlowLayout.LEFT);
        barraHerramientas.add(verLista);
        barraHerramientas.add(filtrar);
        barraHerramientas.add(ordenar);
        
        contenedor.add(barraHerramientas, BorderLayout.NORTH);
    }
    
    private void construirTabla(JPanel contenedor)
    {
        JPanel panelMostrador = new JPanel(new GridLayout(1, 1));
        
        DefaultTableModel tablaModelo = new TableModelNoEditable();
        tablaModelo.setColumnIdentifiers(NOMBRES_COLUMNAS);
        tablaModelo.setRowCount(TAMANIO_ESTANDAR_TABLA);
        mostrador.setModel(tablaModelo);
        mostrador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mostrador.setRowHeight(25);
        mostrador.setDragEnabled(false);
        mostrador.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane panelCorrediso = new JScrollPane(mostrador);
        panelMostrador.add(panelCorrediso);
        
        contenedor.add(panelMostrador, BorderLayout.CENTER);
    }
    
    private class TableModelNoEditable extends DefaultTableModel
    {
        @Override
        public boolean isCellEditable(int fil, int col)
        {
            return false;
        }
    }
}

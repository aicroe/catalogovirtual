/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.vista.admin;

import org.example.catalogovirtual.controlador.JPanelAdminControlador;
import org.example.catalogovirtual.modelo.nucleo.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author USER-UNO
 */
public class JPanelLista extends JPanel{
    private JPanel panelNorte;
    private JPanel panelSur;
    private JList autos;
    private JList clientes;
    private JButton insertar;
    
    public JPanelLista(){
        autos = new JList();
        clientes = new JList();
        panelNorte = new JPanel();
        panelSur = new JPanel();
        insertar = new JButton("Insertar");
    }
    
    public void construir(){
        setLayout(new BorderLayout());
        construirPanelNorte(this);
        construirPanelSur(this);
        configurarComponentes();
    }
    
    private void construirPanelNorte(JPanel contenedor){
        panelNorte.setLayout(new BorderLayout());
        panelNorte.setBorder(new TitledBorder(new EtchedBorder(), "Autos"));
        JScrollPane panelAutos = new JScrollPane(autos);
        panelNorte.add(panelAutos, BorderLayout.CENTER);
        panelNorte.add(insertar, BorderLayout.SOUTH);
        contenedor.add(panelNorte, BorderLayout.CENTER);
    }
    
    private void construirPanelSur(JPanel contenedor){
        panelSur.setLayout(new BorderLayout());
        panelSur.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 3, 0), 
                new TitledBorder(new EtchedBorder(), "Clientes")));
        JScrollPane panelClientes = new JScrollPane(clientes);
        panelSur.add(panelClientes, BorderLayout.CENTER);
        contenedor.add(panelSur, BorderLayout.SOUTH);
    }
    
    public Auto getAutoSelec(){
        
        return (Auto) autos.getSelectedValue();
    }
    
    public Cliente getClienteSelec(){
        
        return (Cliente) clientes.getSelectedValue();
    }
    
    public void actualizarAutos(ArrayList<Auto> autos){
        
        this.autos.setListData(autos.toArray());
    }
    
    public void actualizarClientes(ArrayList<Cliente> clientes){
        
        this.clientes.setListData(clientes.toArray());
    }
    
    private void configurarComponentes(){
        autos.setFixedCellWidth(150);
        clientes.setFixedCellWidth(150);
        autos.setDragEnabled(false);
        autos.setVisibleRowCount(10);
        autos.setVisible(true);
        clientes.setDragEnabled(false);
        clientes.setVisibleRowCount(10);
        clientes.setVisible(true);
    }
    
    public void setListeners(JPanelAdminControlador controlador){
        
        insertar.addActionListener(controlador.new ListenerInsertarAuto());
        autos.addMouseListener(controlador.new ListenerListaAutos(controlador));
        clientes.addMouseListener(controlador.new MouseListenerListaClientes());
        clientes.addKeyListener(controlador.new KeyListenerListaClientes());
    }
}
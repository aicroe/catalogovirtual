/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.vista.admin;

import org.example.catalogovirtual.vista.JDialogAbstracto;
import org.example.catalogovirtual.vista.UsuarioGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author garcia
 */
public class JDialogTerminar extends JDialogAbstracto{

    private JCheckBox dejarGarantia;
    private JTextArea observaciones;
    private JButton terminar;
    
    public JDialogTerminar(UsuarioGUI usuarioGui){
        
        super(usuarioGui, "Terminar Solicitud");
        dejarGarantia = new JCheckBox("Dejar la Garantia");
        observaciones = new JTextArea(8, 20);
        terminar = new JButton("Terminar");
    }
    
    public String getObservaciones(){
        
        return observaciones.getText();
    }
    
    public boolean seDejaLaGarantia(){
        
        return dejarGarantia.isSelected();
    }
    
    public void aniadirListenerTerminal(ActionListener listener){
        
        terminar.addActionListener(listener);
    }

    @Override
    public void construir(){
        
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        JPanel panelDejGaran = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDejGaran.add(dejarGarantia);
        JScrollPane panelObser = new JScrollPane(observaciones);
        panelObser.setBorder(new TitledBorder(new EtchedBorder(), "Observaciones"));
        observaciones.setBorder(new EtchedBorder());
        panelCentral.add(panelDejGaran);
        panelCentral.add(panelObser);
        
        JPanel panelSur = new JPanel();
        panelSur.add(terminar);
        
        contenedor.add(panelCentral, BorderLayout.CENTER);
        contenedor.add(panelSur, BorderLayout.SOUTH);
        setContentPane(contenedor);
        pack();
        posicionarDialogo();
    }
}

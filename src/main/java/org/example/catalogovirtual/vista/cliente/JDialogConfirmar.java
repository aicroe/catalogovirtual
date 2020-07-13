/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.vista.cliente;

import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * JDialog de confirmacion cuando se alquila un auto.
 *
 * @author empujesoft
 * @version 2015.08.16
 */
public class JDialogConfirmar extends JDialogAbstracto
{
    private JTextArea panelTexto;
    private JButton confirmar;
    private JButton cancelar;
    
    public JDialogConfirmar(JFrame duenio)
    {
        super(duenio, "Confirmar");
        panelTexto = new JTextArea(15, 20);
        confirmar = new JButton("Confirmar");
        cancelar = new JButton("Cancelar");
    }

    @Override
    protected void construir()
    {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane contendorTexto = new JScrollPane(panelTexto);
        contendorTexto.setBorder(new EtchedBorder());
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(confirmar);
        panelBotones.add(cancelar);
        
        contenedor.add(contendorTexto, BorderLayout.CENTER);
        contenedor.add(panelBotones, BorderLayout.SOUTH);
        
        configurarComponentes();
        setContentPane(contenedor);
        pack();
        posicionarDialogo();
    }
    
    private void configurarComponentes()
    {
        panelTexto.setEditable(false);
        cancelar.addActionListener((event)->{
            cerrarDialogo();
        });
    }
    
    public void aniadirListenerConfirmar(ActionListener listener)
    {
        confirmar.addActionListener(listener);
    }
    
    public void actualizarComponentes(Solicitud solicitud)
    {
        panelTexto.append(solicitud.toString());
    }
}

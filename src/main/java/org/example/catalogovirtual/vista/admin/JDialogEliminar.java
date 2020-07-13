package org.example.catalogovirtual.vista.admin;

import org.example.catalogovirtual.vista.JDialogAbstracto;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


/**
 * JDialog para eliminar un auto.
 * 
 * @author empujesoft
 * @version 2015.06.25
 */
public class JDialogEliminar extends JDialogAbstracto implements KeyListener
{
    private JTextField placa;
    private JButton eliminar;
    
    public JDialogEliminar(JFrame duenio)
    {
        super(duenio, "Eliminar");
        
        placa = new JTextField(8);
        eliminar = new JButton("Eliminar");
    }
    
    @Override
    protected void construir()
    {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(20, 15, 15, 15));
        
        JPanel panelMarcador = new JPanel();
        JPanel panelPlaca = new JPanel();
        JPanel panelEliminar = new JPanel();
        panelMarcador.add(new JLabel("Placa"));
        panelPlaca.add(placa);
        panelEliminar.add(eliminar);
        
        contenedor.add(panelMarcador, BorderLayout.WEST);
        contenedor.add(panelPlaca, BorderLayout.CENTER);
        contenedor.add(panelEliminar, BorderLayout.SOUTH);
        
        setContentPane(contenedor);
        pack();
        posicionarDialogo();
        eliminar.setEnabled(false);
        placa.addKeyListener(this);
    }
    
    public void aniadirListenerBotonEliminar(ActionListener listener)
    {
        eliminar.addActionListener(listener);
    }
    
    public String getPlacaInsertada()
    {
        return placa.getText();
    }
    
    public void habilitarBotonEliminar(boolean habilitar)
    {
        eliminar.setEnabled(habilitar);
    }
    
    @Override
    public void keyReleased(KeyEvent event)
    {
        habilitarBotonEliminar(getPlacaInsertada().length() > 0);
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

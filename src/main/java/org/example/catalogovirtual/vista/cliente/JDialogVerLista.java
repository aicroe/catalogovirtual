package org.example.catalogovirtual.vista.cliente;

import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.vista.JDialogAbstracto;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * JDialog vista para elegir la categoria de autos que sera vista en el mostrador.
 * 
 * @author empujesoft
 * @version 2015.06.27
 */
public class JDialogVerLista extends JDialogAbstracto
{
    private JComboBox categorias;
    private JButton ver;
    
    public JDialogVerLista(JFrame duenio)
    {
        super(duenio, "Ver Lista");
        
        categorias = new JComboBox(Autos.NOMBRES);
        ver = new JButton("Ver");
    }
    
    @Override
    protected void construir()
    {
        JPanel contenedor = new JPanel(new BorderLayout(5, 0));
        contenedor.setBorder(new EmptyBorder(15, 10, 10, 10));
        
        Box panelMarcadorCat = Box.createHorizontalBox();
        JPanel panelCategoria = new JPanel();
        JPanel panelVer = new JPanel();
        
        panelMarcadorCat.add(new JLabel("Categoria"));
        panelCategoria.add(categorias);
        panelVer.add(ver);
        
        contenedor.add(panelMarcadorCat, BorderLayout.WEST);
        contenedor.add(panelCategoria, BorderLayout.CENTER);
        contenedor.add(panelVer, BorderLayout.SOUTH);
        
        setContentPane(contenedor);
        pack();
        posicionarDialogo();
        categorias.setSelectedIndex(Autos.NUMERO_DE_CATEGORIAS);
    }
    
    public void aniadirListenerVerLista(ActionListener listener)
    {
        ver.addActionListener(listener);
    }
    
    public int getCategoSelec()
    {
        return Autos.categoriaPorNombre((String)categorias.getSelectedItem());
    }
}

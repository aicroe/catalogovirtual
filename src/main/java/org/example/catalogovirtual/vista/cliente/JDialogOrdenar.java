package org.example.catalogovirtual.vista.cliente;

import org.example.catalogovirtual.modelo.cuerpo.utiles.OrdenadorPrecio;
import org.example.catalogovirtual.modelo.cuerpo.utiles.OrdenadorPlaca;
import org.example.catalogovirtual.modelo.cuerpo.utiles.OrdenadorModelo;
import org.example.catalogovirtual.modelo.cuerpo.utiles.OrdenadorNombre;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Ordenador;
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
 * JDialog para ordenar la lista actual del mostrador.
 *
 * @author empujesoft
 * @version 2015.07.01
 */
public class JDialogOrdenar extends JDialogAbstracto
{
    private JComboBox ordenadores;
    private JButton ordenar;

    public static final Ordenador[] ORDENADORES = { new OrdenadorNombre(),
                                                    new OrdenadorPlaca(),
                                                    new OrdenadorModelo(),
                                                    new OrdenadorPrecio()};

    public JDialogOrdenar(JFrame duenio)
    {
        super(duenio, "Ordenar");

        ordenadores = new JComboBox(ORDENADORES);
        ordenar = new JButton("Ordenar");
    }

    @Override
    protected void construir()
    {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(15, 15, 15, 15));

        Box cajaMarcador = Box.createHorizontalBox();
        JPanel panelComboBox = new JPanel();
        JPanel panelBoton = new JPanel();

        cajaMarcador.add(new JLabel("Ordenar por"));
        panelComboBox.add(ordenadores);
        panelBoton.add(ordenar);

        contenedor.add(cajaMarcador, BorderLayout.WEST);
        contenedor.add(panelComboBox, BorderLayout.CENTER);
        contenedor.add(panelBoton, BorderLayout.SOUTH);

        setContentPane(contenedor);
        pack();
        posicionarDialogo();
    }

    public void aniadirListenerBotonOrdenar(ActionListener listener)
    {
        ordenar.addActionListener(listener);
    }

    public Ordenador getOrdenadorSelec()
    {
        return (Ordenador) ordenadores.getSelectedItem();
    }

}

package org.example.catalogovirtual.vista;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;



/**
 * Nuestro propio visor de imagenes.
 * 
 * @author empujesoft
 * @version 2015.06.14
 */
public class ImageView extends JComponent
{
    private BufferedImage imagen;
    private final Dimension tamanio;
    
    public ImageView(int alto, int ancho)
    {
        imagen = null;
        tamanio = new Dimension(alto, ancho);
    }
    
    public Image getImage()
    {
        return imagen;
    }
    
    public void setImage(BufferedImage imagen)
    {
        if(imagen != null){
            this.imagen = imagen;
            repaint();
        }
    }
    
    public void limpiarVista()
    {
        Graphics graficos = getGraphics();
        graficos.setColor(Color.WHITE);
        graficos.fillRect(0, 0, (int)tamanio.getWidth(), (int)tamanio.getHeight());
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return tamanio;
    }
    
    @Override
    public void paintComponent(Graphics graficos)
    {
        graficos.clearRect(0, 0, (int)tamanio.getWidth(), (int)tamanio.getHeight());
        if(imagen != null){
            int x = (int)(tamanio.getWidth() - imagen.getWidth())/2;
            int y = (int)(tamanio.getHeight() - imagen.getHeight())/2;
            graficos.drawImage(imagen, x, y, null);
        }
    }
}

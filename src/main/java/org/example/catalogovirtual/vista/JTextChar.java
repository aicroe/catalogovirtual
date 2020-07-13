package org.example.catalogovirtual.vista;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;


/**
 * JTextField especializado para un caracter
 * 
 * @author empujesoft
 * @version 2015.07.17
 */
public class JTextChar extends JTextField
{
    private char[] validos;
    
    public JTextChar(int columnas, char... caracteres)
    {
        super(columnas);
        validos = caracteres;
        addKeyListener(new KeyListenerUnCaracter());
    }
    
    public char getChar()
    {
        String texto = getText();
        if(texto.length() > 0){
            return texto.charAt(0);
        }
        return ' ';
    }
    
    private class KeyListenerUnCaracter extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent event) 
        {
            if(getText().length() == 1){
                event.consume();
                return;
            }
            char tecla = event.getKeyChar();
            boolean teclaValida = false;
            int i = 0;
            while(!teclaValida && i < validos.length){
                teclaValida = tecla == validos[i];
                i++;
            }
            if(!teclaValida){
                event.consume();
            }
            
        }
    }
}

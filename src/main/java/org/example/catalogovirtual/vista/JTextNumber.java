package org.example.catalogovirtual.vista;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;


/**
 * JTextField especializado a numeros
 * 
 * @author empujesoft
 * @version 2015.07.17
 */
public class JTextNumber extends JTextField
{
    private final int maxCantCaracteres;
    private boolean permitirPunto;
    
    public static final int CANT_CARACTERES_DEFECTO = 9;
    public static final int MAX_CANT_CARACTARES = 19;
    
    public JTextNumber(int columnas)
    {
        super(columnas);
        maxCantCaracteres = CANT_CARACTERES_DEFECTO;
        permitirPunto = false;
        addKeyListener(new KeyListenerSoloNumeros());
    }
    
    public JTextNumber(int columnas, int maxCantCaracteres, boolean permitirPunto)
    {
        super(columnas);
        if(maxCantCaracteres > 0 && maxCantCaracteres <= MAX_CANT_CARACTARES){
            this.maxCantCaracteres = maxCantCaracteres;
            this.permitirPunto = permitirPunto;
            addKeyListener(new KeyListenerSoloNumeros());
        }else{
            throw new IllegalArgumentException("maxCantCaracteres: " + maxCantCaracteres);
        }
    }
    
    public int getInt()
    {
        if(getText().length() == 0){
            return 0;
        }
        return Integer.parseInt(getText());
    }
    
    public double getDouble()
    {
        if(getText().length() == 0){
            return 0;
        }
        return Double.parseDouble(getText());
    }
    
    public long getLong()
    {
        if(getText().length() == 0){
            return 0;
        }
        return Long.parseLong(getText());
    }
    
    private class KeyListenerSoloNumeros extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent event)
        {
            char tecla = event.getKeyChar();
            if((tecla < '0' || tecla > '9') || !sePuedeInsertarMas()){
                if(!permitirPunto || (tecla != '.' || hayPunto(getText()))){
                    event.consume();
                    requestFocus();
                }
            }
        }
        
        private boolean sePuedeInsertarMas()
        {
            int tamanioTexto = getText().length();
            return tamanioTexto < maxCantCaracteres;
        }
        
        private boolean hayPunto(String texto)
        {
            int i = 0;
            while(i < texto.length()){
                if(texto.charAt(i) == '.'){
                    return true;
                }
                i++;
            }
            return false;
        }
    }
}

package org.example.catalogovirtual.modelo.cuerpo.utiles;

/**
 * Clase auxiliar que permite validar las cadenas introducidas por teclado 
 * para que sean utilizadas como password o ID de los usuarios.
 * 
 * @author Francisco Camacho
 * @version 14052015
 */
public class Validador
{
    
    public static boolean validar(String cadena){
        return validar(cadena.toCharArray());
    }
    
    private static boolean validar(char[] cadena){
        return validar(cadena, 0);
    } 
    
    private static boolean validar(char[] cadena, int pos){
        boolean valida = true;
        if(pos < cadena.length)
            if(!((cadena[pos]>='A')&&(cadena[pos]<='Z')))
                if(!((cadena[pos]>='a')&&(cadena[pos]<='z')))
                    if(!((cadena[pos]>='0')&&(cadena[pos]<='9'))){
                        valida = false;
                    }
                    else{   
                        pos++;
                        valida = validar(cadena, pos);
                    }
        return valida;
    }

    public static String decodificarPassword(char[] password) {
        
        String decodificado = "";
        for (int i = 0; i < password.length; i++) {
            decodificado += password[i];
        }
        return decodificado;
    }
    
    public static boolean verificarPlaca(String placa){
        
        int izq = 0;
        int der = placa.length() - 1;
        if(esNumero(placa.charAt(izq)) && esLetraMaysc(placa.charAt(der))){
            return verifFormatoNumeroLetras(izq + 1, der - 1, placa);
        }else if(esLetraMaysc(placa.charAt(izq)) && esNumero(placa.charAt(der))){
            return verifFormatoLetrasNumero(izq + 1, der - 1, placa);
        }else{
            return false;
        }
    }
    
    public static boolean verifFormatoNumeroLetras(int izq, int der, String placa){
        
        if(izq < der){
            if(esNumero(placa.charAt(izq)) && esLetraMaysc(placa.charAt(der))){
                return verifFormatoNumeroLetras(izq + 1, der - 1, placa);
            }else{
                return false;
            }
        }else{
            return izq + der >= 5;
        }
    }
    
    public static boolean verifFormatoLetrasNumero(int izq, int der, String placa){
        
        if(izq < der){
            if(esLetraMaysc(placa.charAt(izq)) && esNumero(placa.charAt(der))){
                return verifFormatoLetrasNumero(izq + 1, der - 1, placa);
            }else{
                return false;
            }
        }else{
            return izq + der >= 5;
        }
    }
    
    public static boolean esLetraMaysc(char c){
        
        return 'A' <= c && c <= 'Z';
    }
    
    public static boolean esNumero(char c){
        
        return '0' <= c && c <= '9';
    }
}
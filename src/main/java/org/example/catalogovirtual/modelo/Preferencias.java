/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.modelo;

import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import java.io.Serializable;

/**
 * Las preferncias del administrador.
 *
 * @author empujesoft
 */
public final class Preferencias implements Serializable{
    
    private static Raiz raiz;
    
    private Preferencias(){
    }
    
    public static Raiz getRaiz(){
        
        return raiz;
    }
    
    public static void setRaiz(Raiz unaRaiz){
        
        raiz = unaRaiz;
    }

    public static int getMaxNumDiasAlquiler() {
        
        return raiz.getMaxNumDiasAlquiler();
    }

    public static void setMaxNumDiasAlquiler(int aMaxNumDiasAlquiler) {
        
        raiz.maxNumDiasAlquiler = aMaxNumDiasAlquiler;
    }

    public static int getMaxNumDiasSolEnReserva() {
        
        return raiz.getMaxNumDiasSolEnReserva();
    }

    public static void setMaxNumDiasSolEnReserva(int aMaxNumDiasSolEnReserva) {
        
        raiz.maxNumDiasSolEnReserva = aMaxNumDiasSolEnReserva;
    }

    public static int getCantMaxSolCliente() {
        
        return raiz.getCantMaxSolCliente();
    }

    public static void setCantMaxSolCliente(int aCantMaxSolCliente) {
        
        raiz.cantMaxSolCliente = aCantMaxSolCliente;
    }
    
    public static void resetearPreferencias(){
        
        raiz = new Raiz();
    }
    
    public static class Raiz implements Serializable{
        
        private int maxNumDiasAlquiler;
        private int maxNumDiasSolEnReserva;
        private int cantMaxSolCliente;
        
        public Raiz(){
            
            maxNumDiasAlquiler = Solicitudes.MAX_NUM_DIAS_ALQUILER;
            maxNumDiasSolEnReserva = Solicitudes.MAX_NUM_DIAS_EN_RESERVA;
            cantMaxSolCliente = Solicitudes.LIMITE_SOLIC_CLIENTE;
        }

        public int getMaxNumDiasAlquiler() {
            
            return maxNumDiasAlquiler;
        }

        public int getMaxNumDiasSolEnReserva() {
            
            return maxNumDiasSolEnReserva;
        }

        public int getCantMaxSolCliente() {
            
            return cantMaxSolCliente;
        }
    }
}

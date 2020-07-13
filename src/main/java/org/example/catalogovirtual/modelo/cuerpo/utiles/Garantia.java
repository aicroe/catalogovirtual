/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.modelo.cuerpo.utiles;

import java.io.Serializable;

/**
 * Garantias.
 *
 * @author empujesoft
 * @version 2015.08.10
 */
public class Garantia implements Serializable{
    
    private static Garantia[] valores;
    
    private double garantia;
    private String nombre;

    private Garantia(double garantia, String nombre){
        
        this.garantia = garantia;
        this.nombre = nombre;
    }
    
    public double getGarantia(){
        
        return garantia;
    }
    
    public void setGarantia(double garantia){
        
        this.garantia = garantia;
    }
    
    @Override
    public String toString(){
        
        return nombre;
    }
    
    public static Garantia[] getValores(){
        
        return valores;
    }
    
    public static void cargarValores(Garantia[] garantias){
        
        valores = garantias;
    }
    
    public static Garantia getGarantia(int indice){
        
        return valores[indice];
    }
    
    public static void cargarValoresDefecto(){
        
        valores = new Garantia[Autos.NUMERO_DE_CATEGORIAS];
        valores[Autos.AUTOMOVIL] = new Garantia(
                Autos.GARANTIA_AUTOMOVIL, 
                Autos.NOMBRE_AUTOMOVIL);
        valores[Autos.CAMIONETA] = new Garantia(
                Autos.GARANTIA_CAMIONETA, 
                Autos.NOMBRE_CAMIONETA);
        valores[Autos.LIMOSINA] = new Garantia(
                Autos.GARANTIA_LIMOSINA, 
                Autos.NOMBRE_LIMOSINA);
        valores[Autos.VAGONETA] = new Garantia(
                Autos.GARANTIA_VAGONETA, 
                Autos.NOMBRE_VAGONETA);
    }
}

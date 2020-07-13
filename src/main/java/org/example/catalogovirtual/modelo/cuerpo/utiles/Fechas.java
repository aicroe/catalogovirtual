/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.modelo.cuerpo.utiles;

import java.util.Calendar;
import java.util.Date;

/**
 * Utiles para las fechas.
 *
 * @author empujesoft
 * @version 2015.08.16
 */
public final class Fechas
{
    
    public static int calcularDiasEntreFechas(Date fechaAnt, Date fechaPost){
        
        long tiempoInicial = fechaAnt.getTime();
        long tiempoFinal = fechaPost.getTime();
        long diferencia = tiempoFinal - tiempoInicial;
        return (int)(diferencia / (1000 * 3600 * 24));
    }

    public static double calcularPrecioTotal(
            Date fechaInicial, Date fechaFinal, double precioPorDia) {
        if (fechaFinal.after(fechaInicial)) {
            return calcularDiasEntreFechas(fechaInicial, fechaFinal) * precioPorDia;
        }
        return -1;
    }

    public static Date crearFecha(int dia, int mes, int anio) {
        
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, anio);
        calendario.set(Calendar.MONTH, mes);
        calendario.set(Calendar.DAY_OF_MONTH, dia);
        return new Fecha(calendario.getTime().getTime());
    }

    public static Date getFechaActual() {
        
        return new Fecha(Calendar.getInstance().getTimeInMillis());
    }
    
    public static int getAnioActual()
    {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    
    public static class Fecha extends Date
    {
        public Fecha(long time)
        {
            super(time);
        }
        
        @Override
        public String toString()
        {
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(this);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);
            int mes = calendario.get(Calendar.MONTH) + 1;
            int anio = calendario.get(Calendar.YEAR);
            return Fechas.completarCeros(2, dia) + " - " + 
                    Fechas.completarCeros(2, mes) + " - " +  
                    Fechas.completarCeros(4, anio);
        }
    }
    
    public static String completarCeros(int tamanio, int valor) {
        
        String texto = String.valueOf(valor);
        while (texto.length() < tamanio) {
            texto = "0" + texto;
        }
        return texto;
    }
    
    public static final String[] DIAS_DEL_MES = new String[]
                    {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", 
                    "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
                    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", 
                    "31"};
    
    public static enum Mes
    {
        ENERO("Ene", Calendar.JANUARY),
        FEBRERO("Feb", Calendar.FEBRUARY),
        MARZO("Mar", Calendar.MARCH), 
        ABRIL("Abr", Calendar.APRIL), 
        MAYO("May", Calendar.MAY), 
        JUNIO("Jun", Calendar.JUNE), 
        JULIO("Jul", Calendar.JULY), 
        AGOSTO("Ago", Calendar.AUGUST),
        SEPTIEMBRE("Sep", Calendar.SEPTEMBER), 
        OCTUBRE("Oct", Calendar.OCTOBER), 
        NOVIEMBRE("Nov", Calendar.NOVEMBER), 
        DICIEMBRE("Dic", Calendar.DECEMBER);
        
        private final String nombre;
        private final int valor;
        
        private Mes(String nombre, int valor)
        {
            this.nombre = nombre;
            this.valor = valor;
        }
        
        public int getValor()
        {
            return valor;
        }
        
        @Override
        public String toString()
        {
            return nombre;
        }
    }
}

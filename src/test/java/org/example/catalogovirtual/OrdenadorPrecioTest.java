package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.cuerpo.utiles.OrdenadorPrecio;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Automovil;
import org.example.catalogovirtual.modelo.nucleo.Camioneta;
import org.example.catalogovirtual.modelo.nucleo.Limosina;
import org.example.catalogovirtual.modelo.nucleo.Vagoneta;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class OrdenadorPrecioTest.
 *
 * @author  empujesoft
 */
public class OrdenadorPrecioTest
{
    
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
    }
    
    @Test
    public void ordenarListaVaciaTest(){
        OrdenadorPrecio ordenador = new OrdenadorPrecio();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
    
    @Test
    public void ordenarListaYaOrdenadaTest(){
        OrdenadorPrecio ordenador = new OrdenadorPrecio();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1, auto2, auto3;
        auto1 = new Automovil();
        auto2 = new Automovil();
        auto3 = new Automovil();
        auto1.setPrecioPorDia(100);
        auto2.setPrecioPorDia(150);
        auto3.setPrecioPorDia(200);
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
    
    @Test
    public void ordenarListaElmtosIguales(){
        OrdenadorPrecio ordenador = new OrdenadorPrecio();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1, auto2, auto3;
        auto1 = new Automovil();
        auto2 = new Automovil();
        auto3 = new Automovil();
        auto1.setPrecioPorDia(100);
        auto2.setPrecioPorDia(150);
        auto3.setPrecioPorDia(200);
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        
        assertEquals(ordenador.ordenar(lista).isEmpty(), false);        
    }
    
    @Test
    public void ordenarVariasCategoriasTest(){
        OrdenadorPrecio ordenador = new OrdenadorPrecio();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1 = new Automovil();
        Vagoneta auto2 = new Vagoneta();
        Camioneta auto3 = new Camioneta();
        Limosina auto4 = new Limosina();
        auto1.setPrecioPorDia(100);
        auto2.setPrecioPorDia(150);
        auto3.setPrecioPorDia(200);
        auto4.setPrecioPorDia(250);
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        lista.add(auto4);
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
}

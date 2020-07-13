package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.utiles.FiltroPrecio;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
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
 * The test class FiltroPlacaTest.
 *
 * @author  empujesoft
 */
public class FiltroPrecioTest
{
    
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
    }
    
    @Test
    public void filtrarDosAutosTest(){
        Automovil auto1, auto2;
        auto1 = new Automovil();
        auto1.setPrecioPorDia(150);
        auto2 = new Automovil();
        auto2.setPrecioPorDia(100);
        ArrayList<Auto> lista = new ArrayList<Auto>();
        lista.add(auto1);
        lista.add(auto2);
        FiltroPrecio filtro1 = new FiltroPrecio(150);
        FiltroPrecio filtro2 = new FiltroPrecio(100);
        FiltroPrecio filtro3 = new FiltroPrecio(200);
        ArrayList<Auto> listaFiltrada1 = new ArrayList<Auto>();
        listaFiltrada1.add(auto1);
        ArrayList<Auto> listaFiltrada2 = new ArrayList<Auto>();
        listaFiltrada2.add(auto2);
        
        assertEquals(filtro1.filtrar(lista), listaFiltrada1);
        assertEquals(filtro2.filtrar(lista), listaFiltrada2);
        assertEquals(filtro3.filtrar(lista).isEmpty(), true);
    }
    
    @Test
    public void filtrarListaVaciaTest(){
        ArrayList<Auto> lista = new ArrayList<Auto>();
        FiltroPrecio filtro = new FiltroPrecio(100);
        
        assertEquals(filtro.filtrar(lista).isEmpty(), true); 
    }
    
    @Test
    public void filtrarListaVariasCategoriasTest(){
        Automovil auto1 = new Automovil();
        Vagoneta auto2 = new Vagoneta();
        Camioneta auto3 = new Camioneta();
        Limosina auto4 = new Limosina();
        auto1.setPrecioPorDia(100);
        auto2.setPrecioPorDia(100);
        auto3.setPrecioPorDia(100);
        auto4.setPrecioPorDia(100);
        ArrayList<Auto> lista = new ArrayList<Auto>();
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        lista.add(auto4);
        FiltroPrecio filtro = new FiltroPrecio(100);
        
        assertEquals(filtro.filtrar(lista), lista);
    }
}

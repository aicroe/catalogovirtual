package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.cuerpo.utiles.OrdenadorPlaca;
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
 * The test class OrdenadorPlacaTest.
 *
 * @author  empujesoft
 */
public class OrdenadorPlacaTest
{
    
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
    }
    
    @Test
    public void ordenarListaVaciaTest(){
        OrdenadorPlaca ordenador = new OrdenadorPlaca();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
    
    @Test
    public void ordenarListaYaOrdenadaTest(){
        OrdenadorPlaca ordenador = new OrdenadorPlaca();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1, auto2, auto3;
        auto1 = new Automovil();
        auto2 = new Automovil();
        auto3 = new Automovil();
        auto1.setPlaca("A");
        auto2.setPlaca("B");
        auto3.setPlaca("C");
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
    
    @Test
    public void ordenarListaElmtosIguales(){
        OrdenadorPlaca ordenador = new OrdenadorPlaca();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1, auto2, auto3;
        auto1 = new Automovil();
        auto2 = new Automovil();
        auto3 = new Automovil();
        auto1.setPlaca("A");
        auto2.setPlaca("A");
        auto3.setPlaca("A");
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        
        assertEquals(ordenador.ordenar(lista).isEmpty(), false);        
    }
    
    @Test
    public void ordenarVariasCategoriasTest(){
        OrdenadorPlaca ordenador = new OrdenadorPlaca();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1 = new Automovil();
        Vagoneta auto2 = new Vagoneta();
        Camioneta auto3 = new Camioneta();
        Limosina auto4 = new Limosina();
        auto1.setPlaca("A");
        auto2.setPlaca("B");
        auto3.setPlaca("C");
        auto4.setPlaca("D");
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        lista.add(auto4);
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
}

package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.cuerpo.utiles.OrdenadorNombre;
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
 * The test class OrdenadorNombreTest.
 *
 * @author  empujesoft
 */
public class OrdenadorNombreTest
{
    
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
    }
    
    @Test
    public void ordenarListaVaciaTest(){
        OrdenadorNombre ordenador = new OrdenadorNombre();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
    
    @Test
    public void ordenarListaYaOrdenadaTest(){
        OrdenadorNombre ordenador = new OrdenadorNombre();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1, auto2, auto3;
        auto1 = new Automovil();
        auto2 = new Automovil();
        auto3 = new Automovil();
        auto1.setNombre("A");
        auto2.setNombre("B");
        auto3.setNombre("C");
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
    
    @Test
    public void ordenarListaElmtosIguales(){
        OrdenadorNombre ordenador = new OrdenadorNombre();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1, auto2, auto3;
        auto1 = new Automovil();
        auto2 = new Automovil();
        auto3 = new Automovil();
        auto1.setNombre("A");
        auto2.setNombre("A");
        auto3.setNombre("A");
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        
        assertEquals(ordenador.ordenar(lista).isEmpty(), false);        
    }
    
    @Test
    public void ordenarVariasCategoriasTest(){
        OrdenadorNombre ordenador = new OrdenadorNombre();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1 = new Automovil();
        Vagoneta auto2 = new Vagoneta();
        Camioneta auto3 = new Camioneta();
        Limosina auto4 = new Limosina();
        auto1.setNombre("A");
        auto2.setNombre("B");
        auto3.setNombre("C");
        auto4.setNombre("D");
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        lista.add(auto4);
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
}

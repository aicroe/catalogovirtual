package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.cuerpo.utiles.OrdenadorModelo;
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
 * The test class OrdenadorModeloTest.
 *
 * @author  empujesoft
 */
public class OrdenadorModeloTest
{
    
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
    }
    
    @Test
    public void ordenarListaVaciaTest(){
        OrdenadorModelo ordenador = new OrdenadorModelo();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
    
    @Test
    public void ordenarListaYaOrdenadaTest(){
        OrdenadorModelo ordenador = new OrdenadorModelo();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1, auto2, auto3;
        auto1 = new Automovil();
        auto2 = new Automovil();
        auto3 = new Automovil();
        auto1.setModelo(1998);
        auto2.setModelo(1999);
        auto3.setModelo(2000);
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
    
    @Test
    public void ordenarListaElmtosIguales(){
        OrdenadorModelo ordenador = new OrdenadorModelo();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1, auto2, auto3;
        auto1 = new Automovil();
        auto2 = new Automovil();
        auto3 = new Automovil();
        auto1.setModelo(2000);
        auto2.setModelo(2000);
        auto3.setModelo(2000);
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        
        assertEquals(ordenador.ordenar(lista).isEmpty(), false);        
    }
    
    @Test
    public void ordenarVariasCategoriasTest(){
        OrdenadorModelo ordenador = new OrdenadorModelo();
        ArrayList<Auto> lista = new ArrayList<Auto>();
        Automovil auto1 = new Automovil();
        Vagoneta auto2 = new Vagoneta();
        Camioneta auto3 = new Camioneta();
        Limosina auto4 = new Limosina();
        auto1.setModelo(2000);
        auto2.setModelo(2001);
        auto3.setModelo(2002);
        auto4.setModelo(2003);
        lista.add(auto1);
        lista.add(auto2);
        lista.add(auto3);
        lista.add(auto4);
        
        assertEquals(ordenador.ordenar(lista), lista);
    }
}

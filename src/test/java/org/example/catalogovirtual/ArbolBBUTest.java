package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.cuerpo.ArbolBBC;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.IDYaExistente;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The test class ArbolBBUTest.
 *
 * @author  empujesoft
 * @version 2015.07.21
 */
public class ArbolBBUTest
{
    
    @Test
    public void buscarElemento_ArbolVacio()
    {
        ArbolBBC arbol = new ArbolBBC();
        
        assertNull(arbol.buscarCliente("Goude"));
    }
    
    @Test
    public void buscarElemento_ParametroNulo()
    {
        ArbolBBC arbol = new ArbolBBC();
        
        assertNull(arbol.buscarCliente(null));
    }
    
    @Test
    public void buscarElemento_ArbolConElemtos_NoEncontrado() throws IDYaExistente, DatosInvalidosException
    {
        ArbolBBC arbol = new ArbolBBC();
        
        Cliente clienteA = Cliente.crearCliente(100000, "Poret", "Tada",'B', 12345678);
        Cliente clienteB = Cliente.crearCliente(111111, "Darr", "Carl", 'C', 32445577);
        Cliente clienteC = Cliente.crearCliente(222222, "Carro", "Andy", 'A', 66537787);
        Cliente clienteD = Cliente.crearCliente(333333, "Palo", "Maya", 'B', 23604455);
        
        arbol.insertarSR(clienteA);
        arbol.insertarSR(clienteB);
        arbol.insertarSR(clienteC);
        arbol.insertarSR(clienteD);
        
        assertNull(arbol.buscarCliente("000000"));
    }
    
    @Test
    public void buscarElemento_ArbolConElemtos_Encontrado() throws IDYaExistente, DatosInvalidosException
    {
        ArbolBBC arbol = new ArbolBBC();
        
        Cliente clienteA = Cliente.crearCliente(100000, "Poret", "Tada", 'B', 12345678);
        Cliente clienteB = Cliente.crearCliente(111111, "Darr", "Carl", 'C', 32445678);
        Cliente clienteC = Cliente.crearCliente(222222, "Carro", "Andy", 'A', 66535678);
        Cliente clienteD = Cliente.crearCliente(333333, "Palo", "Maya", 'B', 23605678);
        
        arbol.insertarSR(clienteA);
        arbol.insertarSR(clienteB);
        arbol.insertarSR(clienteC);
        arbol.insertarSR(clienteD);
        
        assertEquals(clienteB, arbol.buscarCliente("111111"));
    }
}

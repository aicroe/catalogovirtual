package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.cuerpo.ArbolBBSR;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.ElementoRepetidoException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The test class ArbolBBSRTest.
 *
 * @author  empujesoft
 * @version 2015.07.20
 */
public class ArbolBBSRTest
{
    
    @Test
    public void insertar_HerenciaRechazada()
    {
        try{
            ArbolBBSR<Integer> arbol = new ArbolBBSR<>();
            arbol.insertar(0);
            assert false;
        }catch(UnsupportedOperationException ex){
            assert true;
        }
    }
    
    @Test
    public void insertarSR_InsertarNulo_NoInsertado()
    {
        try{
            ArbolBBSR arbol = new ArbolBBSR();
            arbol.insertarSR(null);
            assertFalse(arbol.buscar(null));
        }catch(ElementoRepetidoException ex){
            assert false;
        }
    }
    
    @Test
    public void insertarSR_InsertarUnNumero_EstaEnLaRaiz()
    {
        try{
            ArbolBBSR<String> arbol = new ArbolBBSR<>();
            arbol.insertarSR("auto");
            assertEquals("auto", arbol.getRaiz());
        }catch(ElementoRepetidoException ex){
            assert false;
        }
    }
    
    @Test
    public void insertarSR_InsertarVariosSinRepetir_EstanEnElArbol()
    {
        try{
            ArbolBBSR<String> arbol = new ArbolBBSR<>();
            
            arbol.insertarSR("polca");
            arbol.insertarSR("teclado");
            arbol.insertarSR("matriz");
            arbol.insertarSR("sol");
            arbol.insertarSR("hoy");
            arbol.insertarSR("viajar");
            
            assertTrue(arbol.buscar("polca"));
            assertTrue(arbol.buscar("teclado"));
            assertTrue(arbol.buscar("matriz"));
            assertTrue(arbol.buscar("sol"));
            assertTrue(arbol.buscar("hoy"));
            assertTrue(arbol.buscar("viajar"));
        }catch(ElementoRepetidoException ex){
            assert false;
        }
    }
    
    @Test
    public void insertarSR_InsertarRepetido_NoInsertado()
    {
        ArbolBBSR<Integer> arbol = new ArbolBBSR<>();
        try{
            arbol.insertarSR(54);
            arbol.insertarSR(34);
            arbol.insertarSR(76);
            arbol.insertarSR(34);
            
            assert false;
        }catch(ElementoRepetidoException ex){
            assertTrue(arbol.eliminar(34));
            assertFalse(arbol.buscar(34));
        }
    }
    
    @Test
    public void insertarSR_InsertarDos_Repetidos()
    {
        try{
            ArbolBBSR<Integer> arbol = new ArbolBBSR<>();
            
            arbol.insertarSR(4);
            arbol.insertarSR(4);
            
            assert false;
        }catch(ElementoRepetidoException ex){
            assert true;
        }
    }
    
    @Test
    public void insertarSR_ArbolConElemtos_InsertarRepetido()
    {
        try{
            ArbolBBSR<Integer> arbol = new ArbolBBSR<>();
            
            arbol.insertarSR(54);
            arbol.insertarSR(34);
            arbol.insertarSR(76);
            arbol.insertarSR(33);
            arbol.insertarSR(45);
            arbol.insertarSR(455);
            arbol.insertarSR(13);
            arbol.insertarSR(33);
            
            assert false;
        }catch(ElementoRepetidoException ex){
            assert true;
        }
    }
}

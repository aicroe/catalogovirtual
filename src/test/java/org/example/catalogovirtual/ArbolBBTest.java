package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.ArbolBB;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.nucleo.Automovil;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import org.junit.Before;

/**
 * The test class ArbolBBTest.
 *
 * @author  empujesoft
 * @version 2015.07.20
 */
public class ArbolBBTest
{
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
    }
    
    @Test
    public void estaVacia_ArbolVacio()
    {
        ArbolBB arbol = new ArbolBB();
        
        assertTrue(arbol.estaVacia());
    }
    
    @Test
    public void estaVacia_ArbolNoVacio()
    {
        ArbolBB arbol = new ArbolBB();
        
        arbol.insertar(new Automovil());
        
        assertFalse(arbol.estaVacia());
    }
    
    @Test
    public void buscar_BuscarNuloArbolVacio_NuncaHayNulo()
    {
        ArbolBB arbol = new ArbolBB();
        
        assertFalse(arbol.buscar(null));
    }
    
    @Test
    public void buscar_ArbolVacio_NoPasaNada()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        assertFalse(arbol.buscar(1));
    }
    
    @Test
    public void buscar_BuscarNuloArbolConElemtos_NuncaHayNulo()
    {
        ArbolBB<String> arbol = new ArbolBB<>();
        
        arbol.insertar("Hola");
        arbol.insertar("Juan");
        arbol.insertar("Sal");
        arbol.insertar("Pereza");
        
        assertFalse(arbol.buscar(null));
    }
    
    @Test
    public void buscar_ArbolConElemtos_NoEstabaEnElArbol()
    {
        ArbolBB<String> arbol = new ArbolBB<>();
        
        arbol.insertar("Hola");
        arbol.insertar("Juan");
        arbol.insertar("Sal");
        arbol.insertar("Pereza");
        
        assertFalse(arbol.buscar("Romero"));
    }
    
    @Test
    public void buscar_BuscarUnicoElemto_Encontrado()
    {
        ArbolBB<String> arbol = new ArbolBB<>();
        
        arbol.insertar("Barco");
        
        assertTrue(arbol.buscar("Barco"));
    }
    
    @Test
    public void buscar_ArbolConElemtos_Encontrado()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        arbol.insertar(43);
        arbol.insertar(37);
        arbol.insertar(34);
        arbol.insertar(7);
        
        assertTrue(arbol.buscar(7));
    }
    
    @Test
    public void insertar_InsertarNulo_NoInsertado()
    {
        ArbolBB arbol = new ArbolBB();
        
        arbol.insertar(null);
        
        assertFalse(arbol.buscar(null));
    }
    
    @Test
    public void insertar_InsertarUnNumero_EstaEnLaRaiz()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        arbol.insertar(1);
        
        assertEquals(1, (int)arbol.getRaiz());
    }
    
    @Test
    public void insertar_InsertarCincoNumeros_EstanEnElArbol()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        arbol.insertar(7);
        arbol.insertar(3);
        arbol.insertar(9);
        arbol.insertar(1);
        arbol.insertar(4);
        
        assertTrue(arbol.buscar(7));
        assertTrue(arbol.buscar(3));
        assertTrue(arbol.buscar(9));
        assertTrue(arbol.buscar(1));
        assertTrue(arbol.buscar(4));
    }
    
    @Test
    public void contarElementos_ArbolVacio_CeroElmtos()
    {
        ArbolBB arbol = new ArbolBB();
        
        assertEquals(0, (int)arbol.contarElementosAlmacenados());
    }
    
    @Test
    public void contarElementos_ArbolConTresNumeros_CeroElmtos()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        arbol.insertar(9);
        arbol.insertar(0);
        arbol.insertar(7);
        
        assertEquals(3, (int)arbol.contarElementosAlmacenados());
    }
    
    @Test
    public void verificarArbolBB_ArbolConDiezElemtos()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(27);
        arbol.insertar(70);
        arbol.insertar(45);
        arbol.insertar(80);
        arbol.insertar(24);
        arbol.insertar(78);
        arbol.insertar(35);
        arbol.insertar(74);
        
        assertEquals(10, arbol.contarElementosAlmacenados());
        assertTrue(verificarArbolBB(arbol));
    }
    
    @Test
    public void verificarArbolBB_ArbolConVeinteElemtos()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        arbol.insertar(435);
        arbol.insertar(346);
        arbol.insertar(654);
        arbol.insertar(645);
        arbol.insertar(846);
        arbol.insertar(468);
        arbol.insertar(578);
        arbol.insertar(457);
        arbol.insertar(578);
        arbol.insertar(573);
        arbol.insertar(458);
        arbol.insertar(454);
        arbol.insertar(578);
        arbol.insertar(974);
        arbol.insertar(863);
        arbol.insertar(836);
        arbol.insertar(435);
        arbol.insertar(734);
        arbol.insertar(963);
        arbol.insertar(347);
        
        assertEquals(20, arbol.contarElementosAlmacenados());
        assertTrue(verificarArbolBB(arbol));
    }
    
    /**
     * Comprueva el buen funcionamiento del arbol binarion de busqueda. Se dice
     * que funciona bien cuando el arbol ordena los elementos que contiene de mayor a 
     * menor, teniendo como referencia la raiz o el primer elemento que fue insertado, 
     * los mayores o iguales a la raiz son colocados a la derecha, y los menores a 
     * la raiz son colocados a la izquierda y asi recursivamente. Si no estan ordenados
     * de esta manera tubo que ocurrir un mal funcionamiento en el arbol. Eso es lo que 
     * busca esta metodo.
     * 
     * @param arbol el arbol al que se quiere verificar el buen funcionamiento
     * @return devuelve true si no hay problemas, false si resulta que el arbol
     *          no esta funcionando bien y tiene a los elementos mal posicionados
     */
    private boolean verificarArbolBB(ArbolBB arbol)
    {
        if(arbol.estaVacia()){
            return true;
        }else{
            Comparable raiz = arbol.getRaiz();
            ArbolBB izq = arbol.getIzq();
            ArbolBB der = arbol.getDer();
            if(izq.estaVacia() && der.estaVacia()){
                return true;
            }else if(!izq.estaVacia()){
                return raiz.compareTo(izq.getRaiz()) > 0 && verificarArbolBB(izq);
            }else if(!der.estaVacia()){
                return raiz.compareTo(der.getRaiz()) <= 0 && verificarArbolBB(der);
            }else{
                return raiz.compareTo(izq.getRaiz()) > 0 && raiz.compareTo(der.getRaiz()) <= 0 && 
                    verificarArbolBB(izq) && verificarArbolBB(der);
            }
        }
    }
    
    @Test
    public void eliminar_EliminarNuloArbolVacio()
    {
        ArbolBB arbol = new ArbolBB();
        
        assertFalse(arbol.eliminar(null));
    }
    
    @Test
    public void eliminar_ArbolVacio_NoPasaNada()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        assertFalse(arbol.eliminar(9999));
    }
    
    @Test
    public void eliminar_EliminarNuloArbolConElemtos()
    {
        ArbolBB arbol = new ArbolBB();
        
        arbol.insertar("palo");
        arbol.insertar("autos");
        arbol.insertar("cometa");
        arbol.insertar("caja");
        arbol.insertar("portatil");
        
        assertFalse(arbol.eliminar(null));
    }
    
    @Test
    public void eliminar_ArbolConElemtos_NoEstabaEnElArbol()
    {
        ArbolBB arbol = new ArbolBB();
        
        arbol.insertar("palo");
        arbol.insertar("autos");
        arbol.insertar("cometa");
        arbol.insertar("caja");
        arbol.insertar("portatil");
        
        assertFalse(arbol.eliminar("correr"));
    }
    
    @Test
    public void eliminar_EliminarUnicoElemento()
    {
        ArbolBB<Integer> arbol = new ArbolBB();
        
        arbol.insertar(1);
        
        assertTrue(arbol.eliminar(1));
        assertTrue(verificarArbolBB(arbol));
    }
    
    @Test
    public void eliminar_ArbolConElemtosEliminarUno_Eliminado()
    {
        ArbolBB<Integer> arbol = new ArbolBB();
        
        arbol.insertar(64);
        arbol.insertar(34);
        arbol.insertar(74);
        arbol.insertar(36);
        arbol.insertar(18);
        arbol.insertar(84);
        
        assertTrue(arbol.eliminar(18));
        assertTrue(verificarArbolBB(arbol));
    }
    
    @Test
    public void eliminar_ArbolConElemtosEliminarVarios_Eliminados()
    {
        ArbolBB<Integer> arbol = new ArbolBB();
        
        arbol.insertar(64);
        arbol.insertar(34);
        arbol.insertar(74);
        arbol.insertar(36);
        arbol.insertar(18);
        arbol.insertar(84);
        
        assertTrue(arbol.eliminar(18));
        assertTrue(verificarArbolBB(arbol));
        assertTrue(arbol.eliminar(64));
        assertTrue(verificarArbolBB(arbol));
        assertTrue(arbol.eliminar(74));
        assertTrue(verificarArbolBB(arbol));
    }
    
    @Test
    public void eliminar_ArbolConElemtosEliminarTodos_ArbolVacio()
    {
        ArbolBB<Integer> arbol = new ArbolBB();
        
        arbol.insertar(64);
        arbol.insertar(34);
        arbol.insertar(74);
        arbol.insertar(36);
        arbol.insertar(18);
        arbol.insertar(84);
        
        assertTrue(arbol.eliminar(18));
        assertTrue(verificarArbolBB(arbol));
        assertTrue(arbol.eliminar(64));
        assertTrue(verificarArbolBB(arbol));
        assertTrue(arbol.eliminar(74));
        assertTrue(verificarArbolBB(arbol));
        assertTrue(arbol.eliminar(36));
        assertTrue(verificarArbolBB(arbol));
        assertTrue(arbol.eliminar(34));
        assertTrue(verificarArbolBB(arbol));
        assertTrue(arbol.eliminar(84));
        assertTrue(verificarArbolBB(arbol));
        assertTrue(arbol.estaVacia());
    }
    
    @Test
    public void eliminar_EliminarRaizHoja()
    {
        ArbolBB<String> arbol = new ArbolBB();
        
        arbol.insertar("Armando");
        
        assertTrue(arbol.eliminar("Armando"));
        assertTrue(verificarArbolBB(arbol));
    }
    
    @Test
    public void eliminar_EliminarRaizConHijoIzquierdo_NuevaRaizHijoIzq()
    {
        ArbolBB<String> arbol = new ArbolBB();
        
        arbol.insertar("Clara");
        arbol.insertar("Ana");
        
        assertTrue(arbol.eliminar("Clara"));
        assertTrue(verificarArbolBB(arbol));
        assertEquals("Ana", arbol.getRaiz());
    }
    
    @Test
    public void eliminar_EliminarRaizConHijoDerechi_NuevaRaizHijoDer()
    {
        ArbolBB<String> arbol = new ArbolBB();
        
        arbol.insertar("Pedro");
        arbol.insertar("Walter");
        
        assertTrue(arbol.eliminar("Pedro"));
        assertTrue(verificarArbolBB(arbol));
        assertEquals("Walter", arbol.getRaiz());
    }
    
    @Test
    public void eliminar_EliminarRaizConDosHijos_NuevaRaizHijoIzq()
    {
        ArbolBB<String> arbol = new ArbolBB();
        
        arbol.insertar("Carolina");
        arbol.insertar("Amador");
        arbol.insertar("Laura");
        
        assertTrue(arbol.eliminar("Carolina"));
        assertTrue(verificarArbolBB(arbol));
        assertEquals("Amador", arbol.getRaiz());
    }
    
    @Test
    public void eliminar_EliminarRaizConDosHijos_NuevaRaizMayorArbolIzq()
    {
        ArbolBB<String> arbol = new ArbolBB();
        
        arbol.insertar("Sophia");
        arbol.insertar("Marcos");
        arbol.insertar("Zenin");
        arbol.insertar("Kenny");
        arbol.insertar("Nana");
        
        assertTrue(arbol.eliminar("Sophia"));
        assertTrue(verificarArbolBB(arbol));
        assertEquals("Nana", arbol.getRaiz());
    }
    
    @Test
    public void insertar_InsertarRepetido_Insertado()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        arbol.insertar(5);
        arbol.insertar(7);
        arbol.insertar(4);
        arbol.insertar(8);
        arbol.insertar(7);
        
        assertTrue(arbol.buscar(7));
        assertTrue(arbol.eliminar(7));
        assertTrue(arbol.buscar(7));
    }
    
    @Test
    public void elementosEnInOrden_ArbolVacio()
    {
        ArbolBB arbol = new ArbolBB();
        
        assertTrue(arbol.elementosEnInOrden().isEmpty());
    }
    
    @Test
    public void elementosEnInOrden_ArbolConElemtos()
    {
        ArbolBB arbol = new ArbolBB();
        
        arbol.insertar("camaron");
        arbol.insertar("carlos");
        arbol.insertar("conejo");
        arbol.insertar("calvo");
        
        ArrayList elemtos = arbol.elementosEnInOrden();
        assertEquals("calvo", elemtos.get(0));
        assertEquals("camaron", elemtos.get(1));
        assertEquals("carlos", elemtos.get(2));
        assertEquals("conejo", elemtos.get(3));
    }
    
    @Test
    public void elementosEnBFS_ArbolVacio()
    {
        ArbolBB arbol = new ArbolBB();
        
        assertTrue(arbol.elementosEnBFS().isEmpty());
    }
    
    @Test
    public void elementosEnBFS_ArbolConElemtos()
    {
        ArbolBB<Integer> arbol = new ArbolBB<>();
        
        arbol.insertar(9);
        arbol.insertar(4);
        arbol.insertar(3);
        arbol.insertar(5);
        arbol.insertar(10);
        arbol.insertar(20);
        arbol.insertar(11);
        arbol.insertar(9);
        
        ArrayList elemtos = arbol.elementosEnBFS();
        assertEquals(9, elemtos.get(0));
        assertEquals(4, elemtos.get(1));
        assertEquals(10, elemtos.get(2));
        assertEquals(3, elemtos.get(3));
        assertEquals(5, elemtos.get(4));
        assertEquals(9, elemtos.get(5));
        assertEquals(20, elemtos.get(6));
        assertEquals(11, elemtos.get(7));
    }
}
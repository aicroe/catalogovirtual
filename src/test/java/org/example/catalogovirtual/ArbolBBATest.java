package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.ArbolBBA;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.PlacaRepetidaException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Automovil;
import org.example.catalogovirtual.modelo.nucleo.Camioneta;
import org.example.catalogovirtual.modelo.nucleo.Limosina;
import org.example.catalogovirtual.modelo.nucleo.Vagoneta;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


/**
 * The test class ArbolBBATest.
 *
 * @author  empujesoft
 * @version 2015.07.20
 */
public class ArbolBBATest
{
    
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
    }
    
    @Test 
    public void arbolInsertarUnAuto() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Vagoneta cuatroxcuatro = new Vagoneta();
        cuatroxcuatro.setModelo(2009);
        cuatroxcuatro.setNombre("4x4");
        cuatroxcuatro.setNumeroDePasajeros(4);
        cuatroxcuatro.setPlaca("681BWS");
        cuatroxcuatro.setPrecioPorDia(4000);
        cuatroxcuatro.setTipoDeCaja(false);
        
        arbol.insertarSR(cuatroxcuatro);
        
        assertTrue(arbol.buscar(cuatroxcuatro));
    }
    
    @Test
    public void arbolInsertaDosAutos() throws PlacaRepetidaException
    {
        ArbolBBA camionetas = new ArbolBBA();
        
        Camioneta camionero = new Camioneta();
        camionero.setModelo(1999);
        camionero.setNombre("Camionero");
        camionero.setNumeroDePasajeros(8);
        camionero.setPlaca("143VSE");
        camionero.setPrecioPorDia(7000);
        camionero.setTipoDeCaja(false);
        
        Camioneta tank = new Camioneta();
        tank.setModelo(2012);
        tank.setNombre("Tank");
        tank.setNumeroDePasajeros(4);
        tank.setPlaca("798ERT");
        tank.setPrecioPorDia(6000);
        tank.setTipoDeCaja(false);
        
        camionetas.insertarSR(camionero);
        camionetas.insertarSR(tank);
        
        assertTrue(camionetas.buscar(camionero));
        assertTrue(camionetas.buscar(tank));
    }
    
    @Test
    public void arbolInsertarTresAutos() throws PlacaRepetidaException
    {
        ArbolBBA automoviles = new ArbolBBA();
        
        Automovil corola = new Automovil();
        corola.setModelo(2000);
        corola.setNombre("Toyota Corola");
        corola.setNumeroDePasajeros(4);
        corola.setPlaca("944HFD");
        corola.setPrecioPorDia(1000);
        corola.setTipoDeCaja(true);
        
        Automovil tiger = new Automovil();
        tiger.setModelo(2012);
        tiger.setNombre("Toyota Tiger");
        tiger.setNumeroDePasajeros(6);
        tiger.setPlaca("654GHD");
        tiger.setPrecioPorDia(1100);
        tiger.setTipoDeCaja(true);
        
        Automovil horse = new Automovil();
        horse.setModelo(1990);
        horse.setNombre("Toyota Horse");
        horse.setNumeroDePasajeros(4);
        horse.setPlaca("754YUH");
        horse.setPrecioPorDia(5000);
        horse.setTipoDeCaja(false);
        
        automoviles.insertarSR(corola);
        automoviles.insertarSR(tiger);
        automoviles.insertarSR(horse);
        
        assertTrue(automoviles.buscar(corola));
        assertTrue(automoviles.buscar(tiger));
        assertTrue(automoviles.buscar(horse));
    }
    
    @Test
    public void insertar_VerOrdenElmtos() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("565DSFG");
        autoB.setPlaca("366HGG");
        autoC.setPlaca("665DSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertEquals(autoA, arbol.getRaiz());
        assertEquals(autoB, arbol.getIzq().getRaiz());
        assertEquals(autoC, arbol.getDer().getRaiz());
    }
    
    @Test
    public void insertar_InsercionHijosEnDiferenteOrden_VerOrdenElmtos() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("565DSFG");
        autoB.setPlaca("366HGG");
        autoC.setPlaca("665DSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoC);
        arbol.insertarSR(autoB);
        
        assertEquals(autoA, arbol.getRaiz());
        assertEquals(autoB, arbol.getIzq().getRaiz());
        assertEquals(autoC, arbol.getDer().getRaiz());
    }
    
    @Test
    public void eliminarPorPlaca_ArbolVacio_NoPasaNada() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Auto auto = new Automovil();
        auto.setPlaca("HOLA800");
        arbol.insertarSR(auto);
        
        assertTrue(arbol.eliminarPorPlaca("HOLA800"));
        assertTrue(arbol.estaVacia());
    }
    
    @Test
    public void eliminarPorPlaca_PlacaNula_NoPasaNada()
    {
        ArbolBBA arbol = new ArbolBBA();
        
        assertFalse(arbol.eliminarPorPlaca(null));
    }
    
    @Test
    public void eliminarPorPlaca_ArbolConUnElmto_ArbolVacio() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil auto = new Automovil();
        auto.setPlaca("432GAS");
        
        arbol.insertarSR(auto);
        assertTrue(arbol.eliminarPorPlaca(auto.getPlaca()));
        assertTrue(arbol.estaVacia());
    }
    
    @Test
    public void eliminarPorPlaca_ArbolConUnElmto_VerSiNoSeRompioNada() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil auto = new Automovil();
        auto.setPlaca("432GAS");
        arbol.insertarSR(auto);
        
        assertTrue(arbol.eliminarPorPlaca(auto.getPlaca()));
        assertNull(arbol.getRaiz());
        assertNull(arbol.getDer());
        assertNull(arbol.getIzq());
    }
    
    @Test
    public void elminarPorPlaca_EliminarHojaDeLaIqz_Exito() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        autoC.setPlaca("445GSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertTrue(arbol.eliminarPorPlaca(autoC.getPlaca()));
        assertFalse(arbol.buscar(autoC));
        assertTrue(arbol.buscar(autoA));
        assertTrue(arbol.buscar(autoB));
    }
    
    @Test
    public void elminarPorPlaca_EliminarHojaDeLaIqz_VerSiNoSeRompioNada() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        autoC.setPlaca("445GSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertTrue(arbol.eliminarPorPlaca(autoC.getPlaca()));
        assertFalse(arbol.buscar(autoC));
        assertNotNull(arbol.getIzq());
        assertNotNull(arbol.getDer());
    }
    
    @Test
    public void elminarPorPlaca_EliminarHojaDeLaDer_Exito() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        autoC.setPlaca("445GSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertTrue(arbol.eliminarPorPlaca(autoB.getPlaca()));
        assertFalse(arbol.buscar(autoB));
        assertTrue(arbol.buscar(autoA));
        assertTrue(arbol.buscar(autoC));
    }
    
    @Test
    public void elminarPorPlaca_EliminarHojaDeLaDer_VerSinNoSeRompioNada() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        autoC.setPlaca("445GSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertTrue(arbol.eliminarPorPlaca(autoB.getPlaca()));
        assertFalse(arbol.buscar(autoB));
        assertNotNull(arbol.getIzq());
        assertNotNull(arbol.getDer());
    }
    
    @Test
    public void elminarPorPlaca_EliminarRaizConHijoDerecho_Exito() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        
        assertTrue(arbol.eliminarPorPlaca(autoA.getPlaca()));
        assertFalse(arbol.buscar(autoA));
        assertTrue(arbol.buscar(autoB));
    }
    
    @Test
    public void elminarPorPlaca_EliminarRaizConHijoDerecho_VerSiNoSeRompioNada() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        
        assertTrue(arbol.eliminarPorPlaca(autoA.getPlaca()));
        assertEquals(autoB, arbol.getRaiz());
        assertTrue(arbol.getIzq().estaVacia());
        assertTrue(arbol.getDer().estaVacia());
    }
    
    @Test
    public void elminarPorPlaca_EliminarRaizConHijoIzquierdo_Exito() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        autoA.setPlaca("7667DAF");
        autoB.setPlaca("645DSAF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        
        assertTrue(arbol.eliminarPorPlaca(autoA.getPlaca()));
        assertFalse(arbol.buscar(autoA));
        assertTrue(arbol.buscar(autoB));
    }
    
    @Test
    public void elminarPorPlaca_EliminarRaizConHijoIzquierdo_VerSiNoSeRompioNada() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        autoA.setPlaca("7667DAF");
        autoB.setPlaca("645DSAF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        
        assertTrue(arbol.eliminarPorPlaca(autoA.getPlaca())); 
        assertEquals(autoB, arbol.getRaiz());
        assertTrue(arbol.getIzq().estaVacia());
        assertTrue(arbol.getDer().estaVacia());
    }
    
    @Test
    public void elminarPorPlaca_EliminarRaizConDosHijos_Exito() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        autoC.setPlaca("445GSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertTrue(arbol.eliminarPorPlaca(autoA.getPlaca()));
        assertFalse(arbol.buscar(autoA));
        assertTrue(arbol.buscar(autoB));
        assertTrue(arbol.buscar(autoC));
    }
    
    @Test
    public void elminarPorPlaca_EliminarRaizConDosHijos_VerSiNoSeRompioNada() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        autoC.setPlaca("445GSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertTrue(arbol.eliminarPorPlaca(autoA.getPlaca()));
        assertEquals(autoC, arbol.getRaiz());
        assertTrue(arbol.getIzq().estaVacia());
        assertFalse(arbol.getDer().estaVacia());
    }
    
    @Test
    public void eliminarPorPlaca_EliminarTodosLosElmtos() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        autoC.setPlaca("445GSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertTrue(arbol.eliminarPorPlaca(autoA.getPlaca()));
        assertTrue(arbol.eliminarPorPlaca(autoB.getPlaca()));
        assertTrue(arbol.eliminarPorPlaca(autoC.getPlaca()));
        assertTrue(arbol.estaVacia());
    }
    
    @Test
    public void eliminarPorPlaca_EliminarTodosLosElmtos_VerSiNoSeRompio() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Automovil autoA = new Automovil();
        Automovil autoB = new Automovil();
        Automovil autoC = new Automovil();
        autoA.setPlaca("645DSAF");
        autoB.setPlaca("7667DAF");
        autoC.setPlaca("445GSF");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertTrue(arbol.eliminarPorPlaca(autoA.getPlaca()));
        assertTrue(arbol.eliminarPorPlaca(autoB.getPlaca()));
        assertTrue(arbol.eliminarPorPlaca(autoC.getPlaca()));
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        
        assertEquals(autoA, arbol.getRaiz());
        assertEquals(autoB, arbol.getDer().getRaiz());
        assertEquals(autoC, arbol.getIzq().getRaiz());
    }
    
    @Test
    public void eliminarPorPlaca_AutoNoDisponible_NoEliminado() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Auto auto = new Automovil();
        auto.setDisponible(false);
        auto.setPlaca("344HGF");
        arbol.insertarSR(auto);
        
        assertFalse(arbol.eliminarPorPlaca(auto.getPlaca()));
        assertTrue(arbol.buscar(auto));
    }
    
    @Test
    public void eliminarForzadamente_Disponible_Eliminado() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Auto auto = new Automovil();
        auto.setPlaca("344HGF");
        arbol.insertarSR(auto);
        
        assertTrue(arbol.eliminarForzadamente(auto.getPlaca()));
        assertFalse(arbol.buscar(auto));
    }
    
    @Test
    public void eliminarForzadamente_AutoNoDisponible_Eliminado() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Auto auto = new Automovil();
        auto.setDisponible(false);
        auto.setPlaca("344HGF");
        arbol.insertarSR(auto);
        
        assertTrue(arbol.eliminarForzadamente(auto.getPlaca()));
        assertFalse(arbol.buscar(auto));
    }
    
    @Test
    public void getAutoPorPlaca_ArbolVacio_Nulo()
    {
        ArbolBBA arbol = new ArbolBBA();
        
        assertNull(arbol.getAutoPorPlaca("434FGD"));
    }
    
    @Test
    public void getAutoPorPlaca_PlacaNula_Nulo()
    {
        ArbolBBA arbol = new ArbolBBA();
        
        assertNull(arbol.getAutoPorPlaca(null));
    }
    
    @Test
    public void getAutoPorPlaca_ArbolConElemtos_Nulo() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Auto autoA = new Automovil();
        Auto autoB = new Camioneta();
        Auto autoC = new Vagoneta();
        Auto autoD = new Limosina();
        
        autoA.setPlaca("RET584");
        autoB.setPlaca("NGF350");
        autoC.setPlaca("RQE046");
        autoD.setPlaca("RTT537");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        arbol.insertarSR(autoD);
        
        assertNull(arbol.getAutoPorPlaca("WERT324"));
    }
    
    @Test
    public void getAutoPorPlaca_ArbolConElemtos_Encontrado() throws PlacaRepetidaException
    {
        ArbolBBA arbol = new ArbolBBA();
        
        Auto autoA = new Automovil();
        Auto autoB = new Camioneta();
        Auto autoC = new Vagoneta();
        Auto autoD = new Limosina();
        
        autoA.setPlaca("RET584");
        autoB.setPlaca("NGF350");
        autoC.setPlaca("RQE046");
        autoD.setPlaca("RTT537");
        
        arbol.insertarSR(autoA);
        arbol.insertarSR(autoB);
        arbol.insertarSR(autoC);
        arbol.insertarSR(autoD);
        
        assertEquals(autoC, arbol.getAutoPorPlaca(autoC.getPlaca()));
    }
    
}


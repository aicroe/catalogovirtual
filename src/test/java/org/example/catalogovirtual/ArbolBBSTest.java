package org.example.catalogovirtual;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.ArbolBBS;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Fechas;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Automovil;
import org.example.catalogovirtual.modelo.nucleo.Camioneta;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.modelo.nucleo.Limosina;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import org.example.catalogovirtual.modelo.nucleo.Vagoneta;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author garcia
 */
public class ArbolBBSTest {
    
    private Cliente cliente;
    private Auto automovil;
    private Auto camioneta;
    private Auto vagoneta;
    private Auto limosina;
    private Date hoy;
    private Date maniana;
    private Date ayer;
    
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
        
        try {
            cliente = Cliente.crearCliente(20150000, "0000", "Cliente", 'A', 70000000);
            
            automovil = new Automovil();
            automovil.setPlaca("QWE435");
            automovil.setNombre("Automovil");
            automovil.setModelo(2000);
            automovil.setNumeroDePasajeros(4);
            automovil.setPrecioPorDia(1000);
            
            camioneta = new Camioneta();
            camioneta.setPlaca("POU123");
            camioneta.setNombre("Camioneta");
            camioneta.setModelo(2001);
            camioneta.setNumeroDePasajeros(6);
            camioneta.setPrecioPorDia(1100);
            
            vagoneta = new Vagoneta();
            vagoneta.setPlaca("TYU864");
            vagoneta.setNombre("Vagoneta");
            vagoneta.setModelo(2014);
            vagoneta.setNumeroDePasajeros(4);
            vagoneta.setPrecioPorDia(2000);
            vagoneta.setTipoDeCaja(true);
            
            limosina = new Limosina();
            limosina.setPlaca("MGS237");
            limosina.setNombre("Limosina");
            limosina.setModelo(2015);
            limosina.setNumeroDePasajeros(2);
            limosina.setPrecioPorDia(3000);
            limosina.setTipoDeCaja(true);
            
            hoy = new GregorianCalendar(2015, 9, 5).getTime();
            maniana = new GregorianCalendar(2015, 9, 6).getTime();
            ayer = new GregorianCalendar(2015, 9, 4).getTime();
            
        } catch (DatosInvalidosException ex) {
            throw new UnsupportedOperationException();
        }
    }
    
    @Test
    public void accederSolicitud_ArbolVacio(){
        
        ArbolBBS arbol = new ArbolBBS();
        
        assertNull(arbol.accederSolicitud(hoy, maniana, automovil));
    }
    
    @Test
    public void accederSolicitud_NoEsta(){
        
        ArbolBBS arbol = new ArbolBBS();
        Solicitud solicitud = new Solicitud(automovil, cliente);
        solicitud.setFechaInicio(hoy);
        solicitud.setFechaFinal(maniana);
        solicitud.setPrecioTotal(Fechas.calcularPrecioTotal(hoy, maniana, automovil.getPrecioPorDia()));
        
        arbol.insertar(solicitud);
        
        assertNull(arbol.accederSolicitud(ayer, hoy, camioneta));
    }
    
    @Test
    public void accederSolicitud_SiEsta(){
        
        ArbolBBS arbol = new ArbolBBS();
        Solicitud solicitud = new Solicitud(automovil, cliente);
        solicitud.setFechaInicio(hoy);
        solicitud.setFechaFinal(maniana);
        solicitud.setPrecioTotal(Fechas.calcularPrecioTotal(hoy, maniana, automovil.getPrecioPorDia()));
        
        arbol.insertar(solicitud);
        
        assertEquals(solicitud, arbol.accederSolicitud(hoy, maniana, automovil));
    }
    
    @Test
    public void accederSolicitud_ArbolConElmts(){
        
        ArbolBBS arbol = new ArbolBBS();
        Solicitud sol1 = new Solicitud(automovil, cliente);
        sol1.setFechaInicio(hoy);
        sol1.setFechaFinal(maniana);
        sol1.setPrecioTotal(Fechas.calcularPrecioTotal(hoy, maniana, automovil.getPrecioPorDia()));
        
        Solicitud sol2 = new Solicitud(camioneta, cliente);
        sol2.setFechaInicio(ayer);
        sol2.setFechaFinal(maniana);
        sol2.setPrecioTotal(Fechas.calcularPrecioTotal(ayer, maniana, camioneta.getPrecioPorDia()));
        
        Solicitud sol3 = new Solicitud(limosina, cliente);
        sol3.setFechaInicio(ayer);
        sol3.setFechaFinal(hoy);
        sol3.setPrecioTotal(Fechas.calcularPrecioTotal(ayer, hoy, limosina.getPrecioPorDia()));
        
        arbol.insertar(sol1);
        arbol.insertar(sol2);
        arbol.insertar(sol3);
        
        assertEquals(sol3, arbol.accederSolicitud(ayer, hoy, limosina));
    }
    
    @Test
    public void insertar_SolicitudesMismasFechas(){
        
        ArbolBBS arbol = new ArbolBBS();
        Solicitud sol1 = new Solicitud(automovil, cliente);
        sol1.setFechaInicio(hoy);
        sol1.setFechaFinal(maniana);
        sol1.setPrecioTotal(Fechas.calcularPrecioTotal(hoy, maniana, automovil.getPrecioPorDia()));
        
        Solicitud sol2 = new Solicitud(camioneta, cliente);
        sol2.setFechaInicio(hoy);
        sol2.setFechaFinal(maniana);
        sol2.setPrecioTotal(Fechas.calcularPrecioTotal(hoy, maniana, camioneta.getPrecioPorDia()));
        
        arbol.insertar(sol1);
        arbol.insertar(sol2);
        
        assertEquals(sol1, arbol.accederSolicitud(hoy, maniana, automovil));
        assertEquals(sol2, arbol.accederSolicitud(hoy, maniana, camioneta));
    }
    
    @Test
    public void retirar_ArbolVacio(){
        
        ArbolBBS arbol = new ArbolBBS();
        
        assertNull(arbol.retirar(hoy, maniana, automovil));
    }
    
    @Test
    public void retirar_NoEsta(){
        
        ArbolBBS arbol = new ArbolBBS();
        Solicitud sol1 = new Solicitud(automovil, cliente);
        sol1.setFechaInicio(hoy);
        sol1.setFechaFinal(maniana);
        sol1.setPrecioTotal(Fechas.calcularPrecioTotal(hoy, maniana, automovil.getPrecioPorDia()));
        
        Solicitud sol2 = new Solicitud(camioneta, cliente);
        sol2.setFechaInicio(hoy);
        sol2.setFechaFinal(maniana);
        sol2.setPrecioTotal(Fechas.calcularPrecioTotal(hoy, maniana, camioneta.getPrecioPorDia()));
        
        arbol.insertar(sol1);
        arbol.insertar(sol2);
        
        assertNull(arbol.retirar(hoy, maniana, vagoneta));
    }
    
    @Test
    public void retirar_Exito_VerificarSiNoEstaEnArbol(){
        
        ArbolBBS arbol = new ArbolBBS();
        Solicitud sol1 = new Solicitud(automovil, cliente);
        sol1.setFechaInicio(hoy);
        sol1.setFechaFinal(maniana);
        sol1.setPrecioTotal(Fechas.calcularPrecioTotal(hoy, maniana, automovil.getPrecioPorDia()));
        
        arbol.insertar(sol1);
        
        assertNotNull(arbol.accederSolicitud(hoy, maniana, automovil));
        assertEquals(sol1, arbol.retirar(hoy, maniana, automovil));
        assertNull(arbol.accederSolicitud(hoy, maniana, automovil));
    }
    
    @Test
    public void retirar_ArbolConElmts_VaciarArbol(){
        
        ArbolBBS arbol = new ArbolBBS();
        Solicitud sol1 = new Solicitud(automovil, cliente);
        sol1.setFechaInicio(hoy);
        sol1.setFechaFinal(maniana);
        sol1.setPrecioTotal(Fechas.calcularPrecioTotal(hoy, maniana, automovil.getPrecioPorDia()));
        
        Solicitud sol2 = new Solicitud(camioneta, cliente);
        sol2.setFechaInicio(ayer);
        sol2.setFechaFinal(maniana);
        sol2.setPrecioTotal(Fechas.calcularPrecioTotal(ayer, maniana, camioneta.getPrecioPorDia()));
        
        Solicitud sol3 = new Solicitud(limosina, cliente);
        sol3.setFechaInicio(ayer);
        sol3.setFechaFinal(hoy);
        sol3.setPrecioTotal(Fechas.calcularPrecioTotal(ayer, hoy, limosina.getPrecioPorDia()));
        
        arbol.insertar(sol1);
        arbol.insertar(sol2);
        arbol.insertar(sol3);
        
        assertFalse(arbol.estaVacia());
        assertEquals(sol2, arbol.retirar(ayer, maniana, camioneta));
        assertEquals(sol1, arbol.retirar(hoy, maniana, automovil));
        assertEquals(sol3, arbol.retirar(ayer, hoy, limosina));
        assertTrue(arbol.estaVacia());
    }
}

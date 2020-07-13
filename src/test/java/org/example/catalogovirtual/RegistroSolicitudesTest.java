package org.example.catalogovirtual;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.RegistroSolicitudes;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.SolicitudNoValidaException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Solicitudes;
import org.example.catalogovirtual.modelo.nucleo.Automovil;
import org.example.catalogovirtual.modelo.nucleo.Cliente;
import org.example.catalogovirtual.modelo.nucleo.Solicitud;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author garcia
 */
public class RegistroSolicitudesTest {
    
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
    }
    
    @Test
    public void verificarSolicitud_SolicitudVacia(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = new Solicitud(new Automovil(), null);
        try {
            Solicitudes.verificarSolParaInsertar(sol);
            assert false;
        } catch (SolicitudNoValidaException ex) {
            assert true;
        }
    }
    
    @Test
    public void verificarSolicitud_FechasIncoherentes(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            sol = new Solicitud(new Automovil(), 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        sol.setFechaInicio(new GregorianCalendar(2015, 8, 20).getTime());
        sol.setFechaFinal(new GregorianCalendar(2015, 8, 19).getTime());
        try {
            Solicitudes.verificarSolParaInsertar(sol);
            assert false;
        } catch (SolicitudNoValidaException ex) {
            assert true;
        }
    }
    
    @Test
    public void verificarSolicitud_EstadoIncoherente(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            sol = new Solicitud(new Automovil(), 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        sol.setFechaInicio(new GregorianCalendar(2015, 8, 20).getTime());
        sol.setFechaFinal(new GregorianCalendar(2015, 8, 29).getTime());
        sol.setEstado(Solicitudes.ESTADO_EN_CURSO);
        try {
            Solicitudes.verificarSolParaInsertar(sol);
            assert false;
        } catch (SolicitudNoValidaException ex) {
            assert true;
        }
    }
    
    @Test
    public void verificarSolicitud_AutoNoDisponible(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            Automovil auto = new Automovil();
            auto.setDisponible(false);
            sol = new Solicitud(auto, 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        sol.setFechaInicio(new GregorianCalendar(2015, 8, 20).getTime());
        sol.setFechaFinal(new GregorianCalendar(2015, 8, 29).getTime());
        try {
            Solicitudes.verificarSolParaInsertar(sol);
            assert false;
        } catch (SolicitudNoValidaException ex) {
            assert true;
        }
    }
    
    @Test
    public void verificarSolicitud_PrecioIncoherente(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            sol = new Solicitud(new Automovil(), 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        sol.setFechaInicio(new GregorianCalendar(2015, 8, 20).getTime());
        sol.setFechaFinal(new GregorianCalendar(2015, 8, 29).getTime());
        sol.setPrecioTotal(-1);
        try {
            Solicitudes.verificarSolParaInsertar(sol);
            assert false;
        } catch (SolicitudNoValidaException ex) {
            assert true;
        }
    }
    
    @Test
    public void verificarSolicitud_GarantiaNoValida(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            Garantia.getGarantia(Autos.AUTOMOVIL).setGarantia(-1000);
            sol = new Solicitud(new Automovil(), 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
            Garantia.getGarantia(Autos.AUTOMOVIL).setGarantia(Autos.GARANTIA_AUTOMOVIL);
        }catch(DatosInvalidosException ex){
            assert false;
        }
        sol.setFechaInicio(new GregorianCalendar(2015, 8, 20).getTime());
        sol.setFechaFinal(new GregorianCalendar(2015, 8, 29).getTime());
        sol.setPrecioTotal(1000);
        try {
            Solicitudes.verificarSolParaInsertar(sol);
            assert false;
        } catch (SolicitudNoValidaException ex) {
            assert true;
        }
    }
    
    @Test
    public void verificarSolicitud_SolicitudAceptable(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            sol = new Solicitud(new Automovil(), 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        Calendar calendario = new GregorianCalendar();
        sol.setFechaInicio(calendario.getTime());
        calendario.set(Calendar.DAY_OF_MONTH, calendario.get(Calendar.DAY_OF_MONTH) + 9);
        sol.setFechaFinal(calendario.getTime());
        sol.setPrecioTotal(1000);
        try {
            Solicitudes.verificarSolParaInsertar(sol);
            assert true;
        } catch (SolicitudNoValidaException ex) {
            assert false;
        }
    }
    
    @Test
    public void insertarSolicitud_SolicitudNoValida(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = new Solicitud(new Automovil(), null);
        try {
            registro.insertarSolicitud(sol);
            assert false;
        } catch (SolicitudNoValidaException ex) {
            assert true;
        }
    }
    
    @Test
    public void insertarSolicitud_SolicitudValida(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            sol = new Solicitud(new Automovil(), 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        Calendar calendario = new GregorianCalendar();
        sol.setFechaInicio(calendario.getTime());
        calendario.set(Calendar.DAY_OF_MONTH, calendario.get(Calendar.DAY_OF_MONTH) + 9);
        sol.setFechaFinal(calendario.getTime());
        sol.setPrecioTotal(1000);
        try {
            registro.insertarSolicitud(sol);
            assert true;
        } catch (SolicitudNoValidaException ex) {
            assert false;
        }
    }
    
    @Test
    public void insertarSolicitud_VerificarEstadoAuto(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            sol = new Solicitud(new Automovil(), 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        Calendar calendario = new GregorianCalendar();
        sol.setFechaInicio(calendario.getTime());
        calendario.set(Calendar.DAY_OF_MONTH, calendario.get(Calendar.DAY_OF_MONTH) + 9);
        sol.setFechaFinal(calendario.getTime());
        sol.setPrecioTotal(1000);
        try {
            assertTrue(sol.getAuto().estaDisponible());
            registro.insertarSolicitud(sol);
            assertFalse(sol.getAuto().estaDisponible());
        } catch (SolicitudNoValidaException ex) {
            assert false;
        }
    }
    
    @Test
    public void insertarSolicitud_VerificarSiEstaEnReservas(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            Automovil auto = new Automovil();
            auto.setPlaca("123QWE");
            sol = new Solicitud(auto, 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        Calendar calendario = new GregorianCalendar();
        sol.setFechaInicio(calendario.getTime());
        calendario.set(Calendar.DAY_OF_MONTH, calendario.get(Calendar.DAY_OF_MONTH) + 9);
        sol.setFechaFinal(calendario.getTime());
        sol.setPrecioTotal(1000);
        try {
            registro.insertarSolicitud(sol);
            assertTrue(registro.estaSolicitud(
                    sol.getFechaInicial(), sol.getFechaFinal(), sol.getAuto(), 
                    Solicitudes.SOLICITUD_RESERVA));
        } catch (SolicitudNoValidaException ex) {
            assert false;
        }
    }
    
    @Test
    public void aprobarReserva_DatosNoValidos(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        
        try{
            registro.aprobarReserva(
                    null, new GregorianCalendar(2015, 8, 21).getTime(), 
                    new Automovil());
            assert false;
        }catch(IllegalArgumentException ex){
            assert true;
        }
    }
    
    @Test
    public void aprobarReserva_NoEncontrado(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        
        assertFalse(registro.aprobarReserva(
                new GregorianCalendar(2015, 8, 20).getTime(), 
                new GregorianCalendar(2015, 8, 21).getTime(), new Automovil()));
    }
    
    @Test
    public void aprobarReserva_SolPuestoEnCurso(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            Automovil auto = new Automovil();
            auto.setPlaca("123QWE");
            sol = new Solicitud(auto, 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        Calendar calendario = new GregorianCalendar();
        sol.setFechaInicio(calendario.getTime());
        calendario.set(Calendar.DAY_OF_MONTH, calendario.get(Calendar.DAY_OF_MONTH) + 9);
        sol.setFechaFinal(calendario.getTime());
        sol.setPrecioTotal(1000);
        try {
            registro.insertarSolicitud(sol);
            assertEquals(Solicitudes.ESTADO_RESERVA, sol.getEstado());
            assertTrue(registro.aprobarReserva(
                    sol.getFechaInicial(), sol.getFechaFinal(), sol.getAuto()));
            assertTrue(registro.estaSolicitud(
                    sol.getFechaInicial(), sol.getFechaFinal(), sol.getAuto(), 
                    Solicitudes.SOLICITUD_EN_CURSO));
            assertEquals(Solicitudes.ESTADO_EN_CURSO, sol.getEstado());
        } catch (SolicitudNoValidaException ex) {
            assert false;
        }
    }
    
    @Test
    public void eliminarReserva_DatosNoValidos(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        
        try{
            registro.eliminarReserva(
                    new GregorianCalendar(2015, 8, 20).getTime(), 
                    null, new Automovil());
            assert false;
        }catch(IllegalArgumentException ex){
            assert true;
        }
    }
    
    @Test
    public void eliminarReserva_NoEncontrado(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        
        assertFalse(registro.eliminarReserva(
                new GregorianCalendar(2015, 8, 20).getTime(), 
                new GregorianCalendar(2015, 8, 21).getTime(), new Automovil()));
    }
    
    @Test
    public void eliminarReserva_VerificarEstadoSol(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            Automovil auto = new Automovil();
            auto.setPlaca("123QWE");
            sol = new Solicitud(auto, 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        Calendar calendario = new GregorianCalendar();
        sol.setFechaInicio(calendario.getTime());
        calendario.set(Calendar.DAY_OF_MONTH, calendario.get(Calendar.DAY_OF_MONTH) + 9);
        sol.setFechaFinal(calendario.getTime());
        sol.setPrecioTotal(1000);
        try {
            registro.insertarSolicitud(sol);
            assertEquals(Solicitudes.ESTADO_RESERVA, sol.getEstado());
            assertTrue(registro.eliminarReserva(
                    sol.getFechaInicial(), sol.getFechaFinal(), sol.getAuto()));
            assertFalse(registro.estaSolicitud(
                    sol.getFechaInicial(), sol.getFechaFinal(), sol.getAuto(), 
                    Solicitudes.SOLICITUD_RESERVA));
            assertEquals(Solicitudes.ESTADO_ELIMINADO, sol.getEstado());
            assertTrue(registro.getHistorial().contains(sol));
        } catch (SolicitudNoValidaException ex) {
            assert false;
        }
    }
    
    @Test
    public void terminarSolicitud_DatosNoValidos(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        
        try{
            registro.terminarSolicitud(
                    new GregorianCalendar(2015, 8, 20).getTime(), 
                    new GregorianCalendar(2015, 8, 21).getTime(), new Automovil(),
                    -1);
            assert false;
        }catch(IllegalArgumentException ex){
            assert true;
        }
    }
    
    @Test
    public void terminarSolicitud_EnCurso_NoEncontrado(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        
        assertFalse(registro.terminarSolicitud(
                new GregorianCalendar(2015, 8, 20).getTime(), 
                new GregorianCalendar(2015, 8, 21).getTime(), new Automovil(),
                Solicitudes.SOLICITUD_EN_CURSO));
    }
    
    @Test
    public void terminarSolicitud_Vencidos_NoEncontrado(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        
        assertFalse(registro.terminarSolicitud(
                new GregorianCalendar(2015, 8, 20).getTime(), 
                new GregorianCalendar(2015, 8, 21).getTime(), new Automovil(),
                Solicitudes.SOLICITUD_VENCIDA));
    }
    
    @Test
    public void terminarSolicitud_VerificarEstadosYHistorial(){
        
        RegistroSolicitudes registro = new RegistroSolicitudes();
        Solicitud sol = null;
        try{
            Automovil auto = new Automovil();
            auto.setPlaca("123QWE");
            sol = new Solicitud(auto, 
                    Cliente.crearCliente(100000, "0000", "Prueba", 'A', 1111111));
        }catch(DatosInvalidosException ex){
            assert false;
        }
        Calendar calendario = new GregorianCalendar();
        sol.setFechaInicio(calendario.getTime());
        calendario.set(Calendar.DAY_OF_MONTH, calendario.get(Calendar.DAY_OF_MONTH) + 9);
        sol.setFechaFinal(calendario.getTime());
        sol.setPrecioTotal(1000);
        try {
            registro.insertarSolicitud(sol);
            assertEquals(Solicitudes.ESTADO_RESERVA, sol.getEstado());
            assertTrue(registro.aprobarReserva(
                    sol.getFechaInicial(), sol.getFechaFinal(), sol.getAuto()));
            assertTrue(registro.terminarSolicitud(
                    sol.getFechaInicial(), sol.getFechaFinal(), sol.getAuto(),
                    Solicitudes.SOLICITUD_EN_CURSO));
            assertEquals(Solicitudes.ESTADO_TERMINADO, sol.getEstado());
            assertTrue(sol.getAuto().estaDisponible());
            assertTrue(registro.getHistorial().contains(sol));
        } catch (SolicitudNoValidaException ex) {
            assert false;
        }
    }
    
}

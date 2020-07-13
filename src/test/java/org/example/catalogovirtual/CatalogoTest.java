package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.Catalogo;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.CategoriaNoExisteException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.NoExisteTalAutoException;
import org.example.catalogovirtual.modelo.cuerpo.excepciones.PlacaRepetidaException;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Automovil;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import org.junit.Before;

/**
 * The test class CatalogoTest.
 *
 * @author  empujesoft
 * @version  2015.06.03
 */
public class CatalogoTest
{
    
    @Before
    public void setUp(){
        
        Preferencias.setRaiz(new Preferencias.Raiz());
        Garantia.cargarValoresDefecto();
    }
    
    @Test
    public void getUltimaAccion_CatalogoNuevo_Vacio()
    {
        Catalogo catalogo = new Catalogo();
        
        assertTrue(catalogo.getUltimaLista().isEmpty());
    }
    
    @Test
    public void verLista_CatalogoNuevo_NoNulo()
    {
        Catalogo catalogo = new Catalogo();
        
        assertNotNull(catalogo.listarCatalogo());
    }
    
    @Test
    public void listaDeCategoria_CatagoriaAutomovil_NoNulo()
    {
        Catalogo catalogo = new Catalogo();
        
        ArrayList lista = catalogo.listarCategoria(Autos.AUTOMOVIL);
        
        assertNotNull(lista);
    }
    
    @Test
    public void listaDeCategoria_CatagoriaVagoneta_NoNulo()
    {
        Catalogo catalogo = new Catalogo();
        
        ArrayList lista = catalogo.listarCategoria(Autos.VAGONETA);
        
        assertNotNull(lista);
    }
    
    @Test
    public void listaDeCategoria_CatagoriaCamioneta_NoNulo()
    {
        Catalogo catalogo = new Catalogo();
        
        ArrayList lista = catalogo.listarCategoria(Autos.CAMIONETA);
        
        assertNotNull(lista);
    }
    
    @Test
    public void listaDeCategoria_CatagoriaLimosina_NoNulo()
    {
        Catalogo catalogo = new Catalogo();
        
        ArrayList lista = catalogo.listarCategoria(Autos.LIMOSINA);
        
        assertNotNull(lista);
    }
    
    @Test
    public void listaDeCategoria_CategoriaNegativaFueraDeRango_CategoriaNoExiteException()
    {
        Catalogo catalogo = new Catalogo();
        try{
            catalogo.listarCategoria(-1);
            assert false;
        }catch(CategoriaNoExisteException ex){
            assert true;
        }
    }
    
    @Test
    public void listaDeCategoria_CategoriaFueraDeRango_CategoriaNoExiteException()
    {
        Catalogo catalogo = new Catalogo();
        try{
            catalogo.listarCategoria(Autos.NUMERO_DE_CATEGORIAS);
            assert false;
        }catch(CategoriaNoExisteException ex){
            assert true;
        }
    }
    
    @Test
    public void seleccionarAuto_Encotrado()
    {
        Catalogo catalogo = new Catalogo();
        Automovil auto = new Automovil();
        auto.setPlaca("678YUI");
        auto.setPrecioPorDia(1000);
        auto.setModelo(2000);
        auto.setNombre("Auto");
        auto.setNumeroDePasajeros(4);
        try{
            catalogo.insertarNuevoAuto(auto);
            catalogo.seleccionarAuto("678YUI");
            assert true;
        }catch(NoExisteTalAutoException | PlacaRepetidaException 
                | DatosInvalidosException ex){
            assert false;
        }
    }
    
    @Test
    public void seleccionarAuto_NoEncotrado()
    {
        Catalogo catalogo = new Catalogo();
        try{
            catalogo.seleccionarAuto("YOO666");
            assert false;
        }catch(NoExisteTalAutoException ex){
            assert true;
        }
    }
    
    @Test
    public void insertarNuevoAuto_Exito()
    {
        Catalogo catalogo = new Catalogo();
        Automovil auto = new Automovil();
        auto.setPlaca("YOO666");
        auto.setModelo(2000);
        auto.setNombre("Auto");
        auto.setNumeroDePasajeros(4);
        auto.setPrecioPorDia(200);
        try{
            catalogo.insertarNuevoAuto(auto);
            Auto encontrado = catalogo.seleccionarAuto("YOO666");
            assertEquals(auto, encontrado);
        }catch(PlacaRepetidaException | NoExisteTalAutoException | DatosInvalidosException ex){
            assert false;
        }
    }
    
    @Test
    public void insertarNuevoAuto_NullPointerException()
    {
        Catalogo catalogo = new Catalogo();
        try{
            catalogo.insertarNuevoAuto(null);
            assert false;
        }catch(PlacaRepetidaException | DatosInvalidosException ex){
            assert false;
        }catch(NullPointerException ex){
            assert true;
        }
    }
    
    @Test
    public void insertarNuevoAuto_PlacaExistenteException()
    {
        Catalogo catalogo = new Catalogo();
        Automovil auto = new Automovil();
        auto.setPlaca("678YUI");
        auto.setPrecioPorDia(1000);
        auto.setModelo(2000);
        auto.setNombre("Auto1");
        auto.setNumeroDePasajeros(4);
        
        Automovil automovil = new Automovil();
        automovil.setPlaca("678YUI");
        automovil.setModelo(2000);
        automovil.setNombre("Auto2");
        automovil.setNumeroDePasajeros(4);
        automovil.setPrecioPorDia(200);
        try{
            catalogo.insertarNuevoAuto(auto);
            catalogo.insertarNuevoAuto(automovil);
            assert false;
        }catch(PlacaRepetidaException ex){
            assert true;
        }catch(NullPointerException | DatosInvalidosException ex){
            assert false;
        }
    }
}

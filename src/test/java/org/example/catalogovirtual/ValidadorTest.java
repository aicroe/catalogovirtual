package org.example.catalogovirtual;

import org.example.catalogovirtual.modelo.cuerpo.utiles.Validador;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ValidadorTest.
 *
 * @author  empujesoft
 */
public class ValidadorTest
{
    /**
     * Default constructor for test class ValidadorTest
     */
    public ValidadorTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void validadorTest1(){
        String password = new String("miMamaMeMima");
        boolean exito = false;
        if(Validador.validar(password)) exito = true;
        assertEquals(exito, true);
    }
    
    @Test
    public void validadorTest2(){
        String password = new String("$$$$$$$$");
        boolean exito = false;
        if(Validador.validar(password)) exito = true;
        assertEquals(exito, false);
    }
    
    @Test
    public void validadorTest3(){
        String password = new String("#MarParaBolivia");
        boolean exito = false;
        if(Validador.validar(password)) exito = true;
        assertEquals(exito, false);
    }
}
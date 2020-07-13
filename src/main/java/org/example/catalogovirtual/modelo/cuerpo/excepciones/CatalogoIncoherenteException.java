package org.example.catalogovirtual.modelo.cuerpo.excepciones;


/**
 * Excepcion lanzada si despues de la carga del catalogo se encuentra algun fallo en los archivos
 * 
 * @author empujesoft
 * @version 2015.06.11
 */
public class CatalogoIncoherenteException extends RuntimeException
{
    public CatalogoIncoherenteException(int categoria, String placa)
    {
        super("�Incoherencia en los archivos de los autos!" + "\nCatagoria de autos Nro: " + categoria + 
            ". Placa repetida: " + placa);
    }
}

package org.example.catalogovirtual.modelo.cuerpo.utiles;

import org.example.catalogovirtual.modelo.cuerpo.excepciones.DatosInvalidosException;
import org.example.catalogovirtual.modelo.nucleo.Auto;
import org.example.catalogovirtual.modelo.nucleo.Automovil;
import org.example.catalogovirtual.modelo.nucleo.Camioneta;
import org.example.catalogovirtual.modelo.nucleo.Limosina;
import org.example.catalogovirtual.modelo.nucleo.Vagoneta;


/**
 * Define las constantes que representan a cada categoria de autos.
 * 
 * @author empujesoft
 * @version 2015.06.27
 */

public final class Autos
{
    public static final int AUTOMOVIL = 0;
    public static final int CAMIONETA = 1;
    public static final int VAGONETA = 2;
    public static final int LIMOSINA = 3;
    
    public static final int TODAS_LAS_CATEGORIAS = -1;
    public static final int NUMERO_DE_CATEGORIAS = 4;
    
    public static final String[] NOMBRES = new String[]
            {"Automovil", "Camioneta", "Vagoneta", "Limosina", "Todos"};
    
    public static final String NOMBRE_AUTOMOVIL = NOMBRES[AUTOMOVIL];
    public static final String NOMBRE_CAMIONETA = NOMBRES[CAMIONETA];
    public static final String NOMBRE_VAGONETA = NOMBRES[VAGONETA];
    public static final String NOMBRE_LIMOSINA = NOMBRES[LIMOSINA];
    
    public static final double GARANTIA_AUTOMOVIL = 1000.0;
    public static final double GARANTIA_CAMIONETA = 2000.0;
    public static final double GARANTIA_LIMOSINA = 5000.0;
    public static final double GARANTIA_VAGONETA = 3000.0;

    
    private Autos()
    {
    }

    public static int categoriaPorNombre(String nombreCategoria)
    {
        String categoria = nombreCategoria.toLowerCase();
        if (categoria.startsWith("auto")) {
            return Autos.AUTOMOVIL;
        } else if (categoria.startsWith("camio")) {
            return Autos.CAMIONETA;
        } else if (categoria.startsWith("vago")) {
            return Autos.VAGONETA;
        } else if (categoria.startsWith("limo")) {
            return Autos.LIMOSINA;
        } else if (categoria.startsWith("tod")) {
            return Autos.TODAS_LAS_CATEGORIAS;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static Auto autoPorCategoria(String categoria)
    {
        if (categoria.equals("Automovil")) {
            return new Automovil();
        } else if (categoria.equals("Camioneta")) {
            return new Camioneta();
        } else if (categoria.equals("Vagoneta")) {
            return new Vagoneta();
        } else if (categoria.equals("Limosina")) {
            return new Limosina();
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    public static void verificarAutoParaInsertar(Auto auto) throws DatosInvalidosException {
        
        if (auto == null || auto.getPlaca() == null || 
                auto.getPlaca().isEmpty() || auto.getNombre() == null || 
                auto.getNombre().isEmpty()) {
            throw new DatosInvalidosException("Datos vacios");
        } else if (auto.getModelo() <= 1900 || auto.getModelo() >= 2100) {
            throw new DatosInvalidosException("Modelo Incoherente");
        } else if (auto.getNumeroDePasajeros() <= 0) {
            throw new DatosInvalidosException("No. Pasajeros no puede ser: " + 
                    auto.getNumeroDePasajeros());
        } else if (auto.getPrecioPorDia() <= 0) {
            throw new DatosInvalidosException("Precio no puede ser: " + 
                    auto.getPrecioPorDia());
        } else if (!auto.estaDisponible()) {
            throw new DatosInvalidosException("Estado Invalido");
        } else if (!Validador.verificarPlaca(auto.getPlaca())){
            throw new DatosInvalidosException("Formato Placa Invalido");
        }
    }
    
}

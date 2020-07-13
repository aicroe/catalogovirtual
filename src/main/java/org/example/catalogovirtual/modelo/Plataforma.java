/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.modelo;

import org.example.catalogovirtual.modelo.cuerpo.Catalogo;
import org.example.catalogovirtual.modelo.cuerpo.RegistroSolicitudes;
import org.example.catalogovirtual.modelo.cuerpo.RegistroUsuarios;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que esta encargada de la administracion del los datos o el modelo la 
 * aplicacion.
 *
 * @author empujesoft
 * @version 2015.08.18
 */
public class Plataforma {
    
    private Catalogo catalogo;
    private RegistroUsuarios regUsuarios;
    private RegistroSolicitudes regSolicitudes;
    
    private static final int INDICE_CATALOGO = 0;
    private static final int INDICE_REG_USU = 1;
    private static final int INDICE_REG_SOL = 2;
    private static final int INDICE_RAIZ_PREFER = 3;
    private static final int INDICE_GARANTIAS = 4;
    public static final String NOM_ARCHIVO_DATOS = "catalogovirtual";
    public static final String FOLDER_CONTENEDOR = "ses/";
    public static final String EXTENSION_DATO = "dat";
    
    /**
     * Catalogo de Autos.
     * @return El catalogo
     */
    public Catalogo getCatalogo(){
        
        return catalogo;
    }
    
    /**
     * Registro de Usuarios.
     * @return El registro de usuarios
     */
    public RegistroUsuarios getRegUsuarios(){
        
        return regUsuarios;
    }
    
    /**
     * Registro de Solicitudes
     * @return El registro de solicitudes
     */
    public RegistroSolicitudes getRegSolicitudes(){
        
        return regSolicitudes;
    }
    
    /**
     * Carga los datos que fueron guardados la ultima sesion, si no se 
     * guardaron datos en la anterior sesion o es la primera sesion de la
     * aplicacion se crean objetos nuevo y vacios, pueden cargarse los datos 
     * mientras los datos actuales sean nulos, osea que este metodo debe ser 
     * de los primeros metodos invocados en la aplicacion, los datos que carga 
     * son el catalogo de autos, el registro de usuarios, y el registro de 
     * solicitudes, Si este metodo no es llamado antes los metodos accesores 
     * respectivos devolveran null, Ademas de cargar los datos, los actualiza
     * y por motivos de seguridad se verifica que los autos esten bien cargados
     * cada vez que se llame este metodo.
     */
    public void cargarDatos(){
        
        if(getCatalogo() == null && getRegUsuarios() == null && 
                getRegSolicitudes() == null && Preferencias.getRaiz() == null){
            ArrayList datos = (ArrayList) leerObjeto(NOM_ARCHIVO_DATOS, EXTENSION_DATO);
            if(datos != null){
                catalogo = (Catalogo) datos.get(INDICE_CATALOGO);
                regUsuarios = (RegistroUsuarios) datos.get(INDICE_REG_USU);
                regSolicitudes = (RegistroSolicitudes) datos.get(INDICE_REG_SOL);
                Preferencias.setRaiz((Preferencias.Raiz) datos.get(INDICE_RAIZ_PREFER));
                Garantia.cargarValores((Garantia[]) datos.get(INDICE_GARANTIAS));
            }else{
                catalogo = new Catalogo();
                regUsuarios = new RegistroUsuarios();
                regSolicitudes = new RegistroSolicitudes();
                Preferencias.setRaiz(new Preferencias.Raiz());
                Garantia.cargarValoresDefecto();
            }
            catalogo.verificarCargaDeAutos();
            regSolicitudes.actualizarRegistro();
        }else{
            throw new UnsupportedOperationException();
        }
    }
    
    /**
     * Guarda los datos de esta sesion y vuleve las referencias nulas, asi que
     * este debe ser el metodo que se invoca cuando se esta cerrando la 
     * aplicacion, solo se pueden guardar datos mientras se hayan 
     * cargado otros antes, Los Datos se guardan todos en un ArrayList
     * porque si creamos serializamos cada objeto por separado no encontramos
     * con muchos problemas, por ejemplo que se crean varias referencias de un
     * mismo objeto.
     */
    public void guardarDatos(){
        
        if(getCatalogo() != null && getRegUsuarios() != null && 
                getRegSolicitudes() != null && Preferencias.getRaiz() != null){
            ArrayList<Object> datos = new ArrayList<>();
            datos.add(INDICE_CATALOGO, catalogo);
            datos.add(INDICE_REG_USU, regUsuarios);
            datos.add(INDICE_REG_SOL, regSolicitudes);
            datos.add(INDICE_RAIZ_PREFER, Preferencias.getRaiz());
            datos.add(INDICE_GARANTIAS, Garantia.getValores());
            escribirObjeto(datos, NOM_ARCHIVO_DATOS, EXTENSION_DATO);
            
            catalogo = null;
            regUsuarios = null;
            regSolicitudes = null;
            Preferencias.setRaiz(null);
            Garantia.cargarValores(null);
        }else{
            throw new UnsupportedOperationException();
        }
    }
    
    /**
     * Lee un objeto serializado que se encuentra en el folder contenedor de
     * la aplicacion (bin/), leemos el objeto dados el nombre del archivo y 
     * la extencion del mismo, si el archivo no existe se devuelve nulo.
     * @param nombre El nombre del archivo donde se encuentra el objeto que 
     *                  queremos leer
     * @param extencion La extencion del archivo
     * @return el objeto leido o nulo si el archivo no existe
     */
    public static Object leerObjeto(String nombre, String extencion){
        
        if(nombre == null || nombre.isEmpty() || 
                extencion == null || extencion.isEmpty())
            throw new IllegalArgumentException();
        File archivo = new File(FOLDER_CONTENEDOR + nombre + "." + extencion);
        ObjectInputStream lector = null;
        if(archivo.exists()){
            try{
                lector = new ObjectInputStream(new FileInputStream(archivo));
                return lector.readObject();
            }catch(IOException | ClassNotFoundException ex){
                System.out.println(ex);
            }finally{
                try{
                    lector.close();
                }catch(IOException ex){
                    System.out.println(ex);
                }
            }
        }
        return null;
    }
    
    /**
     * Escribe un objeto serializable, en un archivo con el nombre y extencion
     * especificados, en la carpeta contenedora de la apliacion (bin/), ninguno
     * de los parametros no puede ser nulo o vacio, si ya habia una version
     * anterior del objeto en la carpeta contenedora, este se elimina y se 
     * escribe el nuevo objeto actualizado.
     * @param objeto El objeto serializable que se va a escribir
     * @param nombre El nombre con del archivo que se va ha escribir
     * @param extencion La extencion del archivo para el objeto serializable
     */
    public static void escribirObjeto(
            Serializable objeto, String nombre, String extencion){
        
        if(objeto == null || nombre == null || nombre.isEmpty() ||
                extencion == null || extencion.isEmpty())
            throw new IllegalArgumentException();
        File archivo = new File(FOLDER_CONTENEDOR + nombre + "." + extencion);
        ObjectOutputStream escritor = null;
        if(!archivo.exists()) archivo.mkdirs();
        try{
            archivo.delete();
            escritor = new ObjectOutputStream(new FileOutputStream(archivo));
            escritor.writeObject(objeto);
        }catch(IOException ex){
            System.out.println(ex);
        }finally{
            try{
                escritor.close();
            }catch(IOException ex){
                System.out.println(ex);
            }
        }
    }
}

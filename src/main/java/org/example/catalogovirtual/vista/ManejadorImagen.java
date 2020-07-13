package org.example.catalogovirtual.vista;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * El manejador de las imagenes del catalogo.
 * 
 * @author empujesoft
 * @version 2015.06.22
 */
public final class ManejadorImagen
{
    public static final int MAX_TAMANIO_IMG_AUTO = 200;
    public static final int MAX_TAMANIO_IMG_USUARIO = 100;
    
    public static final String FORMATO_IMAGEN = "jpg";
    public static final String FOLDER_AUTOS = "res/autos/";
    public static final String FOLDER_USUARIOS = "res/usuarios/";
    public static final String IMAGEN_USUARIO_DEFECTO = "/usuariodefecto.jpg";
    public static final String IMAGEN_AUTO_DEFECTO = "/00000A.jpg";
    
    private ManejadorImagen()
    {
    }
    
    /**
     * Lee una imagen del folder especificado, y nombre. Pora la aplicacion
     * la imagenes que se usaran estan en la carpeta res. La imagen
     * a ser abierta debe cumplir con los requisitos de ancho y alto, de imagen,
     * Si la imagen no existe o no cumple con el alto y ancho. Se devuelve la 
     * imagen especificada en el parametro imagenDefecto que debe ser uno de 
     * los recursos defecto de la aplicacion.
     * 
     * @param folder folder donde esta la imagen
     * @param nombre nombre de la imagen
     * @param ancho ancho maximo de la imagen
     * @param alto  alto maximo de la imagen
     * @param imagenDefecto nombre de la imagen que se usara si pasa algo
     * @return la imagen especificada por el nombre, si pasa algo la imagenDefecto
     * @throws IllegalArgumentException
     */
    public static BufferedImage leerImagen(String folder, String nombre, int ancho, int alto, 
            String imagenDefecto){
        if((!folder.equals(FOLDER_AUTOS) && !folder.equals(FOLDER_USUARIOS)) ||
                nombre == null || nombre.isEmpty() || ancho <= 0 || alto <= 0 ||
                (!imagenDefecto.equals(IMAGEN_AUTO_DEFECTO) && !imagenDefecto.equals(IMAGEN_USUARIO_DEFECTO)))
            throw new IllegalArgumentException();
        try{
            File archivo = new File(folder + nombre + "." + FORMATO_IMAGEN);
            return leerImagenDentroLimites(archivo, ancho, alto);
        }catch(ImagenNoValidaException ex){
            return leerImagenRecurso(imagenDefecto);
        }
    }
    
    /**
     * Escribe una imagen en el disco de la computadora, en el folder especificado 
     * y con el nombre deseado, pero con el formato imagen estandar de la aplicacion
     * JPG. Si los parametros son nulos o vacios se lanza IllegalArgumentException.
     * Pora la aplicacion la imagenes se deben guardar en la carpeta res.
     * 
     * @param imagen imagen a ser escrita
     * @param folder folder en el que se escribira la imagen
     * @param nombre nombre con el que se escribira la imagen
     * @throws IllegalArgumentException
     */
    public static void escribirImagen(BufferedImage imagen, String folder, String nombre)
    {
        if(imagen == null || (!folder.equals(FOLDER_AUTOS) && !folder.equals(FOLDER_USUARIOS)) ||
                nombre == null || nombre.isEmpty()) throw new IllegalArgumentException();
        try{
            File archivo = new File(folder + nombre + "." + FORMATO_IMAGEN);
            if(!archivo.exists())archivo.mkdirs();
            archivo.delete();
            ImageIO.write(imagen, FORMATO_IMAGEN, archivo);
        }catch(IOException ex){
            System.out.println(ex);
        }
    }
    
    /**
     * Elimina una imagen de la carpeta res. Si la imagen no existe no pasa nada,
     * si los parametros no son aceptables se lanza IllegalArgumnetException
     * 
     * @param folder folder en el que esta la imagen
     * @param nombre nombre se la imagen
     * @throws IllegalArgumentException
     */
    public static void eliminarArchivoImagen(String folder, String nombre)
    {
        if((!folder.equals(FOLDER_AUTOS) && !folder.equals(FOLDER_USUARIOS)) || 
                nombre == null || nombre.isEmpty()) throw new IllegalArgumentException();
        File archivo = new File(folder + nombre + "." + FORMATO_IMAGEN);
        if(archivo.exists()){
            archivo.delete();
        }
    }
    
    /**
     * Busca una imagen en el disco de la computadora. Este metodo debe ser usado
     * por una de las vistas de la aplicacion que requiera que el usuario busque
     * una imagen. Al final el metodo devolvera la imagen en un buff o nulo, si la 
     * imagen no es valida. Los eventos que se daran en la busqueda de la imagen
     * son escuchados por el objeto listenerBuscador.
     * 
     * @param listenerBuscador el que escucha los eventos para buscar una imagen
     * @return devuelve la imagen buscada en un buff o nulo.
     */
    public static BufferedImage buscarImagen(BuscarImagenListener listenerBuscador)
    {
        if(listenerBuscador == null) throw new IllegalArgumentException();
        JFileChooser buscador = new JFileChooser();
        FileNameExtensionFilter filtros = new FileNameExtensionFilter(
                "Formato Imagen '.jpg', '.jpeg' ,'.png'", "jpg", "jpeg", "png");
        buscador.addChoosableFileFilter(filtros);
        buscador.setFileFilter(filtros);
        int accion = buscador.showOpenDialog(null);
        if (accion == JFileChooser.APPROVE_OPTION) {
            File seleccionado = buscador.getSelectedFile();
            if (seleccionado != null) {
                try {
                    BufferedImage imagen = ManejadorImagen.leerImagenDentroLimites(seleccionado, 
                            listenerBuscador.getAnchoMaximo(), listenerBuscador.getAltoMaximo());
                    listenerBuscador.mostrarNombreImagen(seleccionado.getName());
                    return imagen;
                } catch (ImagenNoValidaException ex) {
                    listenerBuscador.mostrarNombreImagen("");
                    listenerBuscador.mostrarMensaje(ex.getMessage(), Color.RED);
                }
            }
        }
        return null;
    }
    
    /**
     * Lee una imagen del archivo. La imagen a ser abierta debe cumplir con 
     * los requisitos de ancho y alto, de imagen. Si la imagen no existe o no 
     * cumple con el alto y ancho o no es una imagen. 
     * Se lanza la excepcion ImagenNoValida.
     * 
     * @param archivo archivo que representa la imagen que se quiere abrir
     * @param ancho ancho maximo de la imagen
     * @param alto  alto maximo de la imagen
     * @return la imagen especificada por el nombre, si pasa algo
     *  se lanza la excepcion
     * @throws ImagenNoValidaException
     * @throws IllegalArgumentException
     */
    public static BufferedImage leerImagenDentroLimites(File archivo, int ancho, int alto)
                                            throws ImagenNoValidaException {
        if(archivo == null || ancho <= 0 || alto <= 0) throw new IllegalArgumentException();
        if(archivo.exists()){
            try{
                BufferedImage imagen = ImageIO.read(archivo);
                if(imagen == null){
                    throw new ImagenNoValidaException("No es una Imagen.");
                }else if(imagen.getWidth() <= ancho && imagen.getHeight() <= alto){
                    return imagen;
                }else{
                    throw new ImagenNoValidaException("Tamaño maximo: " +  ancho + " x " + alto);
                }
            }catch(IOException ex){
                System.out.println(ex);
            }
        }
        throw new ImagenNoValidaException("El archivo no existe.");
    }
    
    /**
     * Lee una de las imagenes recurso de la aplicacion.
     * 
     * @param nombreRecurso nombre de la imagen recurso
     * @return devuelve la imagen en un buff
     */
    private static BufferedImage leerImagenRecurso(String nombreRecurso)
    {
        Image imagen = new ImageIcon(urlDeRecurso(nombreRecurso)).getImage();
        BufferedImage buffimagen = new BufferedImage(
                imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D graficos = buffimagen.createGraphics();
        graficos.drawImage(imagen, 0, 0, null);
        return buffimagen;
    }
    
    /**
     * Devuelve la direccion url del recurso por el nombre especificado.
     * 
     * @param nombreRecurso nombre del recurso
     * @return una url del recurso
     */
    private static URL urlDeRecurso(String nombreRecurso)
    {
        return new ManejadorImagen().getClass().getResource(nombreRecurso);
    }
    
    /**
     * El que oye los eventos cuando se busca una imagen con el metodo
     * buscarImagen(...). Tambien tiene informacion necesaria para el buscador.
     */
    public static interface BuscarImagenListener
    {
        abstract int getAnchoMaximo();
        abstract int getAltoMaximo();
        abstract void mostrarNombreImagen(String nombre);
        abstract void mostrarMensaje(String mensaje, Color color);
    }
    
    public static class ImagenNoValidaException extends Exception
    {
        public ImagenNoValidaException(String mensaje)
        {
            super(mensaje);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.controlador;

import org.example.catalogovirtual.modelo.Preferencias;
import org.example.catalogovirtual.modelo.cuerpo.RegistroUsuarios;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Autos;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Garantia;
import org.example.catalogovirtual.modelo.cuerpo.utiles.Validador;
import org.example.catalogovirtual.modelo.nucleo.Administrador;
import org.example.catalogovirtual.vista.JTextNumber;
import org.example.catalogovirtual.vista.UsuarioGUI;
import org.example.catalogovirtual.vista.admin.JDialogPreferencias;
import org.example.catalogovirtual.vista.admin.JDialogPreferencias.Cambios;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;
import javax.swing.JComboBox;

/**
 *
 * @author garcia
 */
public class JDialogPreferControlador {
    
    private JDialogPreferencias dialogoPrefer;
    private RegistroUsuarios registro;
    private Administrador administrador;
    private double[] bufGarantias;
    
    public JDialogPreferControlador(
            JDialogPreferencias dialogoPrefer,
            RegistroUsuarios registro, Administrador administrador){
        
        this.dialogoPrefer = dialogoPrefer;
        this.registro = registro;
        this.administrador = administrador;
        bufGarantias = new double[Autos.NUMERO_DE_CATEGORIAS];
        Arrays.fill(bufGarantias, -1);
    }
    
    public FocusListener crearListenerCampoGarantia(JComboBox garantias, JTextNumber campo){
        
        return new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event){
                
                int indice = garantias.getSelectedIndex();
                bufGarantias[indice] =  campo.getDouble();
            }
        };
    }
    
    public ActionListener crearListenerGarantia(JComboBox garantias, JTextNumber campo){
        
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                
                int indice = garantias.getSelectedIndex();
                Garantia garantia = (Garantia) garantias.getItemAt(indice);
                double valor = (bufGarantias[indice] == -1)? 
                        garantia.getGarantia() : bufGarantias[indice];
                campo.setText(String.valueOf(valor));
            }
            
        };
    }
    
    public class ListenerBotonResetear implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            Preferencias.resetearPreferencias();
            Garantia.cargarValoresDefecto();
            bufGarantias = new double[Autos.NUMERO_DE_CATEGORIAS];
            Arrays.fill(bufGarantias, -1);
            dialogoPrefer.actualizarComponentes(
                    Preferencias.getRaiz(), 
                    administrador, Garantia.getValores());
        }
    }
    
    public class ListenerGuradarCambios implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            
            Cambios cambios = dialogoPrefer.getCambios();
            String loginAdmin = cambios.loginAdmin;
            boolean cambiarPassword = cambios.cambiarPassword;
            boolean estaUsuario = registro.estaUsuarioPorLogin(loginAdmin);
            if(!estaUsuario && !cambiarPassword){
                administrador.setLogin(loginAdmin);
            }else if(estaUsuario){
                if(!loginAdmin.equals(administrador.getLogin())){
                    dialogoPrefer.cambiarTextoBarraMensaje(
                            "Usuario ya registrado", Color.RED);
                    return;
                }
            } 
            if(cambiarPassword){
                if(Validador.validar(loginAdmin) && administrador.setPassword(
                        Validador.decodificarPassword(cambios.antPassword),
                        Validador.decodificarPassword(cambios.nuevoPassword))){
                    administrador.setLogin(loginAdmin);
                }else{
                    dialogoPrefer.cambiarTextoBarraMensaje(
                            "Login o Password incorrectos", Color.RED);
                    return;
                }
            }
            
            Preferencias.setCantMaxSolCliente(cambios.cantMaxSolCli);
            Preferencias.setMaxNumDiasAlquiler(cambios.maxNumDiasAlq);
            Preferencias.setMaxNumDiasSolEnReserva(cambios.maxNumDiasSolRese);
            Garantia[] garantias = Garantia.getValores();
            for(int i = 0; i < garantias.length; i++){
                if(bufGarantias[i] != -1){
                    garantias[i].setGarantia(bufGarantias[i]);
                }
            }
            dialogoPrefer.cambiarTextoBarraMensaje(
                    "Guardado con Exito", UsuarioGUI.VERDE_OSCURO);
        }
    }
    
}

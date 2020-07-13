/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.controlador;

import org.example.catalogovirtual.modelo.nucleo.*;
import org.example.catalogovirtual.vista.admin.JDialogAuto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

/**
 *
 * @author USER-UNO
 */
public class JDialogAutoControlador {
    private Auto auto;
    private JDialogAuto dialogo;
    
    public JDialogAutoControlador(JDialogAuto dialogo){
        this.dialogo = dialogo;
    }
    
    public JDialogAuto getDialogo(){
        return dialogo;
    }
            
    public class ListenerBotonModificar implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dialogo.setModificable(true);
        }
    }
    
    public class ListenerBotonConfirmar implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(dialogo.sonCamposValidos()){
                JDialogAuto.PerfilAuto perfil = dialogo.getPerfil();
                String categoria = dialogo.getCategoria();
                switch(categoria){
                    case "Automovil": auto = new Automovil();
                        break;
                    case "Vagoneta": auto = new Vagoneta();
                        break;
                    case "Limosina": auto = new Limosina();
                        break;
                    case "Camioneta": auto = new Camioneta();
                        break;
                }
                auto.setModelo(perfil.modelo);
                auto.setNombre(perfil.nombre);
                auto.setNumeroDePasajeros(perfil.nroPasajeros);
                auto.setPlaca(perfil.placa);
                auto.setPrecioPorDia(perfil.precioPorDia);
                auto.setTipoDeCaja(perfil.tipoDeCaja);
                dialogo.setAutoModificado(auto);
                dialogo.setModificable(false);
                dialogo.actualizarBarraMensaje("Cambios confirmados con exito. Seleccione"
                        + " aceptar para guardar la informacion",
                        Color.BLUE);
            }
            else dialogo.actualizarBarraMensaje("Datos invalidos, intente nuevamente", 
                    Color.RED);
        }
    }
}
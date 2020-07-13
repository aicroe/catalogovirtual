/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.catalogovirtual.modelo.cuerpo.utiles;

import org.example.catalogovirtual.modelo.nucleo.Solicitud;

/**
 *
 * @author garcia
 */
public class BuscadorCi extends Buscador{

    public BuscadorCi(String paramBusqueda){
        
        super(paramBusqueda);
        setMotor(new MotorCi());
    }
    
    private class MotorCi extends MotorDeBusqueda{
        
        @Override
        public boolean emparejan(Solicitud solicitud, String param){
            
            if(param.isEmpty()) return false;
            return String.valueOf(solicitud.getCliente().getCi()).contains(param);
        }
    }
}

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
public class BuscadorPlaca extends Buscador{

    public BuscadorPlaca(String paramBusqueda){
        
        super(paramBusqueda);
        setMotor(new MotorAuto());
    }
    
    private class MotorAuto extends MotorDeBusqueda{
        
        @Override
        public boolean emparejan(Solicitud solicitud, String param){
            
            if(param.isEmpty()) return false;
            return solicitud.getAuto().getPlaca().contains(param.toUpperCase());
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;

/**
 *
 * @author dam1a_10
 */
public class Tareas {
    
    private Object[][] tareas;

    
 
//  CONSTRUCTORES  
    public Tareas(){}

    public Tareas(Object[][] tareas) {
        this.tareas = tareas;
    }
    
//  GETTERS   
    public Object[][] getTareas() {
        return tareas;
    }
    
    
//  SETTERS
    public void setTareas(Object[][] tareas) {
        this.tareas = tareas;
   }
// METODOS
    
    
    /**
     *
     * @return Entero con el numero de tareas que hay
     */
  
    
     public int numeroTareas() {
             return tareas.length;
    }
   
    
     public int numeroTareas(Object[][] tareas) {
             return tareas.length;
    }
    
}

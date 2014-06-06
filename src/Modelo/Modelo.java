/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Conexion;
import javax.swing.JOptionPane;

/**
 *
 * @author Broker
 */
public class Modelo extends Conexion {

    public Usuario usuario = new Usuario();

    public Modelo() {
    }

//____________________________________________________________________________ 
    public void insertarUusario(String usuario, String pass) {
        this.instert("  Usuarios ", "'" + usuario + "','" + pass + "'");
        JOptionPane.showMessageDialog(null, "Usuario creado correctamente", "Usuario incorrecto.", 1);
    }

    /**
     * Metodo para identificarse en el sistmea
     *
     * @param usuario
     * @param pass
     * @return Deuvelve true si existe en el sistema, false sino.
     */
    public boolean loguear(String usuario, String pass) {

        Object[][] user = select("usuarios", " usuario, pass ", " usuario='" + usuario + "' and pass='" + pass + "'", null);
        if (user.length > 0) {

            this.usuario.setUsuario((String) user[0][0]);
            this.usuario.setPass(user[0][1].toString());
            return true;
        } else {
            return false;
        }

    }

    public void insertarTarea(String usuario, String titulo, String descripcion, String prioridad, int progreso) {

        instert("TAREAS ", +maxID() + 1 + ",'" + usuario + "','" + titulo + "','" + descripcion + "','" + prioridad + "'," + progreso + ",sysdate");

    }

    /**
     *
     * @return Entero con el numero de tareas que hay
     */
    public int numeroTareas() {
        Object[][] count = select("TAREAS", "ID", null, null);
        return count.length;
    }
/**
 * 
 * @return Entero. El número mas grande de id, para poder hacer un insert
 * sin repetir la clave primaria (id)
 */
    public int maxID() {
        if (numeroTareas() == 0) {  
            return 1;
        } else {
            Object[][] max = select("TAREAS", "max(id)", null, null);
            return Integer.parseInt((String) max[0][0]);
        }
    }

    public Object[][] recuperarTareas(String orderBy) {
        Object[][] tareas = select("TAREAS", "ID,USUARIO,TITULO,DESCRIPCION,PRIORIDAD,PROGRESO,FECHA ", null, orderBy);
        return tareas;

    }

    public Object[][] recuperarTareas() {
        Object[][] tareas = select("TAREAS", "ID,USUARIO,TITULO,DESCRIPCION,PRIORIDAD,PROGRESO,FECHA ", null, null);
        return tareas;

    }

}

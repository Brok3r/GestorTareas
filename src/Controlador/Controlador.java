/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Modelo;
import Vista.VentanaInicio;
import Vista.IniciarSesion;
import Vista.NuevaTarea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dam1a_10
 */
public class Controlador implements ActionListener {

    private VentanaInicio view;
    private Modelo model;
//form hijos
    IniciarSesion isesion;
    NuevaTarea  nuevaTarea;

    public Controlador(VentanaInicio vista, Modelo modelo) {
        this.view = vista;
        this.model = modelo;
        iniciar();
    }
    /* INICIA */

    private void iniciar() {
        if(model.getConnectoin()!=null) view.conectado.setEnabled(true); else view.conectado.setEnabled(false); 
        view.setTitle("Gestor de Tareas");
        view.setExtendedState(VentanaInicio.MAXIMIZED_BOTH);
        //Se añade las acciones a los controles del formulario padre
        this.view.iniciarSesion.setActionCommand("Iniciar Sesion");
        view.nuevaTarea.setActionCommand("Nueva Tarea");
        view.nuevaTarea.setEnabled(false);
        //Se pone a escuchar las acciones del usuario
        view.iniciarSesion.addActionListener(this);
        view.nuevaTarea.addActionListener(this);
    }

 //___________________________________________________________________________________ Soy una barra separadora :)
/* muestra la vista al usuario */
    public void go() {
        this.view.setVisible(true);
    }

  //___________________________________________________________________________________ Soy una barra separadora :)
    /* ATENTO A LAS ACCIONES DEL USUARIO */
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("Iniciar Sesion")) {
            form_sesion();
        }

        if (comando.equals("Registrar")) {

            model.insertarUusario(this.isesion.usuario.getText(), this.isesion.pass.getText());
        }
        if (comando.equals("Log")) {
            if (model.loguear(isesion.usuario.getText(), isesion.pass.getText())) {

                isesion.dispose(); //cierra el form
                view.nuevaTarea.setEnabled(true);
                view.iniciarSesion.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario incorrecto, vuelva a intentarlo.", "Usuario incorrecto", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (comando.equals("Nueva Tarea")) {
            formTarea();
            
        }
if(comando.equals("Cancelar")) nuevaTarea.dispose();
    }

 //METODO QUE DEVUELVE UN VALOR BOOLEAN PARA SABER SI UN JINTERNALFRAME ESTA ABIERTO O NO
    private boolean estacerrado(Object obj) {
        JInternalFrame[] activos = this.view.desktop.getAllFrames();
        boolean cerrado = true;
        int i = 0;
        while (i < activos.length && cerrado) {
            if (activos[i] == obj) {
                cerrado = false;
            }
            i++;
        }
        return cerrado;
    }

    /*
     ********************************************************
     ******************* FORMULARIOS ************************
     ********************************************************
     */
    //______________________________
  /* FORMULARIO PARA INICIAR SESION*/
    public void form_sesion() {
        isesion = new IniciarSesion();
        isesion.setTitle("Iniciar Sesion");
        isesion.setVisible(true);
        isesion.setLocationRelativeTo(view); //centrado

        // Se añaden las accione sa los controles
        isesion.registrar.setActionCommand("Registrar");
        isesion.log.setActionCommand("Log");
      
        isesion.registrar.addActionListener(this);
        isesion.log.addActionListener(this);
    }
    
   //FORMULARIO NUEVA TAREA
    public void formTarea(){
        nuevaTarea = new NuevaTarea();
        nuevaTarea.setTitle("Nueva Tarea");
        nuevaTarea.setVisible(true);
        isesion.setLocationRelativeTo(view);
        // Se añaden las accione sa los controles
        nuevaTarea.aceptar.setActionCommand("Aceptar");
        nuevaTarea.cancelar.setActionCommand("Cancelar");
        //Se pone a escuchar
        nuevaTarea.aceptar.addActionListener(this);
        nuevaTarea.cancelar.addActionListener(this);
    }
    

}

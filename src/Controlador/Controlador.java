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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author dam1a_10
 */
public class Controlador implements ActionListener {

    private VentanaInicio view;
    private Modelo model;
//form hijos

    IniciarSesion isesion;
    NuevaTarea nuevaTarea;

    public Controlador() {
    }
    MouseListener ml = new MouseListener() {

        public void mouseClicked(MouseEvent e) {
            
            if (!view.nuevaTarea.isEnabled()) { //el boton "eliminar" pone el resto de boton en disable, así controlamos si se ha pulsado el boton elimninar.
            for (int i = 0; i < view.text.length; i++) {      
                if (e.getSource().equals(view.text[i])) {

                    int seleccion = JOptionPane.showOptionDialog(null, "¿Desea eliminar esta tarea?", //Mensaje
                            "Eliminar Tarea", // Título
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, // null para icono por defecto.
                            new Object[]{"Si", "No"}, // null para YES, NO y CANCEL
                            "Si");
                    if (seleccion == 0) { 
                        model.delete("tareas ", "id="+view.text[i].getName());
                        
                         }
                        
           view.nuevaTarea.setEnabled(true);
            view.actualizar.setEnabled(true);}
            }
            }
        }
        public void mousePressed(MouseEvent e) {  }      @Override
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {
            for (int i = 0; i < view.text.length; i++) {
                if (e.getSource().equals(view.text[i])) {
                    view.text[i].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
        @Override
        public void mouseExited(MouseEvent e) {
            for (int i = 0; i < view.text.length; i++) {
                if (e.getSource().equals(view.text[i])) {
                    view.text[i].setBackground(Color.DARK_GRAY);
                }
            }
        }
    };

    public Controlador(VentanaInicio vista, Modelo modelo) {
        this.view = vista;
        this.model = modelo;
        iniciar();
        view.mostrarTareas(model.numeroTareas(), model.recuperarTareas());
        for (int i = 0; i < view.text.length; i++) {
            view.text[i].addMouseListener(ml);
        }
    }
    /* INICIA */

    private void iniciar() {
        if (model.getConnectoin() != null) {
            view.conectado.setEnabled(true);
        } else {
            view.conectado.setEnabled(false);
        }
        view.setTitle("Gestor de Tareas");
        view.setExtendedState(VentanaInicio.MAXIMIZED_BOTH);
        //Se añade las acciones a los controles del formulario padre
        this.view.iniciarSesion.setActionCommand("Iniciar Sesion");
        view.nuevaTarea.setActionCommand("Nueva Tarea");
        view.nuevaTarea.setEnabled(false);
        view.actualizar.setActionCommand("Actualizar");
        view.eliminar.setActionCommand("Eliminar");
        view.ordenar.setActionCommand("Ordenar");
        //Se pone a escuchar las acciones del usuario
        view.iniciarSesion.addActionListener(this);
        view.nuevaTarea.addActionListener(this);
        view.actualizar.addActionListener(this);
        view.eliminar.addActionListener(this);
        view.ordenar.addActionListener(this);

        //Mouese Listener
        view.panel.addMouseListener(ml);

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
            formSesion();
        }
        if (comando.equals("Actualizar")) {
            int index=view.ordenar.getSelectedIndex();
            if(index!=0){
view.mostrarTareas(model.numeroTareas(), model.recuperarTareas(view.ordenar.getItemAt(index).toString()));
            }
            view.mostrarTareas(model.numeroTareas(), model.recuperarTareas());
            for (int i = 0; i < view.text.length; i++) {
                view.text[i].addMouseListener(ml);
            }
        }
        if (comando.equals("Eliminar")) {
            view.nuevaTarea.setEnabled(false);
            view.actualizar.setEnabled(false);
            

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
        {
            if (comando.equals("Aceptar")) {
              
                int index = nuevaTarea.prioridad.getSelectedIndex();            
                
              
                model.insertarTarea(model.usuario.getUsuario(), nuevaTarea.titulo.getText(),
                        nuevaTarea.descripcion.getText(), nuevaTarea.prioridad.getItemAt(index).toString(), (int) nuevaTarea.progreso.getValue());

                nuevaTarea.dispose();
            }

            if (comando.equals("Cancelar")) {
                nuevaTarea.dispose();
            }
        }

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
    public void formSesion() {

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
    public void formTarea() {
        nuevaTarea = new NuevaTarea();
        nuevaTarea.setTitle("Nueva Tarea");
        nuevaTarea.setVisible(true);
        nuevaTarea.setLocationRelativeTo(view);
        // Se añaden las accione sa los controles
        nuevaTarea.aceptar.setActionCommand("Aceptar");
        nuevaTarea.cancelar.setActionCommand("Cancelar");
        //Se pone a escuchar
        nuevaTarea.aceptar.addActionListener(this);
        nuevaTarea.cancelar.addActionListener(this);
    }

}

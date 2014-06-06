package GestorDeTareas;

import Controlador.Controlador;
import Modelo.Modelo;
import Vista.VentanaInicio;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author dam1a_10
 */
public class Main {
    
    public static void main(String[] args) {
    Modelo modelo = new Modelo();
    VentanaInicio vista = new VentanaInicio();
 try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            new Controlador(vista, modelo).go();
        } catch (UnsupportedLookAndFeelException ex) {}
          catch (ClassNotFoundException ex) {}
          catch (InstantiationException ex) {}
          catch (IllegalAccessException ex) {}
    }

}

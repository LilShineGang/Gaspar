package com.bdd.guiswingorderwinbdd;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación.
 * Muestra primero el Logo y luego la Ventana Principal.
 */
public class MainBDD {

    public static void main(String[] args) {
        // Ejecutamos en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            
            // --- PASO 1: MOSTRAR EL LOGO ---
            // Necesitamos un marco "padre" temporal para el diálogo
            JFrame dummyFrame = new JFrame();
            
            // Creamos el diálogo del logo. 'true' significa que es MODAL (bloquea la ejecución)
            dlgLogoBDD dialogLogo = new dlgLogoBDD(dummyFrame, true);
            
            // Centramos y mostramos
            dialogLogo.setLocationRelativeTo(null);
            dialogLogo.setVisible(true); 
            // ALERTA: El código se "congela" aquí hasta que cierres la ventanita del logo con la X.
            
            
            // --- PASO 2: MOSTRAR LA APP PRINCIPAL ---
            // Una vez cerrado el logo, el código sigue aquí.
            dummyFrame.dispose(); // Tiramos el marco temporal
            
            // Creamos y mostramos tu ventana grande
            GuiSwingOrderWinBDD app = new GuiSwingOrderWinBDD();
            app.setLocationRelativeTo(null); // Centrar en pantalla
            app.setVisible(true);
        });
    }
}
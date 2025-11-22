package com.bdd.guiswingorderwinbdd;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainBDD {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Mostrar Logo (Bloqueante)
            JFrame dummy = new JFrame();
            dlgLogoBDD logo = new dlgLogoBDD(dummy, true);
            logo.setLocationRelativeTo(null);
            logo.setVisible(true); // La ejecución se detiene aquí hasta cerrar el logo
            
            dummy.dispose();

            // 2. Iniciar Aplicación Principal
            GuiSwingOrderWinBDD app = new GuiSwingOrderWinBDD();
            app.setLocationRelativeTo(null);
            app.setVisible(true);
        });
    }
}
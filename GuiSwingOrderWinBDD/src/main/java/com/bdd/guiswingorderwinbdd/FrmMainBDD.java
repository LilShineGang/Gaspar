package com.bdd.guiswingorderwinbdd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class FrmMainBDD extends JFrame {

    public FrmMainBDD() {
        initUI();
    }

    private void initUI() {
        // --- 1. Configuración Básica de la Ventana ---
        setTitle("Aplicación Principal - BDD");
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar en pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- 2. Establecer el Icono de la Aplicación ---
        // La imagen debe estar en el mismo paquete que este archivo
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("icon-bdd.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("No se encontró el icono 'icon-bdd.png'. Asegúrate de ponerlo junto al .java");
        }

        // --- 3. Crear la Barra de Menú ---
        crearBarraMenu();
        
        // --- 4. Agregar contenido de prueba (Opcional) ---
        JLabel label = new JLabel("Bienvenido al sistema de pedidos", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
    }

    private void crearBarraMenu() {
        JMenuBar menuBar = new JMenuBar();

        // =======================================================
        // MENÚ APP
        // =======================================================
        JMenu menuApp = new JMenu("App");
        menuApp.setMnemonic(KeyEvent.VK_A); // Alt+A abre este menú

        // Elemento: Ver Pedido Completo
        JMenuItem itemPedido = new JMenuItem("Ver Pedido Completo");
        // Atajo: Ctrl + P
        itemPedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        itemPedido.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función Ver Pedido (Pendiente)"));
        
        // Elemento: Calculadora
        JMenuItem itemCalc = new JMenuItem("Calculadora");
        // Atajo: Ctrl + C
        itemCalc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        itemCalc.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función Calculadora (Pendiente)"));

        // Elemento: Salir
        JMenuItem itemSalir = new JMenuItem("Salir");
        // Atajo: Ctrl + S
        itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        // Acción: Cerrar aplicación
        itemSalir.addActionListener(e -> System.exit(0));

        // Agregar elementos al menú App
        menuApp.add(itemPedido);
        menuApp.add(itemCalc);
        menuApp.addSeparator(); // Línea separadora
        menuApp.add(itemSalir);

        // =======================================================
        // MENÚ AYUDA
        // =======================================================
        JMenu menuAyuda = new JMenu("Ayuda");
        menuAyuda.setMnemonic(KeyEvent.VK_H); // Alt+H

        // Elemento: Información
        JMenuItem itemInfo = new JMenuItem("Información");
        itemInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        itemInfo.addActionListener(e -> JOptionPane.showMessageDialog(this, "Información del sistema v1.0"));

        // Elemento: Compañía (Muestra el JDialog del Logo)
        JMenuItem itemCompania = new JMenuItem("Compañía");
        // Atajo: Ctrl + L (Logo)
        itemCompania.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        itemCompania.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoCompania();
            }
        });

        // Elemento: Acerca de
        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        itemAcerca.addActionListener(e -> JOptionPane.showMessageDialog(this, "Desarrollado por: BDD"));

        // Agregar elementos al menú Ayuda
        menuAyuda.add(itemInfo);
        menuAyuda.add(itemCompania);
        menuAyuda.add(itemAcerca);

        // Agregar menús a la barra
        menuBar.add(menuApp);
        menuBar.add(menuAyuda);

        // Asignar la barra al JFrame
        setJMenuBar(menuBar);
    }

    // Método para abrir tu JDialog existente
    private void mostrarDialogoCompania() {
        // Creamos una instancia de tu diálogo 'dlgLogoBDD'
        // 'this' es el padre, 'true' es para que sea MODAL
        dlgLogoBDD dialog = new dlgLogoBDD(this, true);
        dialog.setLocationRelativeTo(this); // Centrar sobre la ventana principal
        dialog.setVisible(true);
    }
}
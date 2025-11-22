package com.bdd.guiswingorderwinbdd;

import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class FrmMainBDD extends JFrame {

    public FrmMainBDD() {
        initUI();
    }

    private void initUI() {
        setTitle("Aplicación Principal - BDD");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cargar icono de la aplicación
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/img_BDD/icon-BDD.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Nota: No se encontró el icono en /img_BDD/icon-BDD.png");
        }

        crearBarraMenu();

        // Contenido de prueba
        JLabel label = new JLabel("Bienvenido al sistema de pedidos", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
    }

    private void crearBarraMenu() {
        JMenuBar menuBar = new JMenuBar();

        // --- Menú App ---
        JMenu menuApp = new JMenu("App");
        menuApp.setMnemonic(KeyEvent.VK_A);

        JMenuItem itemPedido = new JMenuItem("Ver Pedido Completo");
        itemPedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        itemPedido.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función Ver Pedido (Pendiente)"));

        JMenuItem itemCalc = new JMenuItem("Calculadora");
        itemCalc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        itemCalc.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función Calculadora (Pendiente)"));

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        itemSalir.addActionListener(e -> System.exit(0));

        menuApp.add(itemPedido);
        menuApp.add(itemCalc);
        menuApp.addSeparator();
        menuApp.add(itemSalir);

        // --- Menú Ayuda ---
        JMenu menuAyuda = new JMenu("Ayuda");
        menuAyuda.setMnemonic(KeyEvent.VK_H);

        JMenuItem itemInfo = new JMenuItem("Información");
        itemInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        itemInfo.addActionListener(e -> JOptionPane.showMessageDialog(this, "Información del sistema v1.0"));

        JMenuItem itemCompania = new JMenuItem("Compañía");
        itemCompania.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        itemCompania.addActionListener(e -> mostrarDialogoCompania());

        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        itemAcerca.addActionListener(e -> JOptionPane.showMessageDialog(this, "Desarrollado por: BDD"));

        menuAyuda.add(itemInfo);
        menuAyuda.add(itemCompania);
        menuAyuda.add(itemAcerca);

        // Asignar menú
        menuBar.add(menuApp);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
    }

    private void mostrarDialogoCompania() {
        dlgLogoBDD dialog = new dlgLogoBDD(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
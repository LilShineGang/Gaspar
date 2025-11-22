package com.bdd.guiswingorderwinbdd;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * @author dan
 */
public class dlgLogoBDD extends JDialog {

    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;

    public dlgLogoBDD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // 1. Configuración del diseño principal
        jPanel1.setLayout(new BorderLayout());

        // 2. Panel superior transparente para el botón (Alineado a la IZQUIERDA)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);

        // Personalizamos el botón X
        jPanel1.remove(jButton1); // Lo quitamos de su posición original
        jButton1.setText("X");
        jButton1.setMargin(new Insets(2, 2, 2, 2)); // Márgenes reducidos

        topPanel.add(jButton1);
        jPanel1.add(topPanel, BorderLayout.NORTH);

        // 3. Cargar y centrar la imagen del logo
        try {
            ImageIcon logo = new ImageIcon(getClass().getResource("/img_BDD/logo-BDD.png"));
            jLabel1.setIcon(logo);
            jLabel1.setText("");
            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

            jPanel1.remove(jLabel1); // Lo recolocamos en el centro
            jPanel1.add(jLabel1, BorderLayout.CENTER);

        } catch (Exception e) {
            System.err.println("Error: No se pudo cargar el logo.");
        }

        // 4. Ajuste automático al tamaño de la imagen
        jPanel1.setPreferredSize(null);
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        jButton1 = new JButton();
        jLabel1 = new JLabel();

        setModal(true);
        setUndecorated(true);
        setResizable(false);

        jButton1.setText("X");
        jButton1.addActionListener(evt -> {
            this.setVisible(false);
            this.dispose();
        });

        jLabel1.setText("jLabel1");

        // Nota: Este diseño inicial se sobrescribe en el constructor
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 300, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addComponent(jLabel1)
                .addGap(0, 200, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, BorderLayout.CENTER);
        pack();
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            dlgLogoBDD dialog = new dlgLogoBDD(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
}